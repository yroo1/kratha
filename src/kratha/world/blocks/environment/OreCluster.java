package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.environment.*;

import static mindustry.Vars.*;

public class OreCluster extends Block{
    public float shadowOffset = -3f;
    public float layer = Layer.power + 1;
    public float shadowLayer = Layer.power - 1;
    public float rotationRand = 20f;
    public float shadowAlpha = 0.6f;
    public static final Point2[] offsets = {
        new Point2(0, 0),
        new Point2(1, 0),
        new Point2(1, 1),
        new Point2(0, 1),
        new Point2(-1, 1),
        new Point2(-1, 0),
        new Point2(-1, -1),
        new Point2(0, -1),
        new Point2(1, -1),
    };

    static{
        for(var p : offsets){
            p.sub(1, 1);
        }
    }

    public OreCluster(String name){
        super(name);
        variants = 2;
        solid = true;
        clipSize = 90;
        customShadow = true;
    }

    @Override
    public void drawBase(Tile tile){
        if(isCenter(tile));
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
            tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot);

        Draw.color();

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            tile.worldx(), tile.worldy(), rot);
    }

    @Override
    public void drawShadow(Tile tile){

    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }

    public boolean isCenter(Tile tile){
        Tile topRight = tile.nearby(1, 1);
        return topRight != null && topRight.block() == tile.block() && checkAdjacent(topRight);
    }
    
    //note that only the top right tile works for this; render order reasons.
    //yes anuke
    public boolean checkAdjacent(Tile tile){
        for(var point : offsets){
            Tile other = Vars.world.tile(tile.x + point.x, tile.y + point.y);
            if(other == null || other.block() != this){
                return false;
            }
        }
        return true;
    }
          }
