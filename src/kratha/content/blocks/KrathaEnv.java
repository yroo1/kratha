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
import kratha.graphics.KrathaPal;

import static mindustry.type.ItemStack.with;

public class KrathaEnv {
    public static Block
            darkness,
            arkteraWallA,arkteraWallB,arkteraWallBOff,arkteraTileA,arkteraTileB,arkteraTileC,arkteraTileD,arkteraTileVent,
            terrasporeTree,terrasporeTreeLarge,woodWall,woodFloor,terrasporeFern,terrasporeFernLarge,
            container,
            krathiteStaticwall,krathiteRough,krathiteFloor,krathitePlated,krathiteKrathagen,krathiteBoulder,krathiteBoulderLarge,krathiteVent,
            akrockWall,akacyteFloor,akrock,akrockPlated,akrockBoulder,akrockBoulderLarge,
            terrastoneWall,terrastoneErodedWall,terrastoneGrassy,terrastoneFloor,terrastoneEroded,terrastoneWatra,terrastoneBoulder,terrastoneErodedBoulder,terrastoneErodedVent,
            krathagenFloor,krathagenDeep,krathagenDeeper,krathagenDeepest,krathagenVoid,krathagenDeeperBlock,krathagenDeepestBlock,krathagenVoidBlock,krathagenWall,
            watraShallow,watraDeep,
            fallenLeavesLegacy,fallenLeaves,lilypad,plant,flowerGreen,flowerPink,flowerRed,pebbles,krathiteOreWall,guartzOre,guartzCluster,guartzClusterSmall,cobaltOre,anemiteOre,arkscrapOre;
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
                arkteraWallA = new StaticWall("arktera-wall-a");
                arkteraWallB = new Prop("arktera-wall-b"){{
                    emitLight = true;
                    lightRadius = 30f;
                    variants = 0;
                    lightColor = KrathaPal.arkteraOrange.a(0.5f);
                    solid = true;
                    breakable = false;
                    alwaysReplace = false;
                }};
                arkteraWallBOff = new Prop("arktera-wall-b-off"){{
                    emitLight = false;
                    variants = 0;
                    solid = true;
                    breakable = false;
                    alwaysReplace = false;
                }};
                arkteraTileA = new Floor("arktera-tile-a", 4){{
                    drawEdgeOut = false;
                }};
                arkteraTileB = new Floor("arktera-tile-b", 0){{
                    drawEdgeOut = false;
                    blendGroup = arkteraTileA;
                }};
                arkteraTileC = new Floor("arktera-tile-c", 2){{
                    drawEdgeOut = false;
                    blendGroup = arkteraTileA;
                }};
                arkteraTileD = new Floor("arktera-tile-d", 4){{
                    drawEdgeIn = false;
                }};
                arkteraTileVent = new SteamVent("arktera-tile-vent"){{
                    blendGroup = arkteraTileA;
                    parent = arkteraTileC;
                    attributes.set(Attribute.steam, 1f);
                    variants = 0;
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
                    shadowLayer = Layer.power - 1.02f;
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
                krathiteVent = new SteamVent("krathite-vent"){{
                    parent = blendGroup = krathiteFloor;
                    attributes.set(Attribute.steam, 1f);
                }};
                //赤rock
                akrockWall = new StaticWall("akrock-wall");
                akacyteFloor = new Floor("akacyte-floor"){{
                    speedMultiplier = 0.95f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.akacyte;
                    isLiquid = true;
                    cacheLayer = CacheLayer.mud;
                    albedo = 1f;
                }};
                akrock = new Floor("akrock", 4);
                akrockPlated = new Floor("akrock-plated", 4);
                akrockBoulder = new Prop("akrock-boulder"){{
                    variants = 2;
                    akrock.asFloor().decoration = this;
                    akrockPlated.asFloor().decoration = this;
                }};
                akrockBoulderLarge = new TallBlock("akrock-boulder-large"){{
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
                    supportsOverlay = true;
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
                    supportsOverlay = true;
                }};
                krathagenDeep = new Floor("krathagen-deep"){{
                    speedMultiplier = 0.6f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = true;
                    drownTime = 120;
                    blendGroup = krathagenFloor;
                }};
                krathagenDeeper = new ParallaxFloor("krathagen-deeper"){{
                    speedMultiplier = 0.3f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = true;
                    drownTime = 1;
                    parallaxBlock = krathagenDeeperBlock;
                    blendGroup = krathagenFloor;
                }};
                krathagenDeepest = new ParallaxFloor("krathagen-deepest"){{
                    speedMultiplier = 0.2f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = true;
                    drownTime = 1;
                    parallaxBlock = krathagenDeepestBlock;
                    blendGroup = krathagenFloor;
                }};
                krathagenVoid = new ParallaxFloor("krathagen-void"){{
                    speedMultiplier = 0.1f;
                    variants = 0;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = true;
                    drownTime = 1;
                    parallaxBlock = krathagenVoidBlock;
                    blendGroup = krathagenFloor;
                }};
                krathagenDeeperBlock = new ParallaxBlock("krathagen-deeper-block"){{
                    variants = 4;
                    parallaxAmount = -80;
                    floorName = "krathagen-deeper";
                    depthFlag = 2;
                }};
                krathagenDeepestBlock = new ParallaxBlock("krathagen-deepest-block"){{
                    variants = 4;
                    parallaxAmount = -150;
                    floorName = "krathagen-deepest";
                    depthFlag = 1;
                }};
                krathagenVoidBlock = new ParallaxBlock("krathagen-void-block"){{
                    variants = 0;
                    parallaxAmount = -180;
                    floorName = "krathagen-void";
                    depthFlag = 0;
                }};
                //watra
                watraShallow = new Floor("watra-shallow"){{
                    speedMultiplier = 0.8f;
                    variants = 3;
                    liquidDrop = KrathaLiquids.watra;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.95f;
                    supportsOverlay = true;
                }};
                watraDeep = new Floor("watra-deep"){{
                    speedMultiplier = 0.6f;
                    variants = 3;
                    liquidDrop = KrathaLiquids.watra;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 1f;
                    supportsOverlay = true;
                    drownTime = 120;
                }};
                //extra deco
                fallenLeavesLegacy = new OverlayFloor("fallen-leaves"){{variants=3;}}; //old version of fallen leaves that is OverlayFloor instead of Prop, this is kept so old files wont be corrupted
                fallenLeaves = new Prop("fallen-leaves-new"){{variants=3;hasShadow=false;layer=Layer.floor+0.01f;}};
                lilypad = new Prop("lilypad"){{variants=6;hasShadow=false;layer=Layer.floor+0.01f;}};
                plant = new Prop("plant"){{variants=3;hasShadow=false;}};
                flowerGreen = new Prop("flower-green"){{variants=3;hasShadow=false;}};
                flowerPink = new Prop("flower-pink"){{variants=3;hasShadow=false;}};
                flowerRed = new Prop("flower-red"){{variants=3;hasShadow=false;}};
                pebbles = new OverlayFloor("pebbles");
                //ores
                krathiteOreWall = new StaticWall("krathite-ore-wall"){{
                    itemDrop = KrathaItems.krathite;
                }};
                guartzOre = new OreBlock("guartz-ore",KrathaItems.guartz);
                guartzCluster = new OreCluster("guartz-cluster"){{
                    requirements(Category.effect, with());
                    variants = 2;
                    itemDrop = KrathaItems.guartz;
                }};
                guartzClusterSmall = new OreCluster("guartz-cluster-small"){{
                    requirements(Category.effect, with());
                    variants = 2;
                    size = 2;
                    maxDrillCount = 1;
                    itemDrop = KrathaItems.guartz;
                }};
                cobaltOre = new OreBlock("cobalt-ore",KrathaItems.cobalt);
                anemiteOre = new OreBlock("anemite-ore",KrathaItems.anemite);
                arkscrapOre = new OreBlock("arkscrap-ore",KrathaItems.arkscrap);
            }
        }
    }
}
