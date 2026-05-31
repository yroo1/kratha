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
import kratha.world.blocks.production.*;

import static mindustry.type.ItemStack.with;

public class KrathaProduction {
    public static Block
    thermicDrill,laserBore,cliffBore;
    public static void load() {
        thermicDrill = new Drill("thermic-drill"){{
            requirements(Category.production, with(KrathaItems.krathite, 10, KrathaItems.guartz, 5));
            consumePower(10/60f);
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();
            
            drillTime = 400;
            drawRim = true;
            hasPower = true;
            tier = 2;
            size = 2;
            fogRadius = 0;
            squareSprite = false;
            researchCost = with(KrathaItems.krathite, 10, KrathaItems.guartz, 5);
        }};
        laserBore = new CliffDrill("laser-bore"){{
            requirements(Category.production, with(KrathaItems.krathite, 5, KrathaItems.guartz, 10));
            consumePower(10/60f);
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();

            drillTime = 380;
            tier = 3;
            size = 2;
            squareSprite = false;
            researchCost = with(KrathaItems.krathite, 5, KrathaItems.guartz, 10);
        }};
        cliffBore = new WallCrafter("cliff-bore"){{
            requirements(Category.production, with(KrathaItems.krathite, 25, KrathaItems.guartz, 20));
            consumePower(5 / 60f);

            drillTime = 170f;
            size = 2;
            attribute = Attribute.terrasand;
            output = KrathaItems.terrasand;
            fogRadius = 0;
            researchCost = with(KrathaItems.krathite, 30, KrathaItems.guartz, 25);
            ambientSound = Sounds.loopDrill;
            ambientSoundVolume = 0.04f;
        }};
    }
}
