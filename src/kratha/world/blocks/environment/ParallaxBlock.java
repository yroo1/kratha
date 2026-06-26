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
    public void drawBase(Tile tile){
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
    }
  
    @Override
    public boolean synthetic(){
        return true;
    }
}
