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

public class DeterraEnv {
    public static Block
            krathiteStaticwall,krathiteFloor,krathitePlated,krathiteKrathagen,krathiteBoulder,
            krathagenFloor,krathagenDeep;
    public static void load() {
        {
            {
                //krathite
                krathiteStaticwall = new StaticWall("krathite-staticwall");
                krathiteFloor = new Floor("krathite-floor", 4);
                krathitePlated = new Floor("eonstone-lightly-eroded-floor", 4);
                krathiteBoulder = new Prop("eonstone-boulder"){{
                    variants = 2;
                    krathiteFloor.asFloor().decoration = this;
                    krathitePlated.asFloor().decoration = this;
                }};
                krathiteKrathagen = new Floor("krathite-krathagen"){{
                    speedMultiplier = 0.9f;
                    variants = 4;
                    liquidDrop = KrathaLiquids.krathagen;
                    isLiquid = true;
                    cacheLayer = CacheLayer.water;
                    albedo = 0.8f;
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
