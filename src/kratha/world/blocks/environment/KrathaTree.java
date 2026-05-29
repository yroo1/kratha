package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.Vars;

//BranchedTree probably would be better
public class KrathaTree extends TallBlock{
    //why
    public TextureRegion branchRegion1;
    public TextureRegion branchRegion2;
    public TextureRegion branchRegion1bot;
    public TextureRegion branchRegion2bot;
    public TextureRegion branchRegion1s;
    public TextureRegion branchRegion2s;
    
    public int lobesMin = 4, lobesMax = 7;
    public float botAngle = 50f, origin = 0.1f;
    public float sclMin = 300f, sclMax = 360f, magMin = 5f, magMax = 15f, timeRange = 40f, spread = 0f;
    public float fadeDist = 60f, fadeDistTo = 40f, fadeAmount=0.75f; //fade amount 1 means 100% 0 means no fade

    static Rand rand = new Rand();

    public KrathaTree(String name){
        super(name);
        variants = 2;
        obstructsLight = true;
    }
    @Override
    public void load(){
      super.load();
      branchRegion1=Core.atlas.find(name+"-branch1");
      branchRegion2=Core.atlas.find(name+"-branch2");
      branchRegion1bot=Core.atlas.find(name+"-branch1-bot");
      branchRegion2bot=Core.atlas.find(name+"-branch2-bot");
      branchRegion1s=Core.atlas.find(name+"-branch1-shadow");
      branchRegion2s=Core.atlas.find(name+"-branch2-shadow");
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        rand.setSeed(tile.pos());
        float offset = rand.random(180f);
        int lobes = rand.random(lobesMin, lobesMax);
        for(int i = 0; i < lobes; i++){
            float ba =  i / (float)lobes * 360f + offset + rand.range(spread), angle = ba + Mathf.sin(Time.time + rand.random(0, timeRange), rand.random(sclMin, sclMax), rand.random(magMin, magMax));
            //variant 2 of branch will be choosen 2/3 of the time instead of 1/2, intended.
            int variant = rand.random(0, 2);
            var bReg = (variant>1?branchRegion1:branchRegion2);
            float w = bReg.width * bReg.scl(), h = bReg.height * bReg.scl();
            var region = Angles.angleDist(ba, 225f) <= botAngle ? (variant>1?branchRegion1bot:branchRegion2bot) : (variant>1?branchRegion1:branchRegion2);
            var sRegion = (variant>1?branchRegion1s:branchRegion2s);
            
            Draw.color(0f, 0f, 0f, shadowAlpha);
            if(sRegion!=null){
                Draw.z(shadowLayer);
                Draw.rect(sRegion,
                    tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f, tile.worldy() - Angles.trnsy(angle, origin),
                    w, h,
                    origin*4f, h/2f,
                    angle
                );    
            }
            float bAlpha=1f;
            if(Vars.player.unit()!=null&&!Vars.player.unit().dead()){
                tAlpha=Math.max(0,Math.min(fadeDist-fadeDistTo,Mathf.dst(tile.worldx() - Angles.trnsx(angle, origin) + w*0.5f,tile.worldy() - Angles.trnsy(angle, origin),Vars.player.unit().x,Vars.player.unit().y)-fadeDistTo)/(fadeDist-fadeDistTo)*fadeAmount+(1-fadeAmount);
            }
            Draw.color(1f,1f,1f,bAlpha);
            Draw.z(layer);
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
        float tAlpha=1f;
        if(Vars.player.unit()!=null&&!Vars.player.unit().dead()){
            tAlpha=Math.max(0,Math.min(fadeDist-fadeDistTo,Mathf.dst(tile.worldx(),tile.worldy(),Vars.player.unit().x,Vars.player.unit().y)-fadeDistTo)/(fadeDist-fadeDistTo)*fadeAmount+(1-fadeAmount);
        }
        Draw.color(1f,1f,1f,tAlpha);

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
            tile.worldx(), tile.worldy(), rot);
    }
}
