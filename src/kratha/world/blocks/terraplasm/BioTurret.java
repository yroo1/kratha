package kratha.world.blocks.terraplasm;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import arc.math.geom.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.Tile;
import mindustry.graphics.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.meta.*;
import mindustry.gen.*;
import mindustry.type.*;
import java.util.Random;
import kratha.graphics.*;
import kratha.content.*;
import arc.struct.*;
import mindustry.entities.bullet.*;
import mindustry.logic.*;
import kratha.content.terraplasm.Terraplasm;

import static mindustry.Vars.*;

public class BioTurret extends Turret{
    public BulletType shootType;
    public boolean isRoot=false;
    public float pulseScale=0.5f;
    public Item ammoItem=null; //only use single type of ammo
    public int ammoItemMultiplier=1;
    
    public BioTurret(String name){
        super(name);
        update=true;
        hasItems=true;
        rebuildable = false;
        drawTeamOverlay = false;
        outlineColor = KrathaPal.terraOutline;
        destroySound = Sounds.loopSpray;
        unitFilter = u -> u.getDuration(KrathaStatusEffects.seen)>1;
        buildVisibility = BuildVisibility.editorOnly;
    }

    @Override
    public void requirements(Category cat, ItemStack[] stacks){
        requirements(cat, BuildVisibility.editorOnly, stacks);
    }
    

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
    }

    public void limitRange(float margin){
        limitRange(shootType, margin);
    }

    public class BioTurretBuild extends TurretBuild{
        public float pulseProgress=0;
        public int biopulse=0;
        public float pulseTimer=0;
        public float resetPulseTimer=0;
        public float deathTimer=0;
        public float deathTimerLimit=240f;
        public boolean pulsed=false;
        public boolean fullyGrown=false;
        public float growProgress=-1;
        public float drawPulseScale=0;
        public int expectedAmmo=0; //the amount of ammo requested from nearest heart (which is different from actual amount because it sometimes didnt arrive yet)
        public int ammoToUse=ammoItemMultiplier;
        
        @Override
        public void updateTile(){
            super.updateTile();
            if (biopulse>0&&!pulsed&&fullyGrown){
                growProgress=0f;
                deathTimer=0f;
                if (pulseTimer<4f) {
                    pulseTimer+=delta();
                } else {
                    pulseTimer=0;
                    biopulse=0;
                    pulsed=true;
                    drawPulseScale=pulseScale;
                }
            }
            if (pulsed) {
                if (resetPulseTimer<25f) {
                    resetPulseTimer+=delta();
                } else {
                    resetPulseTimer=0;
                    pulsed=false;
                    Building heart = getNearestHeart();
                    if(expectedAmmo<maxAmmo&&heart!=null&&heart instanceof BioHeart.BioHeartBuild heartbuild){
                        if(heartbuild.items.has(ammoItem,1)){
                            boolean success = heartbuild.send(ammoItem,(int)tile.x,(int)tile.y);
                            if(success){
                                heartbuild.items.remove(ammoItem,1);
                            }
                        }
                    }
                    if(heart==null)return;
                    expectedAmmo=0;
                    int cx = (int)((tile.x+heart.tile.x)/2f);
                    int cy = (int)((tile.y+heart.tile.y)/2f);
                    int ceilDist = (int)Math.ceil(Mathf.dst(tile.x,tile.y,heart.tile.x,heart.tile.y)/2f)+8;
                    for(int i=-ceilDist;i<=ceilDist;i++){
                        for(int j=-ceilDist;j<=ceilDist;j++){
                            Tile adj;
                            adj = tile.nearby(i,j);
                            if (adj != null && adj.build!=null && (adj.build instanceof Root.RootBuild adjr)) {                        
                                Item adjitem = adjr.lastItem;
                                if(world.tile(adjr.itemTargetX,adjr.itemTargetY)==null)continue;
                                if(world.tile(adjr.itemTargetX,adjr.itemTargetY).build==null)continue;
                                if(world.tile(adjr.itemTargetX,adjr.itemTargetY).build!=this)continue;
                                if(adjitem==ammoItem)expectedAmmo++;
                            }
                        }
                    }
                expectedAmmo+=items.get(ammoItem);
                }
            }
            if (biopulse>=0&&deathTimer<deathTimerLimit){
                deathTimer+=delta();
            }
            if (deathTimer>=deathTimerLimit){
                this.damage(4);
            }
            
            if (drawPulseScale>0.01f) {
                drawPulseScale*=0.85f;
            }
            if(!fullyGrown){
                if(!isRoot) {
                    growProgress*=0.95f;
                } else {
                    growProgress*=0.7f; //root grows faster
                }
                pulsed=true; //prevents from getting pulse when still growing
                if(growProgress>-0.05){
                    growProgress=0;
                    fullyGrown=true;
                    pulsed=false;
                }
            }
        }
        public void drawPulse(TextureRegion sprite,float scale) {
            scale+=1f+growProgress;
            if (scale>0.01f) {
                float sx=x-scale*0.5f;
                float sy=y-scale*0.5f;
                Draw.scl(scale,scale);
                Draw.rect(sprite,sx,sy);
            } else {
                Draw.rect(sprite,x,y,rotation);
            }
        }
        public void tellDestroyed(int bit,float maxDist){
            float maxDistSquared=maxDist*maxDist;
            int ceilDist = (int)Math.ceil(maxDist);
            for(int i=-ceilDist;i<=ceilDist;i++){
                for(int j=-ceilDist;j<=ceilDist;j++){
                    Tile adj;
                    adj = tile.nearby(i,j);
                    float dist=i*i+j*j;
                    if (dist<maxDistSquared&&adj != null && adj.build!=null && adj.build instanceof Root.RootBuild r) {                        
                        r.extraFloat3 = Root.setbit(r.extraFloat3,bit,false);
                    }
                }
            }
        }
        public void onDestroyed(){
            splashLiquid(KrathaLiquids.terraplasm,40*size);
            if(Terraplasm.root instanceof Root r){
                if(this.block==Terraplasm.skewer)tellDestroyed(0,r.skewerSpacing);
            }
        }
        public Building getNearestHeart() {
            return Units.findAllyTile(team, x, y, 1000, b -> b.block instanceof BioHeart);
        }
        @Override
        public double sense(LAccess sensor){
            return switch(sensor){
                case ammo -> power.status;
                case ammoCapacity -> 1;
                default -> super.sense(sensor);
            };
        }
        
        @Override
        public boolean hasAmmo(){
            return items.has(ammoItem,ammoPerShot);
        }

        @Override
        public BulletType useAmmo(){
            ammoToUse--;
            if(ammoToUse<=0){
                items.remove(ammoItem,ammoPerShot);
                ammoToUse=ammoItemMultiplier;
            }
            return shootType;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return item==ammoItem&&!items.has(ammoItem,maxAmmo);
        }
        
        @Override
        public void handleItem(Building source, Item item){
            items.add(ammoItem,1);
        }

        @Override
        public BulletType peekAmmo(){
            return shootType;
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(expectedAmmo);
            write.i(ammoToUse);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            expectedAmmo=read.i();
            ammoToUse=read.i();
        }
    }
              }
