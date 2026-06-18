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
            liquidTube,liquidOverpass,liquidCell
    public static void load() {
        {
            {
                liquidTube = new LiquidTube("liquid-tube"){{
                    requirements(Category.liquid, with(KrathaItems.kitegite, 1));
                    botColor = KrathaPal.krathaBaseDarker;
                }};
                liquidOverpass = new LiquidTube("liquid-overpass"){{
                    requirements(Category.liquid, with(KrathaItems.kitegite, 8, KrathaItems.spurstone,4));
                }};
                liquidCell = new LiquidRouter("liquid-cell"){{
                    requirements(Category.liquid, with(KrathaItems.kitegite, 5));
                }};
            }
        }
    }
                      }
