package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import mindustry.content.*;
import mindustry.world.draw.*;
import mindustry.type.*;
import kratha.content.KrathaItems;
import kratha.graphics.KrathaPal;

import static mindustry.type.ItemStack.with;

public class KrathaProduction {
    public static Block
    thermicDrill;
    public static void load() {
        thermicDrill = new Drill("thermic-drill"){{
            requirements(Category.production, with(KrathaItems.krathite, 10, KrathaItems.guartz, 5));
            consumePower(10/60f);
            
            drillTime = 400;
            drawRim = true;
            hasPower = true;
            tier = 2;
            size = 2;
            fogRadius = 0;
            researchCost = with(KrathaItems.krathite, 10, KrathaItems.guartz, 5);
        }};
    }
}
