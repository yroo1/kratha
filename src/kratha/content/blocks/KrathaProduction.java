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
import kratha.content.KrathaLiquids;
import kratha.graphics.KrathaPal;
import kratha.world.blocks.production.*;

import static mindustry.type.ItemStack.with;

public class KrathaProduction {
    public static Block
    crusherDrill,laserBore,cliffBore,

    spurstoneSmelter,crystallizationBasin,earthenExtractor;
    public static void load() {
        crusherDrill = new OreClusterDrill("crusher-drill"){{
            requirements(Category.production, with(KrathaItems.krathite, 10, KrathaItems.guartz, 5));
            consumePower(2/60f);
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();
            
            drillTime = 20;
            drawRim = true;
            hasPower = true;
            tier = 3;
            size = 2;
            fogRadius = 0;
            squareSprite = false;
            researchCost = with(KrathaItems.krathite, 10, KrathaItems.guartz, 5);
        }};
        laserBore = new CliffDrill("laser-bore"){{
            requirements(Category.production, with(KrathaItems.krathite, 5, KrathaItems.guartz, 10));
            consumePower(2/60f);
            consumeLiquid(Liquids.hydrogen, 0.05f).boost();

            drillTime = 400;
            tier = 3;
            size = 2;
            squareSprite = false;
            researchCost = with(KrathaItems.krathite, 5, KrathaItems.guartz, 10);
        }};
        cliffBore = new WallCrafter("cliff-bore"){{
            requirements(Category.production, with(KrathaItems.krathite, 25, KrathaItems.guartz, 20));
            consumePower(1 / 60f);

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
            outputItem = new ItemStack(KrathaItems.spurstone, 4);
            craftTime = 120f;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            squareSprite = false;
            drawer = new DrawMulti(new DrawRegion("-bottom"),new DrawDefault(), new DrawGlowRegion(), new DrawArcSmelt());
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.07f;

            consumeItems(with(KrathaItems.krathite, 3, KrathaItems.terrasand, 5));
            consumePower(20/60f);
            researchCost = with(KrathaItems.krathite, 100, KrathaItems.guartz, 80);
        }};
        crystallizationBasin = new GenericCrafter("crystallization-basin"){{
            requirements(Category.crafting, with(KrathaItems.krathite, 100, KrathaItems.guartz, 40, KrathaItems.spurstone, 40));
            craftEffect = Fx.none;
            outputItem = new ItemStack(KrathaItems.kitegite, 6);
            craftTime = 300f;
            itemCapacity = 20;
            liquidCapacity = 90;
            size = 3;
            hasLiquids = true;
            drawer = new DrawMulti(new DrawRegion("-bottom"),new DrawLiquidTile(){{drawLiquid=KrathaLiquids.akacyte;padding=1;}},new DrawCultivator(){{
                plantColor=KrathaPal.akacyte;
                plantColorLight=KrathaPal.akacyteLight;
                bottomColor=KrathaPal.akacyteDark;
                spread=10.5f;
            }},new DrawDefault());
            consumeItem(KrathaItems.guartz, 6);
            consumeLiquid(KrathaLiquids.akacyte, 18f / 60f);
            ambientSound = Sounds.loopCultivator;
            ambientSoundVolume = 0.075f;
        }};
        earthenExtractor= new AttributeCrafter("earthen-extractor"){{
            requirements(Category.crafting, with(KrathaItems.spurstone, 50, KrathaItems.cobalt, 30));
            craftEffect = Fx.drillSteam;
            attribute = Attribute.steam;
            minEfficiency = 8.999f;
            baseEfficiency = 0;
            displayEfficiency = false;
            boostScale = 1/8.999f;
            customShadow = true;
            outputItem = new ItemStack(KrathaItems.guartz, 4);
            craftTime = 96f;
            size = 3;
            hasLiquids = false;
            squareSprite = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion());
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.1f;

            consumeItems(with(KrathaItems.krathite, 3));
        }};
    }
}
