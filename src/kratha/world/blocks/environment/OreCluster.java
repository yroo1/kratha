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
import mindustry.type.*;

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
        clipSize = 100;
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
        super.setBars();

        addBar("drilllimit", (OreClusterBuild e) ->
             new Bar(() -> Core.bundle.format("kratha.drilllimit", Strings.fixed(3, 2)), () -> Pal.ammo, () -> e.drillCount));
    }

    @Override
    public void drawBase(Tile tile){
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
        tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot);

        Draw.color();

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx(), tile.worldy(), rot);
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
    public boolean synthetic(){
        return false;
    }

    public class OreClusterBuild extends Building{
        public int drillCount = 0;
        
    }
            }
