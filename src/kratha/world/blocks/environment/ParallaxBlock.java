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
        Draw.z(Layer.floor+(parallaxAmount>0?0.01f:-0.01f));
        float camoffX1=(tile.worldx()-tilesize/2-Core.camera.position.x)*((parallaxAmount)/Core.camera.width);
        float camoffY1=(tile.worldy()-tilesize/2-Core.camera.position.y)*((parallaxAmount)/Core.camera.width);
        float camoffX2=(tile.worldx()+tilesize/2-Core.camera.position.x)*((parallaxAmount)/Core.camera.width);
        float camoffY2=(tile.worldy()-tilesize/2-Core.camera.position.y)*((parallaxAmount)/Core.camera.width);
        float camoffX3=(tile.worldx()-tilesize/2-Core.camera.position.x)*((parallaxAmount)/Core.camera.width);
        float camoffY3=(tile.worldy()+tilesize/2-Core.camera.position.y)*((parallaxAmount)/Core.camera.width);
        float camoffX4=(tile.worldx()+tilesize/2-Core.camera.position.x)*((parallaxAmount)/Core.camera.width);
        float camoffY4=(tile.worldy()+tilesize/2-Core.camera.position.y)*((parallaxAmount)/Core.camera.width);

        float c = Color.toFloatBits(1,1,1,1);
        Draw.quad(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            tile.worldx()-tilesize/2+camoffX1, tile.worldy()-tilesize/2+camoffY1, c,
            tile.worldx()+tilesize/2+camoffX2, tile.worldy()-tilesize/2+camoffY2, c,
            tile.worldx()-tilesize/2+camoffX3, tile.worldy()+tilesize/2+camoffY3, c,
            tile.worldx()+tilesize/2+camoffX4, tile.worldy()+tilesize/2+camoffY4, c);
        
    }
    @Override
    public boolean synthetic(){
        return true;
    }
}
