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
    public float[] vertices = new float[24];
    public ParallaxBlock(String name){
        super(name);
        forceDark = false;
        hasShadow = false;
        solid = false;
        for(int i = 0; i < 4; i++){
            vertices[i * 6 + 2] = Color.white.toFloatBits();
            vertices[i * 6 + 5] = Color.clearFloatBits;
        }
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

        setPos(0,tile.worldx()-tilesize/2,tile.worldy()-tilesize/2);
        setPos(1,tile.worldx()+tilesize/2,tile.worldy()-tilesize/2);
        setPos(2,tile.worldx()+tilesize/2,tile.worldy()+tilesize/2);
        setPos(3,tile.worldx()-tilesize/2,tile.worldy()+tilesize/2);
        Draw.vert(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,, vertices, 0, vertices.length);
    }
  
    private void setPos(int i, float x, float y){
        if(i >= 0 && i < 4){
            if(!Float.isNaN(x)) vertices[i * 6] = x * tilesize;
            if(!Float.isNaN(y)) vertices[i * 6 + 1] = y * tilesize;
        }
    }
  
    @Override
    public boolean synthetic(){
        return true;
    }
}
