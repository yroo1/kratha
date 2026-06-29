package kratha.world.blocks.environment;

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
    }

    @Override
    public void drawMain(Tile tile){
        if(checkAdjacent(tile)){
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx() - tilesize, tile.worldy() - tilesize);
        }
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
