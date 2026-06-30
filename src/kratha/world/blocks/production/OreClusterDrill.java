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
import mindustry.world.blocks.storage.*;
import kratha.content.blocks.KrathaEnv;

import static mindustry.Vars.*;

//i dont know what to call this. its a srill for OreCluster
public class OreClusterDrill extends Block{
    public float hardnessDrillMultiplier = 50f;
    public int tier;
    public float drillTime = 300;
    public float liquidBoostIntensity = 1.6f;
    public float warmupSpeed = 0.015f;
    public Item blockedItem;
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

    public float range = 80f;

    /** Multipliers of drill speed for each item. Defaults to 1. */
    public ObjectFloatMap<Item> drillMultipliers = new ObjectFloatMap<>();

    public boolean drawRim = false;
    public boolean drawSpinSprite = true;
    public Color heatColor = Color.valueOf("ff5512");
    public TextureRegion rimRegion;
    public TextureRegion topRegion;
    public TextureRegion itemRegion;
    public TextureRegion wireRegion,wireEndRegion;
    public TextureRegion boreRegion;
    public TextureRegion rotatorRegion;

    public OreClusterDrill(String name){
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
        configurable = true;
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
        itemRegion = Core.atlas.find(name+"-item");
        wireRegion = Core.atlas.find(name+"-wire");
        wireEndRegion = Core.atlas.find(name+"-wire-end");
        boreRegion = Core.atlas.find(name+"-bore");
        rotatorRegion = Core.atlas.find(name+"-rotator");
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("drillspeed", (OreClusterDrillBuild e) ->
             new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * 60 * e.timeScale(), 2)), () -> Pal.ammo, () -> e.warmup));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize, y * tilesize, range, Pal.accent);
    }

    public float getDrillTime(Item item){
        return (drillTime + hardnessDrillMultiplier * item.hardness) / drillMultipliers.get(item, 1f);
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, topRegion};
    }

    public class OreClusterDrillBuild extends Building{
        public int link = -1;
        public float progress;
        public float warmup;
        public float timeDrilled;
        public float lastDrillSpeed;
        public Item drillItem;

        @Override
        public void drawConfigure(){
            Drawf.select(x, y, tile.block().size * tilesize / 2f + 2f, Pal.accent);

            Drawf.dashCircle(x, y, range, Pal.accent);

            if(link!=-1&&world.tile(link)!=null&&world.tile(link).block()!=null){
                Tile linkTile = world.tile(link);
                Drawf.select(linkTile.x*tilesize, linkTile.y*tilesize, linkTile.block().size*tilesize/2f+2f, Pal.remove);
            }
        }

        public int linkCountOf(Building other){
            //Namaka what the hell is this
            int linkCount = 0;
            int s2 = 0;
            int frange = (int) Math.ceil(range/tilesize)+1;
            for(int xm = -frange;xm<=frange;xm++){
                for(int ym = -frange;ym<=frange;ym++){
                    Tile othert = tile.nearby(xm,ym);
                    if(othert!=null&&othert.build!=null&&othert.build instanceof OreClusterDrillBuild o&&o.link!=-1&&world.tile(o.link).build==other) {
                        if(othert.build.block.size==2){
                            s2++;
                        }
                    }
                }
            }
            return linkCount = (int)Math.floor((float)s2/4);
        }

        public boolean canLink(Building other){
            float dx=other.x-x;
            float dy=other.y-y;
            float dst=Mathf.sqrt(dx*dx+dy*dy);
            int linkCount = linkCountOf(other);
            int linkLimit = 0;
            if(other.block instanceof OreCluster o)linkLimit = o.maxDrillCount;
            return dst<=range&&linkCount<linkLimit;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            Tile lastOtherTile = world.tile(link);
            if(lastOtherTile!=null&&lastOtherTile.build!=null&&lastOtherTile.build instanceof OreCluster.OreClusterBuild o)o.updateDrillCount();
            if(other.block instanceof OreCluster&&canLink(other)){
                link = other.pos();
                if(other instanceof OreCluster.OreClusterBuild o)o.updateDrillCount();
                return false;
            }
            if (world.tile(link)!=null&&(other == this || link == other.pos())){
                link = -1;
                return false;
            }
            return true;
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

        protected float[] getBorePos(){
            Tile linkTile = world.tile(link);
            if(linkTile==null)return new float[]{0,0,0};
            if(linkTile.block()==null)return new float[]{0,0,0};
            float x1 = x;
            float y1 = y;
            float offset = linkTile.block().size%2!=0?0:tilesize/2f;
            float x2 = linkTile.worldx()+offset;
            float y2 = linkTile.worldy()+offset;
            float dx = x2-x1;
            float dy = y2-y1;
            float dst = Mathf.sqrt(dx*dx+dy*dy);
            x2-=(dx/dst)*tilesize*1.5f;
            y2-=(dy/dst)*tilesize*1.5f;
            x2+= Mathf.cos(timeDrilled*rotateSpeed*-0.01f+Mathf.randomSeed(tile.pos(),0,360))*tilesize/3f;
            y2+= Mathf.sin(timeDrilled*rotateSpeed*-0.01f+Mathf.randomSeed(tile.pos(),0,360))*tilesize/3f;
            return new float[]{x2,y2,dst};
        }

        @Override
        public void updateTile(){
            if(timer(timerDump, dumpTime / timeScale)){
                dump(drillItem != null && items.has(drillItem) ? drillItem : null);
            }

            if(link == -1){
                drillItem = null;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }
            
            if(drillItem == null){
                if(link != -1){
                    Tile linkTile = world.tile(link);
                    if(linkTile!=null&&linkTile.block()!=null&&linkTile.block().itemDrop!=null){
                        drillItem=linkTile.block().itemDrop;
                    }else{
                        link=-1;
                        return;
                    }
                }else{
                    return;
                }
            }
            
            timeDrilled += warmup * delta();

            float delay = getDrillTime(drillItem);

            if(items.total() < itemCapacity && drillItem != null && efficiency > 0){
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                lastDrillSpeed = (speed * 1 * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * 1 * speed * warmup;

                if(Mathf.chanceDelta(updateEffectChance * warmup)&&link!=-1)
                    updateEffect.at(getBorePos()[0] + Mathf.range(size * 2f), getBorePos()[1] + Mathf.range(size * 2f));
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

                if(wasVisible && Mathf.chanceDelta(drillEffectChance * warmup)) drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), drillItem.color);
            }
        }

        @Override
        public float progress(){
            return drillItem == null ? 0f : Mathf.clamp(progress / getDrillTime(drillItem));
        }

        @Override
        public void drawCracks(){}

        public void drawDefaultCracks(){
            super.drawCracks();
        }

        public void drawBeam(TextureRegion region, TextureRegion endRegion, float x, float y, float width, float angle){
            Draw.scl(width,1);
            Draw.rect(region,x,y,angle);
            Draw.scl(1,1);
            Draw.rect(endRegion,x-Mathf.cosDeg(angle)*width/tilesize, y-Mathf.sinDeg(angle)*width/tilesize, angle);
            Draw.rect(endRegion,x+Mathf.cosDeg(angle)*width/tilesize, y+Mathf.sinDeg(angle)*width/tilesize, angle+180);
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

            Tile linkTile = world.tile(link);
            Draw.z(Layer.power+2);
            if(linkTile!=null){
                float x1 = x;
                float y1 = y;
                float x2 = getBorePos()[0];
                float y2 = getBorePos()[1];
                float dst = getBorePos()[2];
                float cx = (x2+x1)/2f;
                float cy = (y2+y1)/2f;
                float angle1 = Angles.angle(x1, y1, x2, y2);
                drawBeam(wireRegion,wireEndRegion,cx,cy,dst*4,angle1);
                Drawf.spinSprite(rotatorRegion, x2, y2, timeDrilled * rotateSpeed);
                Draw.rect(boreRegion, x2, y2);
            }
            Draw.z(Layer.power+2.1f);
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
              
