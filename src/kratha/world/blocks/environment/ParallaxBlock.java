package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class ParallaxBlock extends TallBlock{
    private static float[] verts = new float[4*6];
    public float parallaxAmount = -100;
    public String floorName;
    public int depthFlag;
    public TextureRegion[] wallRegions = new TextureRegion[6];

    private final float parallaxMultiplier = 1f/2000;
    public ParallaxBlock(String name){
        super(name);
        forceDark = false;
        clipSize = 9999;
        update = true;
        drawTeamOverlay = false;
        targetable = false;
    }
    @Override
    public void init(){
        super.init();
        hasShadow = false;
        solid = false;
    }
    @Override
    public void load(){
        super.load();
        wallRegions[0] = Core.atlas.find(name+"-mid-to-sur");
        wallRegions[1] = Core.atlas.find(name+"-dep-to-mid");
        wallRegions[2] = Core.atlas.find(name+"-dep-to-sur");
        wallRegions[3] = Core.atlas.find(name+"-vod-to-dep");
        wallRegions[4] = Core.atlas.find(name+"-vod-to-mid");
        wallRegions[5] = Core.atlas.find(name+"-vod-to-sur");
    }
    @Override
    public void createIcons(MultiPacker packer){
        super.createIcons(packer);
        mapColor = Color.black;
    }
    @Override
    public void drawBase(Tile tile){
        Draw.z(Layer.floor-0.51f+depthFlag*0.1f);
        Block f = tile.floor();
        float p = renderer.getDisplayScale()*parallaxAmount*parallaxMultiplier;
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

        Draw.z(Layer.floor-0.5f+depthFlag*0.1f);
        if(tile.nearby(0,1)!=null&&tile.nearby(0,1).floor()!=f)drawSide(tile,0,tile.nearby(0,1));
        if(tile.nearby(-1,0)!=null&&tile.nearby(-1,0).floor()!=f)drawSide(tile,1,tile.nearby(-1,0));
        if(tile.nearby(0,-1)!=null&&tile.nearby(0,-1).floor()!=f)drawSide(tile,2,tile.nearby(0,-1));
        if(tile.nearby(1,0)!=null&&tile.nearby(1,0).floor()!=f)drawSide(tile,3,tile.nearby(1,0));
    }
    public void drawSide(Tile tile,int r,Tile ntile){
        //rotation order : up left down right
        float p = renderer.getDisplayScale()*parallaxAmount*parallaxMultiplier;
        float cx = Core.camera.position.x, cy = Core.camera.position.y;
        
        float s = tilesize/2f;
        float x = tile.worldx(), y = tile.worldy();
        float c = Color.white.toFloatBits();
        float mc = Color.clearFloatBits;
        Block nblock=ntile.block();
        Block nfloor=ntile.floor();
        int ndepth = -1;
        if(nblock!=null&&nblock instanceof ParallaxBlock pf)ndepth=pf.depthFlag;
        boolean toSur = !(nfloor instanceof ParallaxFloor);
        boolean deeper = !(nfloor instanceof ParallaxFloor)||(nblock!=null&&nblock instanceof ParallaxBlock pf&&pf.depthFlag>depthFlag);
        TextureRegion reg = region;
        if(depthFlag==2){
            reg = wallRegions[0];
        }
        if(depthFlag==1){
            if(!toSur&&ndepth==2) reg = wallRegions[1];
            if(toSur) reg = wallRegions[2];
        }
        if(depthFlag==0){
            if(!toSur&&ndepth==1) reg = wallRegions[3];
            if(!toSur&&ndepth==2) reg = wallRegions[4];
            if(toSur) reg = wallRegions[5];
        }
        float p2 = 0;
        if(!toSur&&nblock instanceof ParallaxBlock pf)p2 = renderer.getDisplayScale()*pf.parallaxAmount*parallaxMultiplier;

        if(!deeper)return;
        boolean shouldDraw = false;
        //i sure do love assigning everything manually
        if(r==0&&(y+s+(y+s-cy)*p)<(y+s+(y+s-cy)*p2)){
            verts[0] = x - s + (x-s-cx)*p;
            verts[1] = y + s + (y+s-cy)*p;
            verts[6] = x + s + (x+s-cx)*p;
            verts[7] = y + s + (y+s-cy)*p;
            verts[12] = x + s + (x+s-cx)*p2;
            verts[13] = y + s + (y+s-cy)*p2;
            verts[18] = x - s + (x-s-cx)*p2;
            verts[19] = y + s + (y+s-cy)*p2;
            shouldDraw = true;
        }
        if(r==1&&(x-s+(x-s-cx)*p)>(x-s+(x-s-cx)*p2)){
            verts[0] = x - s + (x-s-cx)*p;
            verts[1] = y + s + (y+s-cy)*p;
            verts[6] = x - s + (x-s-cx)*p;
            verts[7] = y - s + (y-s-cy)*p;
            verts[12] = x - s + (x-s-cx)*p2;
            verts[13] = y - s + (y-s-cy)*p2;
            verts[18] = x - s + (x-s-cx)*p2;
            verts[19] = y + s + (y+s-cy)*p2;
            shouldDraw = true;
        }
        if(r==2&&(y-s+(y-s-cy)*p)>(y-s+(y-s-cy)*p2)){
            verts[0] = x + s + (x+s-cx)*p;
            verts[1] = y - s + (y-s-cy)*p;
            verts[6] = x - s + (x-s-cx)*p;
            verts[7] = y - s + (y-s-cy)*p;
            verts[12] = x - s + (x-s-cx)*p2;
            verts[13] = y - s + (y-s-cy)*p2;
            verts[18] = x + s + (x+s-cx)*p2;
            verts[19] = y - s + (y-s-cy)*p2;
            shouldDraw = true;
        }
        if(r==3&&(x+s+(x+s-cx)*p)<(x+s+(x+s-cx)*p2)){
            verts[0] = x + s + (x+s-cx)*p;
            verts[1] = y - s + (y-s-cy)*p;
            verts[6] = x + s + (x+s-cx)*p;
            verts[7] = y + s + (y+s-cy)*p;
            verts[12] = x + s + (x+s-cx)*p2;
            verts[13] = y + s + (y+s-cy)*p2;
            verts[18] = x + s + (x+s-cx)*p2;
            verts[19] = y - s + (y-s-cy)*p2;
            shouldDraw = true;
            
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

        if(shouldDraw)Draw.vert(reg.texture, verts, 0, verts.length);
    }
    @Override
    public boolean synthetic(){
        return true;
    }
    public boolean isAccessible(){
        return false;
    }
    
    public class ParallaxBuild extends Building{
        @Override
        public void unitOn(Unit unit){
            if(unit.isGrounded())unit.kill();
        }
        @Override
        public void damage(float damage){
            return; //no damage
        }

        @Override
        public boolean canPickup(){
            return false; //no
        }

        @Override
        public boolean collide(Bullet other){
            return false; //no
        }
        
    }
}
