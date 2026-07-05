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
import kratha.world.blocks.units.*;

import static mindustry.type.ItemStack.with;

public class KrathaUnits {
    public static Block
            nauticAssembler, ancientFactory;
    public static void load() {
        {
            {
                nauticAssembler = new UnitFactory("nautic-assembler"){{
                    requirements(Category.units, with(KrathaItems.krathite,75,KrathaItems.guartz,60,KrathaItems.spurstone,30,KrathaItems.cobalt,30));
                    plans.add(new UnitPlan(KrathaUnitTypes.sail, 15 * 60f, with(KrathaItems.krathite, 15,KrathaItems.cobalt, 10)));
                    consumePower(60/60f);
                    size = 3;
                    configurable = false;
                    researchCost = with(KrathaItems.krathite,200,KrathaItems.guartz,180,KrathaItems.spurstone,100,KrathaItems.cobalt,50);
                }};
                ancientFactory = new AncientUnitFactory("ancient-factory"){{
                    requirements(Category.units, with());
                    plans.add(new UnitPlan(KrathaUnitTypes.keris, 14 * 60f, with(KrathaItems.guartz, 15,KrathaItems.spurstone, 10)));
                    size = 4;
                    canPickup = false;
                    configurable = false;
                    hasLiquids = true;
                    consumeLiquid(KrathaLiquids.terac, 3f / 60f);
                }};
            }
        }
    }
}
