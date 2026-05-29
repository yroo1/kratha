package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
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
            terrasporeTree,terrasporeTreeLarge,terrasporeFern,terrasporeFernLarge,
            krathiteStaticwall,krathiteFloor,krathitePlated,krathiteKrathagen,krathiteBoulder,
            terrastoneWall,terrastoneErodedWall,terrastoneGrassy,terrastoneFloor,terrastoneEroded,terrastoneBoulder,terrastoneErodedBoulder,
            krathagenFloor,krathagenDeep,krathagenWall,
            fallenLeaves,pebbles,krathiteOreWall,guartzOre;
    public static void load() {
        {
            {
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
                    clipsize = 200;
                }};
                terrasporeTree = new KrathaTree("terraspore-tree"){{
                    variants = 2;
                    clipsize = 9999;
                }};
                terrasporeTreeLarge = new KrathaTree("terraspore-tree-large"){{
                    variants = 0;
                    lobesMin = 4;
                    lobesMax = 6;
                    layer = Layer.power + 2;
                    clipsize = 9999;
                }};
                //krathite
                krathiteStaticwall = new StaticWall("krathite-staticwall");
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
                //terrastone
                terrastoneWall = new StaticTree("terrastone-wall"){{variants=5;}};
                terrastoneErodedWall = new StaticWall("terrastone-eroded-wall");
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
                }};
                //overlays
                fallenLeaves = new OverlayFloor("fallen-leaves");
                pebbles = new OverlayFloor("pebbles");
                //ores
                krathiteOreWall = new StaticWall("krathite-ore-wall");
                guartzOre = new OreBlock("guartz-ore",KrathaItems.guartz);
            }
        }
    }
}
