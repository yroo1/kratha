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

    public OreCluster(String name){
        super(name);
        variants = 2;
        solid = true;
        clipSize = 90;
        customShadow = true;
        size = 3;
    }

    public class OreClusterBuild extends Building{
        @Override
        public void draw(){
            if(!isCenter(tile))return;
            float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

            Draw.z(shadowLayer);
            Draw.color(0f, 0f, 0f, shadowAlpha);
            Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
            x + shadowOffset, y + shadowOffset, rot);

            Draw.color();

            Draw.z(layer);
            Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            x, y, rot);
        }
    }
            }
