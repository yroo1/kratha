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
        float s = tilesize/2f;
        float x = tile.worldx(), y = tile.worldy();
        float c = Color.white.toFloatBits();
        float mc = Color.clearFloatBits;

        verts[0] = x - s;
        verts[1] = y - s;
        verts[2] = c;
        verts[3] = region.u;
        verts[4] = region.v2;
        verts[5] = mc;

        verts[6] = x + s;
        verts[7] = y - s;
        verts[8] = c;
        verts[9] = region.u2;
        verts[10] = region.v2;
        verts[11] = mc;

        verts[12] = x + s;
        verts[13] = y + s;
        verts[14] = c;
        verts[15] = region.u2;
        verts[16] = region.v;
        verts[17] = mc;

        verts[18] = x - s;
        verts[19] = y + s;
        verts[20] = c;
        verts[21] = region.u;
        verts[22] = region.v;
        verts[23] = mc;

        Draw.vert(region.texture, verts, 0, verts.length);
    }
  
    @Override
    public boolean synthetic(){
        return true;
    }
}
