package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import kratha.content.KrathaItems;
import kratha.content.*;
import mindustry.content.*;

import static mindustry.type.ItemStack.with;

public class KrathaUnits {
    public static Block
            rockboredFactory;
    public static void load() {
        {
            {
                rockboredFactory = new UnitFactory("rockbored-factory"){{
                    requirements(Category.units, with(KrathaItems.guartz, 120, KrathaItems.krathite,100, KrathaItems.spurstone,75));
                    plans.add(new UnitPlan(KrathaUnitTypes.keris, 14 * 60f, with(KrathaItems.guartz, 15,KrathaItems.spurstone, 10)));
                    consumePower(90/60f);
                    size = 4;
                    canPickup = false;
                    buildVisibility = BuildVisibility.sandboxOnly;
                    breakable = false;
                    targetable = false;
                }};
            }
        }
    }
}
