package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds.*;
import mindustry.content.Fx;
import mindustry.world.meta.*;
import mindustry.graphics.Layer;
import kratha.content.KrathaItems;
import kratha.content.KrathaLiquids;
import kratha.world.blocks.environment.*;

import static mindustry.type.ItemStack.with;

public class KrathaEnv {
    public static Block
            darkness,
            terrasporeTree,terrasporeTreeLarge,woodWall,woodFloor,terrasporeFern,terrasporeFernLarge,
            container,
            krathiteStaticwall,krathiteRough,krathiteFloor,krathitePlated,krathiteKrathagen,krathiteBoulder,krathiteBoulderLarge,
            terrastoneWall,terrastoneErodedWall,terrastoneGrassy,terrastoneFloor,terrastoneEroded,terrastoneWatra,terrastoneBoulder,terrastoneErodedBoulder,terrastoneErodedVent,
            krathagenFloor,krathagenDeep,krathagenWall,
            watraShallow,watraDeep,
            fallenLeaves,pebbles,krathiteOreWall,guartzOre,cobaltOre;
    public static void load() {
        {
            {
                //darkness
                darkness = new Block("darkness"){{
                    requirements(Category.effect, with());
                    forceDark = true;
                    targetable = false;
                    solid = false;
                    update = false;
                    fillsTile = false;
                    breakable = false;
                    drawTeamOverlay = false;
                    destructible = false;
                    hasShadow = false;
                    buildVisibility = BuildVisibility.sandboxOnly;
                }};
                //terraspore
                terrasporeFern = new SeaBush("terraspore-fern"){{
                    lobesMin = 4;
                    lobesMax = 5;
                    magMin = 5;
                    magMax = 7;
                }};
                terrasporeFernLarge = new SeaBush("terraspore-fern-large"){{
                    lobesMin = 5;
                    lobesMax = 7;
                    magMin = 2;
                    magMax = 4;
                    clipSize = 200;
                }};
                terrasporeTree = new KrathaTree("terraspore-tree"){{
                    variants = 2;
                    clipSize = 9999;
                    layer = Layer.darkness + 1;
                }};
                terrasporeTreeLarge = new KrathaTree("terraspore-tree-large"){{
                    variants = 0;
                    lobesMin = 4;
                    lobesMax = 6;
                    layer = Layer.darkness + 2;
                    clipSize = 9999;
                    parallaxAmount = 150;
                    branchParallaxAmount = 90;
                }};
                woodWall = new StaticWall("wood-wall");
                woodFloor = new Floor("wood-floor", 4);
                //misc
                container = new EnvContainer("container"){{
                    fadeAmount = 0f;
                    layer = Layer.power - 1.01f;
                }};
                //krathite
                krathiteStaticwall = new StaticWall("krathite-staticwall");
                krathiteRough = new Floor("krathite-rough", 4);
                krathiteFloor = new Floor("krathite-floor", 4);
                krathitePlated = new Floor("krathite-plated", 4);
                krathiteKrathagen = new Floor("krathite-krathagen"){{
                    speedMultiplier = 0.9f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.8f;
                }};
                krathiteBoulder = new Prop("krathite-boulder"){{
                    variants = 2;
                    krathiteFloor.asFloor().decoration = this;
                    krathitePlated.asFloor().decoration = this;
                    krathiteKrathagen.asFloor().decoration = this;
                }};
                krathiteBoulderLarge = new TallBlock("krathite-boulder-large"){{
                    variants = 2;
                }};
                //terrastone
                terrastoneWall = new StaticTree("terrastone-wall"){{variants=5;}};
                terrastoneErodedWall = new StaticWall("terrastone-eroded-wall"){{
                    attributes.set(Attribute.sand, 1f);
                }};
                terrastoneFloor = new Floor("terrastone-floor", 4);
                terrastoneGrassy = new Floor("terrastone-grassy", 4);
                terrastoneEroded = new Floor("terrastone-eroded", 4);
                terrastoneBoulder = new Prop("terrastone-boulder"){{
                    variants = 2;
                    terrastoneFloor.asFloor().decoration = this;
                }};
                terrastoneErodedBoulder = new Prop("terrastone-eroded-boulder"){{
                    variants = 2;
                    terrastoneEroded.asFloor().decoration = this;
                }};
                terrastoneErodedVent = new SteamVent("terrastone-eroded-vent"){{
                    parent = blendGroup = terrastoneEroded;
                    attributes.set(Attribute.steam, 1f);
                }};
                terrastoneWatra = new Floor("terrastone-watra"){{
                    speedMultiplier = 0.9f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.muddyWatra;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.8f;
                }};
                //krathagen
                krathagenWall = new StaticWall("krathagen-wall");
                krathagenFloor = new Floor("krathagen-floor"){{
                    speedMultiplier = 0.8f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.9f;
                    supportsOverlay = false;
                }};
                krathagenDeep = new Floor("krathagen-deep"){{
                    speedMultiplier = 0.6f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = false;
                    drownTime = 120;
                }};
                //watra
                watraShallow = new Floor("watra-shallow"){{
                    speedMultiplier = 0.8f;
                    variants = 3;
                    liquidDrop = KrathaLiquids.watra;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = false;
                }};
                watraDeep = new Floor("watra-deep"){{
                    speedMultiplier = 0.6f;
                    variants = 3;
                    liquidDrop = KrathaLiquids.watra;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 1f;
                    supportsOverlay = false;
                    drownTime = 120;
                }};
                //overlays
                fallenLeaves = new OverlayFloor("fallen-leaves");
                pebbles = new OverlayFloor("pebbles");
                //ores
                krathiteOreWall = new StaticWall("krathite-ore-wall"){{
                    itemDrop = KrathaItems.krathite;
                }};
                guartzOre = new OreBlock("guartz-ore",KrathaItems.guartz);
                cobaltOre = new OreBlock("cobalt-ore",KrathaItems.cobalt);
            }
        }
    }
}
