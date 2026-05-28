package kratha.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.world.*;

public class KrathaTree extends TallBlock{
    //why
    public TextureRegion branchRegion1;
    public TextureRegion branchRegion2;
    public TextureRegion branchRegion1bot;
    public TextureRegion branchRegion2bot;
    
    public int lobesMin = 2, lobesMax = 4;
    public float botAngle = 60f, origin = 0.1f;
    public float sclMin = 30f, sclMax = 50f, magMin = 5f, magMax = 15f, timeRange = 40f, spread = 0f;

    static Rand rand = new Rand();

    public KrathaTree(String name){
        super(name);
        variants = 2;
        obstructsLight = true;
    }
    @Override void load(){
      super.load()
      branchRegion1=Core.atlas.find(name+"-branch1");
      branchRegion2=Core.atlas.find(name+"-branch2");
      branchRegion1bot=Core.atlas.find(name+"-branch1-bot");
      branchRegion2bot=Core.atlas.find(name+"-branch2-bot");
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        rand.setSeed(tile.pos());
        float offset = rand.random(180f);
        int lobes = rand.random(lobesMin, lobesMax);
        for(int i = 0; i < lobes; i++){
            float ba =  i / (float)lobes * 360f + offset + rand.range(spread), angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
            float w = region.width * region.scl(), h = region.height * region.scl();
            int variant = rand.random(0, 2);
            var region = Angles.angleDist(ba, 225f) <= botAngle ? (variant>1?branchRegion1bot:branchRegion2bot) : (variant>1?branchRegion1:branchRegion2);

            Draw.rect(region,
                tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, tile.worldy() - Angles.trnsy(angle, origin),
                w, h,
                origin*4f, h/2f,
                angle
            );
        }
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
}
