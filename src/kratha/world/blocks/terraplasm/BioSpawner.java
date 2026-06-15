package kratha.world.blocks.terraplasm;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import arc.math.geom.*;
import mindustry.world.blocks.defense.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.Tile;
import mindustry.graphics.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.meta.*;
import mindustry.gen.*;
import mindustry.type.*;
import java.util.Random;
import kratha.*;
import kratha.content.KrathaFx;
import mindustry.entities.Effect;

import static mindustry.Vars.*;

public class BioSpawner extends BioBlock {
    public UnitType unitType;
    public TextureRegion bubbleRegion;
    public TextureRegion topRegion;
    public Effect spawnEffect=KrathaFx.bulbPop;
    public Item inputItem1;
    public int requiredItem1;
    public Item inputItem2;
    public int requiredItem2;
    public int excessMultiplier = 2;
    
    public float wscl = 10f, wmag = 1.2f, wtscl = 1f, wmag2 = 1.5f;
    
    public int pulseToSpawn=12;
    public BioSpawner(String name){
        super(name);
        update=true;
        isRoot=false;
        hasItems=true;
        itemCapacity=Math.max(requiredItem1,requiredItem2)*2;
    }
    
    @Override
    public void load(){
        super.load();
        bubbleRegion = Core.atlas.find(name+"-bubble");
        topRegion = Core.atlas.find(name+"-top");
    }
    
    public class BioSpawnerBuild extends BioBuilding {
        public int spawnProgress = 0;
        public int expectedItem1 = 0;
        public int expectedItem2 = 0;
        
        @Override
        public void updatePulse(){
            if (this.items.has(inputItem1,requiredItem1)&&this.items.has(inputItem2,requiredItem2)) {
                spawnProgress++;
                if(spawnProgress >= pulseToSpawn) {
                    this.items.remove(inputItem1,requiredItem1);
                    this.items.remove(inputItem2,requiredItem2);
                    
                    spawnProgress = 0;
                    Unit unit = unitType.create(team);
                    unit.set(x, y);
                    unit.rotation = 90f;
                    unit.add();
                    spawnEffect.at(x,y);
                }
            }
            Building heart = getNearestHeart();
            
            if(heart==null)return;
            expectedItem1=0;
            expectedItem2=0;
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
                        if(adjitem==inputItem1)expectedItem1++;
                        if(adjitem==inputItem2)expectedItem2++;
                    }
                }
            }
            expectedItem1+=items.get(inputitem1);
            expectedItem1+=items.get(inputitem2);

            if(expectedItem1<requiredItem1*excessMultiplier&&heart!=null&&heart instanceof BioHeart.BioHeartBuild heartbuild){
                boolean success = heartbuild.send(inputItem1,(int)tile.x,(int)tile.y);
                if(success){
                    heartbuild.items.remove(inputItem1,1);
                }
            }
            if(expectedItem2<requiredItem2*excessMultiplier&&heart!=null&&heart instanceof BioHeart.BioHeartBuild heartbuild){
                boolean success = heartbuild.send(inputItem2,(int)tile.x,(int)tile.y);
                if(success){
                    heartbuild.items.remove(inputItem2,1);
                }
            }
        }
        @Override
        public void draw(){
            drawPulse(block.region,drawPulseScale);
            //copied from WobbleProp lol
            float bubbleScale = spawnProgress / (float)pulseToSpawn;
            Draw.rectv(bubbleRegion, tile.worldx(), tile.worldy(), region.width * region.scl()*(1+drawPulseScale)*bubbleScale, region.height * region.scl()*(1+drawPulseScale)*bubbleScale, 0, vec -> vec.add(
            Mathf.sin(vec.y*3 + Time.time, wscl, wmag) + Mathf.sin(vec.x*3 - Time.time, 70 * wtscl, 0.8f * wmag2),
            Mathf.cos(vec.x*3 + Time.time + 8, wscl + 6f, wmag * 1.1f) + Mathf.sin(vec.y*3 - Time.time, 50 * wtscl, 0.2f * wmag2)
            ));
            Draw.rect(topRegion,x,y);
        }
        @Override
        public boolean acceptItem(Building source, Item item){
            if(item==inputItem1&&!this.items.has(inputItem1,requiredItem1*excessMultiplier))return true;
            if(item==inputItem2&&!this.items.has(inputItem2,requiredItem2*excessMultiplier))return true;
            return false;
        }
        
        @Override
        public void handleItem(Building source, Item item){
            items.add(item,1);
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(spawnProgress);
            write.i(expectedItem1);
            write.i(expectedItem2);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            spawnProgress=read.i();
            expectedItem1=read.i();
            expectedItem2=read.i();
        }
    }
 }     
