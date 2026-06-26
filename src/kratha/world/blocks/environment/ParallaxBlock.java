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
    public float parallaxAmount = -100;
    public ParallaxBlock(String name){
        super(name);
        forceDark = false;
        hasShadow = false;
        solid = false;
    }
    @Override
    public void drawBase(Tile tile){
        //shit code
        float s = Vars.tilesize/2f;
        float x = tile.worldx(), y = tile.worldy();
        float[] verts = new float[24];
        float c = Color.white.toFloatBits;

        verts[0] = x - s;
        verts[1] = y - s;
        verts[2] = c;
        verts[3] = region.u;
        verts[4] = region.v2;

        verts[5] = x + s;
        verts[6] = y - s;
        verts[7] = c;
        verts[8] = region.u2;
        verts[9] = region.v2;

        verts[10] = x + s;
        verts[11] = y + s;
        verts[12] = c;
        verts[13] = region.u2;
        verts[14] = region.v;

        verts[15] = x - s;
        verts[16] = y + s;
        verts[17] = c;
        verts[18] = region.u;
        verts[19] = region.v;

        Draw.vert(region.texture, verts, 0, verts.length);
    }
  
    @Override
    public boolean synthetic(){
        return true;
    }
}
