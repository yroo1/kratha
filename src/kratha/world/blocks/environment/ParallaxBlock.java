package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.environment.TallBlock;

import static mindustry.Vars.*;

public class ParallaxBlock extends TallBlock{
    private static float[] verts = new float[4*6];
    public float parallaxAmount = -100;
    public String floorName;
    public String depthFlag; //for the wall. either 0, 1, or 2. very hardcoded smh
    
    //mid to sur, dep to mid, dep to sur, vod to dep, vod to mid, vod to sur
    public TextureRegion[] wallRegions = new TextureRegion[6]
    public ParallaxBlock(String name){
        super(name);
        forceDark = false;
        solid = false;
    }
    @Override
    public void init(){
        super.init();
        hasShadow = false;
    }
    @Override
    public void load(){
        super.load();
        if(variants > 0){
            for(int i = 0; i < variants; i++){
                variantRegions[i] = Core.atlas.find(floorName + (i + 1));
            }
        }else{
            variantRegions[0] = Core.atlas.find(floorName);
        }
        wallRegions[0] = Core.atlas.find("mid-to-sur");
        wallRegions[1] = Core.atlas.find("dep-to-mid");
        wallRegions[2] = Core.atlas.find("dep-to-sur");
        wallRegions[3] = Core.atlas.find("vod-to-sur");
        wallRegions[4] = Core.atlas.find("vod-to-mid");
        wallRegions[5] = Core.atlas.find("vod-to-sur");
    }
    @Override
    public void drawBase(Tile tile){
        Draw.z(Layer.floor-0.02f);
        Block f = tile.floor();
        float p = parallaxAmount/Core.camera.width;
        float cx = Core.camera.position.x, cy = Core.camera.position.y;
        
        float s = tilesize/2f;
        float x = tile.worldx(), y = tile.worldy();
        float c = Color.white.toFloatBits();
        float mc = Color.clearFloatBits;
        TextureRegion reg = variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region;

        verts[0] = x - s + (x-s-cx)*p;
        verts[1] = y - s + (y-s-cy)*p;
        verts[2] = c;
        verts[3] = reg.u;
        verts[4] = reg.v2;
        verts[5] = mc;

        verts[6] = x + s + (x+s-cx)*p;
        verts[7] = y - s + (y-s-cy)*p;
        verts[8] = c;
        verts[9] = reg.u2;
        verts[10] = reg.v2;
        verts[11] = mc;

        verts[12] = x + s + (x+s-cx)*p;
        verts[13] = y + s + (y+s-cy)*p;
        verts[14] = c;
        verts[15] = reg.u2;
        verts[16] = reg.v;
        verts[17] = mc;

        verts[18] = x - s + (x-s-cx)*p;
        verts[19] = y + s + (y+s-cy)*p;
        verts[20] = c;
        verts[21] = reg.u;
        verts[22] = reg.v;
        verts[23] = mc;

        Draw.vert(region.texture, verts, 0, verts.length);

        Draw.z(Layer.floor-0.01f);
        if(tile.nearby(0,1)!=null&&tile.nearby(0,1).floor()!=f)drawSide(tile,0,tile.nearby(0,1));
        if(tile.nearby(-1,0)!=null&&tile.nearby(-1,0).floor()!=f)drawSide(tile,1,tile.nearby(-1,0));
        if(tile.nearby(0,-1)!=null&&tile.nearby(0,-1).floor()!=f)drawSide(tile,2,tile.nearby(0,-1));
        if(tile.nearby(1,0)!=null&&tile.nearby(1,0).floor()!=f)drawSide(tile,3,tile.nearby(1,0));
    }
    public void drawSide(Tile tile,int r,Tile ntile){
        //rotation order : up left down right
        float p = parallaxAmount/Core.camera.width;
        float cx = Core.camera.position.x, cy = Core.camera.position.y;
        
        float s = tilesize/2f;
        float x = tile.worldx(), y = tile.worldy();
        float c = Color.white.toFloatBits();
        float mc = Color.clearFloatBits;
        Block nfloor=ntils.floor()
        int ndepth = -1;
        if(nfloor instanceof ParallaxFloor pf)ndepth=pf.depthFlag;
        boolean toSur = !(nfloor instanceof ParallaxFloor)
        boolean deeper = !(nfloor instanceof ParralaxFloor)||(nfloor instanceof ParallaxFloor pf&&pf.depthFlag>depthFlag);
        TextureRegion reg = region;
        if(depthFlag==2){
            reg = wallRegions[0];
        }
        if(depthFlag==1){
            if(!toSur&&
            if(toSur) = wallRegions[2];
        }

        if(!deeper)return;

        //i sure do love assigning everything manually
        if(r==0){
            verts[0] = x - s + (x-s-cx)*p;
            verts[1] = y + s + (y+s-cy)*p;
            verts[6] = x + s + (x+s-cx)*p;
            verts[7] = y + s + (y+s-cy)*p;
            verts[12] = x + s;
            verts[13] = y + s;
            verts[18] = x - s;
            verts[19] = y + s;
        }
        if(r==1){
            verts[0] = x - s;
            verts[1] = y - s;
            verts[6] = x - s + (x-s-cx)*p;
            verts[7] = y - s + (y-s-cy)*p;
            verts[12] = x - s + (x-s-cx)*p;
            verts[13] = y + s + (y+s-cy)*p;
            verts[18] = x - s;
            verts[19] = y + s;
        }
        if(r==2){
            verts[0] = x - s;
            verts[1] = y - s;
            verts[6] = x + s;
            verts[7] = y - s;
            verts[12] = x + s + (x+s-cx)*p;
            verts[13] = y - s + (y-s-cy)*p;
            verts[18] = x - s + (x-s-cx)*p;
            verts[19] = y - s + (y-s-cy)*p;
        }
        if(r==3){
            verts[0] = x + s + (x+s-cx)*p;
            verts[1] = y - s + (y-s-cy)*p;
            verts[6] = x + s;
            verts[7] = y - s;
            verts[12] = x + s;
            verts[13] = y + s;
            verts[18] = x + s + (x+s-cx)*p;
            verts[19] = y + s + (y+s-cy)*p;
        }
        
        verts[2] = c;
        verts[3] = reg.u;
        verts[4] = reg.v2;
        verts[5] = mc;

        verts[8] = c;
        verts[9] = reg.u2;
        verts[10] = reg.v2;
        verts[11] = mc;

        verts[14] = c;
        verts[15] = reg.u2;
        verts[16] = reg.v;
        verts[17] = mc;

        verts[20] = c;
        verts[21] = reg.u;
        verts[22] = reg.v;
        verts[23] = mc;

        Draw.vert(reg.texture, verts, 0, verts.length);
    }
    @Override
    public boolean synthetic(){
        return true;
    }
}
