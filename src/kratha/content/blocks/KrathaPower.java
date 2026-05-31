package kratha.content.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.gen.*;
import kratha.content.KrathaItems;
import kratha.world.blocks.power.*;

import static mindustry.type.ItemStack.with;

public class KrathaPower{
    public static Block
    relay, windTurbine;

    public static void load(){
        relay = new PowerNode("relay"){{
            requirements(Category.power, with(KrathaItems.guartz, 5));
            health = 20;
            size = 1;
            squareSprite = false;
            laserRange = 10f;
            maxNodes = 8;
            researchCost = with(KrathaItems.guartz,5);
        }};
        windTurbine = new WindTurbine("wind-turbine"){{
            requirements(Category.power, with(KrathaItems.krathite, 25, KrathaItems.guartz, 40));
            size = 3;
            squareSprite = false;
            powerProduction = 60f/60f;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator", 2, true));
            researchCost = with(KrathaItems.krathite,25,KrathaItems.guartz,40);
        }};
    }
}
