package kratha.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.production.*;
import kratha.world.blocks.environment.*;

import static mindustry.Vars.*;

//i dont know what to call this. its a srill for OreCluster
public class OreClusterDrill extends Block{
    public float hardnessDrillMultiplier = 50f;

    protected final ObjectIntMap<Item> oreCount = new ObjectIntMap<>();
    protected final Seq<Item> itemArray = new Seq<>();

    /** Maximum tier of blocks this drill can mine. */
    public int tier;
    /** Base time to drill one ore, in frames. */
    public float drillTime = 300;
    /** How many times faster the drill will progress when boosted by liquid. */
    public float liquidBoostIntensity = 1.6f;
    /** Speed at which the drill speeds up. */
    public float warmupSpeed = 0.015f;
    /** Special exemption item that this drill can't mine. */
    public Item blockedItem;
    /** Special exemption items that this drill can't mine. */
    public Seq<Item> blockedItems;

    //return variables for countOre
    protected Item returnItem;
    protected int returnCount;

    /** Whether to draw the item this drill is mining. */
    public boolean drawMineItem = true;
    /** Effect played when an item is produced. This is colored. */
    public Effect drillEffect = Fx.mine;
    /** Drill effect randomness. Block size by default. */
    public float drillEffectRnd = -1f;
    /** Chance of displaying the effect. Useful for extremely fast drills. */
    public float drillEffectChance = 0.02f;
    /** Speed the drill bit rotates at. */
    public float rotateSpeed = 2f;
    /** Effect randomly played while drilling. */
    public Effect updateEffect = Fx.pulverizeSmall;
    /** Chance the update effect will appear. */
    public float updateEffectChance = 0.02f;

    /** Multipliers of drill speed for each item. Defaults to 1. */
    public ObjectFloatMap<Item> drillMultipliers = new ObjectFloatMap<>();

    public boolean drawRim = false;
    public boolean drawSpinSprite = true;
    public Color heatColor = Color.valueOf("ff5512");
    public extureRegion rimRegion;
    public TextureRegion topRegion;
    public extureRegion itemRegion;

    public Drill(String name){
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.drills;
        hasLiquids = true;
        hasItems = true;
        ambientSound = Sounds.loopDrill;
        ambientSoundVolume = 0.019f;
        //drills work in space I guess
        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
    }

    @Override
    public void init(){
        super.init();
        if(blockedItems == null && blockedItem != null){
            blockedItems = Seq.with(blockedItem);
        }
        if(drillEffectRnd < 0) drillEffectRnd = size;
    }

    @Override
    public void load(){
        super.load();
        rimRegion = Core.atlas.find(name+"-rim");
        topRegion = Core.atlas.find(name+"-top");
        itemEegion = Core.atlas.find(name+"-item");
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("drillspeed", (DrillBuild e) ->
             new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * 60 * e.timeScale(), 2)), () -> Pal.ammo, () -> e.warmup));
    }

    public Item getDrop(Tile tile){
        return tile.drop();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Tile tile = world.tile(x, y);
        if(tile == null) return;

        countOre(tile);

        if(returnItem != null){
            float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 60f / getDrillTime(returnItem) * returnCount, 2), x, y, valid);
            float dx = x * tilesize + offset - width/2f - 4f, dy = y * tilesize + offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(returnItem.fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(returnItem.fullIcon, dx, dy, s, s);

            if(drawMineItem){
                Draw.color(returnItem.color);
                Draw.rect(itemRegion, tile.worldx() + offset, tile.worldy() + offset);
                Draw.color();
            }
        }else{
            Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> t.drop() != null && (t.drop().hardness > tier || (blockedItems != null && blockedItems.contains(t.drop()))));
            Item item = to == null ? null : to.drop();
            if(item != null){
                drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, valid);
            }
        }
    }

    public float getDrillTime(Item item){
        return (drillTime + hardnessDrillMultiplier * item.hardness) / drillMultipliers.get(item, 1f);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.drillTier, StatValues.drillables(drillTime, hardnessDrillMultiplier, size * size, drillMultipliers, b -> b instanceof Floor f && !f.wallOre && f.itemDrop != null &&
            f.itemDrop.hardness <= tier && (blockedItems == null || !blockedItems.contains(f.itemDrop)) && (indexer.isBlockPresent(f) || state.isMenu())));

        stats.add(Stat.drillSpeed, 60f / drillTime * size * size, StatUnit.itemsSecond);

        if(liquidBoostIntensity != 1 && findConsumer(f -> f instanceof ConsumeLiquidBase && f.booster) instanceof ConsumeLiquidBase consBase){
            stats.remove(Stat.booster);
            stats.add(Stat.booster,
                StatValues.speedBoosters("{0}" + StatUnit.timesSpeed.localized(),
                consBase.amount,
                liquidBoostIntensity * liquidBoostIntensity, false, consBase::consumes)
            );
        }
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, topRegion};
    }

    public class DrillBuild extends Building{
        public int link;
        public float progress;
        public float warmup;
        public float timeDrilled;
        public float lastDrillSpeed;
        public Item drillItem;

        @Override
        public void drawConfigure(){
            Drawf.select(x, y, tile.block().size * tilesize / 2f + 2f, Pal.accent);

            
        }

        @Override
        public boolean shouldConsume(){
            return items.total() < itemCapacity && enabled && drillItem != null;
        }

        @Override
        public boolean shouldAmbientSound(){
            return efficiency > 0.01f && items.total() < itemCapacity;
        }

        @Override
        public float ambientVolume(){
            return efficiency * (size * size) / 4f;
        }

        @Override
        public void drawSelect(){
            drawItemSelection(drillItem);
        }

        @Override
        public void pickedUp(){
            drillItem = null;
        }

        @Override
        public Object senseObject(LAccess sensor){
            if(sensor == LAccess.firstItem) return drillItem;
            return super.senseObject(sensor);
        }

        @Override
        public void updateTile(){
            if(timer(timerDump, dumpTime / timeScale)){
                dump(drillItem != null && items.has(drillItem) ? drillItem : null);
            }

            if(drillItem == null){
                return;
            }

            timeDrilled += warmup * delta();

            float delay = getDrillTime(drillItem);

            if(items.total() < itemCapacity && drilltem != null && efficiency > 0){
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                lastDrillSpeed = (speed * 1 * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * 1 * speed * warmup;

                if(Mathf.chanceDelta(updateEffectChance * warmup))
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
            }else{
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if(drillItem != null && progress >= delay && items.total() < itemCapacity){
                int amount = (int)(progress / delay);
                for(int i = 0; i < amount; i++){
                    offload(drillItem);
                }

                progress %= delay;

                if(wasVisible && Mathf.chanceDelta(drillEffectChance * warmup)) drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
            }
        }

        @Override
        public float progress(){
            return drillItem == null ? 0f : Mathf.clamp(progress / getDrillTime(dominantItem));
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.progress && drillItem != null) return progress;
            return super.sense(sensor);
        }

        @Override
        public void drawCracks(){}

        public void drawDefaultCracks(){
            super.drawCracks();
        }

        @Override
        public void draw(){
            float s = 0.3f;
            float ts = 0.6f;

            Draw.rect(region, x, y);
            Draw.z(Layer.blockCracks);
            drawDefaultCracks();

            Draw.z(Layer.blockAfterCracks);
            if(drawRim){
                Draw.color(heatColor);
                Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
                Draw.blend(Blending.additive);
                Draw.rect(rimRegion, x, y);
                Draw.blend();
                Draw.color();
            }

            Draw.rect(topRegion, x, y);

            if(drillItem != null && drawMineItem){
                Draw.color(drillItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progress);
            write.f(warmup);
            write.i(link);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision >= 1){
                progress = read.f();
                warmup = read.f();
                link = read.i();
            }
        }
    }

          }
              
