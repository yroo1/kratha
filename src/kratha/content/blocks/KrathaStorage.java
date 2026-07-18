package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.storage.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import mindustry.content.*;
import kratha.content.*;
import kratha.world.blocks.storage.*;

import static mindustry.type.ItemStack.with;

public class KrathaStorage {
    public static Block
            coreRelic;
    public static void load() {
        {
            {
                coreRelic = new KrathaCoreBlock("core-relic"){{
                    requirements(Category.effect, with(KrathaItems.krathite, 700,KrathaItems.guartz, 600,KrathaItems.spurstone, 200));
                    size = 5;
                    isFirstTier = true;
                    squareSprite = false;
                    itemCapacity = 4000;
                    health = 5500;
                    unitType = KrathaUnitTypes.settler;
                    alwaysUnlocked = true;
                    thrusterLength = 40/4f;
                }};
            }
        }
    }
}
