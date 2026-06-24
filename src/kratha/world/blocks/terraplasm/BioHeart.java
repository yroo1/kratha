package kratha.world.blocks.terraplasm;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.util.pooling.Pools;
import arc.scene.ui.layout.Scl;
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
import mindustry.type.*;
import mindustry.gen.*;
import mindustry.world.meta.*;
import kratha.content.terraplasm.Terraplasm;

import static mindustry.Vars.*;

public class BioHeart extends BioBlock {
    public float heartBpm=80;
    public int heartPower=32;
    public boolean hideDataPatchWarning=false;
    public BioHeart(String name){
        super(name);
        priority = TargetPriority.core;
        hasItems = true;
        itemCapacity = 20;
        unloadable = false;
    }
    public class BioHeartBuild extends BioBuilding {        
        @Override
        public void updateTile() {
            if (fullyGrown){
                growProgress=0f;
                if (pulseTimer<3600/heartBpm) {
                    pulseTimer+=delta();
                } else {
                    updatePulse();
                    pulseTimer=0;
                    drawPulseScale=pulseScale;
                    if(allowRoot)growRoots();
                }
                if (drawPulseScale>0.01f) {
                    drawPulseScale*=0.9;
                }
            }
            if(!fullyGrown){
                growProgress*=0.95;
                pulsed=true; //prevents from getting pulse when still growing
                if(growProgress>-0.05){
                    growProgress=0;
                    fullyGrown=true;
                    pulsed=false;
                }
            }
            if(!(allowRoot||hideDataPatchWarning)){
                tile.setBlock(Terraplasm.dataPatchInfo,team);
            }
        }
        public void updatePulse() {
            if (true) {
                for(int i=0;i<4;i++){
                    for(int j=-1;j<=1;j++){
                        Building adj;
                        if(i==0||i==2){
                            adj = tile.nearby(Geometry.d4(i).x*2,Geometry.d4(i).y*2+j).build;
                        } else {
                            adj = tile.nearby(Geometry.d4(i).x*2+j,Geometry.d4(i).y*2).build;
                        }
                        if (adj instanceof BioBuilding adjbuild) {
                            if (!adjbuild.pulsed) {                        
                                adjbuild.biopulse=Math.max(adjbuild.biopulse,heartPower);
                                adjbuild.pulseSource=tile;
                            }
                        }
                    }
                }
            }
        }
        public void growRoots(){
            //only for 3x3 block smh
            //well who cares lmao it works
            for(int i=0;i<4;i++){
                for(int j=-1;j<=1;j++){
                    Tile adj;
                    if(i==0||i==2){
                        adj = tile.nearby(Geometry.d4(i).x*2,Geometry.d4(i).y*2+j);
                    } else {
                        adj = tile.nearby(Geometry.d4(i).x*2+j,Geometry.d4(i).y*2);
                    }
                    if(adj!=null&&adj.build==null) adj.setBlock(Terraplasm.root,team);
                }
            }
        }
        public boolean send(Item item, int targetX, int targetY){
            if(!this.items.has(item,1))return false;
            float bestDist = Float.POSITIVE_INFINITY;
            Building outputTo=null;
            for(int i=0;i<4;i++){
                for(int j=-1;j<=1;j++){
                    Tile adj;
                    if(i==0||i==2){
                        adj = tile.nearby(Geometry.d4(i).x*2,Geometry.d4(i).y*2+j);
                    } else {
                        adj = tile.nearby(Geometry.d4(i).x*2+j,Geometry.d4(i).y*2);
                    }
                    if(adj!=null&&adj.block()!=null&&adj.build!=null&&adj.build instanceof Root.RootBuild r){
                        float dist = Mathf.dst(targetX,targetY,adj.x,adj.y);
                        if(dist<bestDist){
                            bestDist=dist;
                            outputTo = r;
                        }
                    }
                }
            }
            if(outputTo!=null&&outputTo.acceptItem(this,item)&&outputTo instanceof Root.RootBuild r){
                r.handleItem(this,item);
                r.itemTargetX=targetX;
                r.itemTargetY=targetY;
                return true;
            }else{
                return false;
            }
        }
        @Override
        public void draw(){
            Draw.z(Layer.block+0.01f); //the heart must be above even if its just slightly
            drawPulse(block.region,drawPulseScale);

            if(allowRoot||hideDataPatchWarning)return;
            Font font = Fonts.outline;
            GlyphLayout l = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
            boolean ints = font.usesIntegerPositions();
            font.getData().setScale(1 / 4f / Scl.scl(1f));
            font.setUseIntegerPositions(false);

            String text = "Datapatch required!";
                
            l.setText(font, text, Color.white, 90f, Align.left, true);
            float offset = 0f;

            Draw.color(0f, 0f, 0f, 0.2f);
            Fill.rect(x, y - tilesize/2f - l.height/2f - offset, l.width + offset*2f, l.height + offset*2f);
            Draw.color();
            font.setColor(Color.red);
            font.draw(text, x - l.width/2f, y - tilesize/2f - offset, 90f, Align.left, true);
            font.setUseIntegerPositions(ints);

            font.getData().setScale(1f);

            Pools.free(l);
        }
        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return 0;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return true; //this guy always accept items, if its full item will be accepted but not added
        }

        @Override
        public void handleItem(Building source, Item item){
            if(items.get(item) < itemCapacity) items.add(item, 1);
        }

        @Override
        public int removeStack(Item item, int amount){
            int result = super.removeStack(item, amount);
            return result;
        }
    }
                       }

