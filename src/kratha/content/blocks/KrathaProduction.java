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
    thermicDrill,plasmaDrill,laserBore,cliffBore,

    spurstoneSmelter;
    public static void load() {
        thermicDrill = new Drill("thermic-drill"){{
            requirements(Category.production, with(KrathaItems.krathite, 10, KrathaItems.guartz, 5));
            consumePower(10/60f);
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();
            
            drillTime = 400;
            drawRim = true;
            hasPower = true;
            tier = 3;
            size = 2;
            fogRadius = 0;
            squareSprite = false;
            researchCost = with(KrathaItems.krathite, 10, KrathaItems.guartz, 5);
        }};
        plasmaDrill = new Drill("plasma-drill"){{
            requirements(Category.production, with(KrathaItems.krathite, 80, KrathaItems.guartz, 75, KrathaItems.spurstone, 40, KrathaItems.cobalt, 40));
            consumePower(60/60f);
            consumeLiquid(Liquids.watra, 4f/60)
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();
            
            drillTime = 320;
            drawRim = true;
            hasPower = true;
            tier = 5;
            size = 3;
            fogRadius = 0;
            squareSprite = false;
        }};
        laserBore = new CliffDrill("laser-bore"){{
            requirements(Category.production, with(KrathaItems.krathite, 5, KrathaItems.guartz, 10));
            consumePower(10/60f);
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();

            drillTime = 350;
            tier = 3;
            size = 2;
            squareSprite = false;
            researchCost = with(KrathaItems.krathite, 5, KrathaItems.guartz, 10);
        }};
        cliffBore = new WallCrafter("cliff-bore"){{
            requirements(Category.production, with(KrathaItems.krathite, 25, KrathaItems.guartz, 20));
            consumePower(5 / 60f);

            drillTime = 160f;
            size = 2;
            attribute = Attribute.sand;
            output = KrathaItems.terrasand;
            fogRadius = 0;
            researchCost = with(KrathaItems.krathite, 30, KrathaItems.guartz, 25);
            ambientSound = Sounds.loopDrill;
            ambientSoundVolume = 0.04f;
        }};

        spurstoneSmelter = new GenericCrafter("spurstone-smelter"){{
            requirements(Category.crafting, with(KrathaItems.krathite, 75, KrathaItems.guartz, 60));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(KrathaItems.spurstone, 3);
            craftTime = 120f;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            squareSprite = false;
            drawer = new DrawMulti(new DrawRegion("-bottom"),new DrawDefault(), new DrawGlowRegion(), new DrawArcSmelt());
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(KrathaItems.krathite, 3, KrathaItems.terrasand, 5));
            consumePower(60/60f);
            researchCost = with(KrathaItems.krathite, 100, KrathaItems.guartz, 80);
        }};
    }
}
