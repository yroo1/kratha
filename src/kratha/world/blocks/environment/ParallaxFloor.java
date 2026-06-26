package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.*;

public class ParallaxFloor extends Floor{
  public float parallaxAmount = -100;
    public ParallaxFloor(String name){
        super(name);
    }
    @Override
    public void drawMain(Tile tile){
        Draw.z(Layer.floor+(parallaxAmount>0?0.01f:-0.01f));
        float camoffX=(tile.worldx()-Core.camera.position.x)*((parallaxAmount)/Core.camera.width);
        float camoffY=(tile.worldy()-Core.camera.position.y)*((parallaxAmount)/Core.camera.width);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            tile.worldx()+camoffX, tile.worldy()+camoffY);
    }
}
