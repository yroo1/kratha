package kratha.content.blocks;

import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.gen.*;
import kratha.content.KrathaItems;
import kratha.content.KrathaLiquids;
import kratha.content.KrathaFx;
import kratha.world.blocks.power.*;

import static mindustry.type.ItemStack.with;

public class KrathaPower{
    public static Block
    relay, windTurbine, turbine,
    candle;

    public static void load(){
        relay = new BeamNode("relay"){{
            requirements(Category.power, with(KrathaItems.guartz, 5));
            health = 20;
            consumesPower = outputsPower = true;
            range = 8;
            fogRadius = 0;
            crushFragile = true;
            consumePowerBuffered(0f);

            researchCost = with(KrathaItems.guartz,5);
        }};
        windTurbine = new WindTurbine("wind-turbine"){{
            requirements(Category.power, with(KrathaItems.krathite, 25, KrathaItems.guartz, 40));
            size = 3;
            squareSprite = false;
            powerProduction = 20f/60f;
            customShadow = true;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator", 2, true));
            researchCost = with(KrathaItems.krathite,25,KrathaItems.guartz,40);
        }};
        turbine = new DualTurbine("turbine"){{
            requirements(Category.power, with(KrathaItems.guartz, 80, KrathaItems.spurstone, 20, KrathaItems.cobalt, 20));
            size = 3;
            squareSprite = false;
            powerProduction = 180f/60f;
            ventLiquid = KrathaLiquids.steam;
            ambientSound = Sounds.loopHum;
            ambientSoundVolume = 0.06f;
            generateEffect = KrathaFx.turbinegenerateSteam;
            liquidCapacity = 24;
            hasLiquids = true;
            consumeLiquid(KrathaLiquids.steam,12f/60);
            outputLiquid = new LiquidStack(KrathaLiquids.watra,4f/60);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(){{drawLiquid=KrathaLiquids.watra;}}, new DrawBlurSpin("-rotator", 5), new DrawDefault(), new DrawGlowRegion());
        }};
        

        //why is this here? Because electricity (i dont know)
        candle = new LightBlock("candle"){{
            requirements(Category.effect, BuildVisibility.lightingOnly, with(KrathaItems.krathite, 15, KrathaItems.guartz, 12));
            brightness = 0.7f;
            radius = 120f;
            consumePower(5/60f);
            researchCost = with(KrathaItems.krathite,100,KrathaItems.guartz,75);
        }};
    }
}
