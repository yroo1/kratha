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
import kratha.content.KrathaItems;
import kratha.content.KrathaLiquids;

import static mindustry.type.ItemStack.with;

public class KrathaEnv {
    public static Block
            terrasporeTree,terrasporeFern,
            krathiteStaticwall,krathiteFloor,krathitePlated,krathiteKrathagen,krathiteBoulder,
            terrastoneWall,terrastoneErodedWall,terrastoneFloor,terrastoneEroded,terrastoneErodedBoulder,
            krathagenFloor,krathagenDeep;
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
                terrasporeTree = new KrathaTree("terraspore-tree"){{
                    variants = 2;
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
                terrastoneEroded = new Floor("terrastone-eroded", 4);
                terrastoneErodedBoulder = new Prop("terrastone-eroded-boulder"){{
                    variants = 2;
                    terrastoneEroded.asFloor().decoration = this;
                }};
                //krathagen
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
            }
        }
    }
}
