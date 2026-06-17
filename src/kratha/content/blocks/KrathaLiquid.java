package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import mindustry.content.*;
import kratha.world.blocks.liquid.*;
import kratha.content.KrathaItems;
import kratha.graphics.KrathaPal;

import static mindustry.type.ItemStack.with;

public class KrathaLiquid {
    public static Block
            liquidTube;
    public static void load() {
        {
            {
                liquidTube = new LiquidTube("liquid-tube"){{
                    requirements(Category.liquid, with(KrathaItems.krathite, 2));
                    botColor = KrathaPal.krathaBaseDarker;
                    researchCost = with(KrathaItems.krathite, 100);
                }};
            }
        }
    }
                      }
