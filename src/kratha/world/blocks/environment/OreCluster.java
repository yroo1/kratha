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
import mindustry.gen.Building;
import kratha.world.blocks.production.OreClusterDrill;
import mindustry.type.*;
import mindustry.ui.*;
import arc.util.io.*;

import static mindustry.Vars.*;

public class OreCluster extends Block{
    public float shadowOffset = -3f;
    public float layer = Layer.power + 1;
    public float shadowLayer = Layer.power - 1;
    public float rotationRand = 20f;
    public float shadowAlpha = 0.6f;
    public int maxDrillCount = 3;

    public OreCluster(String name){
        super(name);
        variants = 2;
        solid = true;
        createRubble = false;
        size = 3;
        buildVisibility = BuildVisibility.editorOnly;
        clipSize = 100;
        update = true;
        drawTeamOverlay = false;
        targetable = false;
        customShadow = true;
    }

    @Override
    public void setBars(){
        addBar("drilllimit", (OreClusterBuild e) ->
             new Bar(() -> Core.bundle.format("kratha.drilllimit", e.drillCount, maxDrillCount), () -> Pal.ammo, () -> ((float)e.drillCount)/maxDrillCount));
    }

    @Override
    public void drawBase(Tile tile){
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

        float offset = size%2!=0?0:tilesize/2f;

        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
        tile.worldx() + shadowOffset + offset, tile.worldy() + shadowOffset + offset, rot);

        Draw.color();

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx() + offset, tile.worldy() + offset, rot);
    }

    @Override
    public void drawShadow(Tile tile){
        //no
    }

    @Override
    public boolean canBreak(Tile tile) {
        return state.isEditor();
    }
    
    @Override
    public int minimapColor(Tile tile){
        if(itemDrop==null)return 0;
        return itemDrop.color.rgba();
    }
    
    public class OreClusterBuild extends Building{
        public int drillCount = 0;

        public void updateDrillCount(){
            //this code is shitty, well, it does all that i need
            int s2 = 0;
            int frange = (int) Math.ceil(20)+1;
            for(int xm = -frange;xm<=frange;xm++){
                for(int ym = -frange;ym<=frange;ym++){
                    Tile othert = tile.nearby(xm,ym);
                    if(othert!=null&&othert.build!=null&&othert.build instanceof OreClusterDrill.OreClusterDrillBuild o&&o.link!=-1&&world.tile(o.link).build==this) {
                        if(othert.build.block.size==2){
                            s2++;
                        }
                    }
                }
            }
            drillCount = (int)Math.floor((float)s2/4);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(drillCount);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            drillCount = read.i();
        }
    }
            }
