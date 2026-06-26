package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
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
        Draw.z(Layer.floor+(parallaxAmount>0?0.01f:-0.01f));
        float camoffX=(tile.worldx()-Core.camera.position.x)*((parallaxAmount)/Core.camera.width);
        float camoffY=(tile.worldy()-Core.camera.position.y)*((parallaxAmount)/Core.camera.width);
        float scl=parallaxAmount/Core.camera.width;
        float sx=x-scale*0.5f;
        float sy=y-scale*0.5f;
        Draw.scl(scale,scale)
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            tile.worldx()+camoffX+sx, tile.worldy()+camoffY+sy);
        Draw.scl(1,1);
    }
    @Override
    public boolean synthetic(){
        return true;
    }
}
