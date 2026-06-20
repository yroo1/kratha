package kratha.content.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.gen.*;
import kratha.content.KrathaItems;
import kratha.graphics.KrathaPal;

import static mindustry.type.ItemStack.with;

public class KrathaDefense{
    public static Block
    krathiteWall,krathiteWallLarge,
    radar,cutsceneRadar;
  
    public static void load(){
         krathiteWall = new Wall("krathite-wall"){{
             requirements(Category.defense, with(KrathaItems.krathite, 6));
             health = 380;
         }};
         radar = new Radar("radar"){{
            requirements(Category.effect, BuildVisibility.fogOnly, with(KrathaItems.krathite, 30, KrathaItems.guartz, 50, KrathaItems.spurstone, 15));
            outlineColor = KrathaPal.krathaOutline;
            fogRadius = 35;
            glowColor = KrathaPal.krathiteLight;
            researchCost = with(KrathaItems.krathite, 100, KrathaItems.guartz, 150, KrathaItems.spurstone, 50);
            customShadow = true;
            consumePower(30/60f);
        }};
        cutsceneRadar = new Radar("cutscene-radar"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly, with());
            outlineColor = KrathaPal.krathaOutline;
            fogRadius = 40;
            discoveryTime = 30f;
            glowColor = KrathaPal.krathiteLight;
            customShadow = true;
            rebuildable = false;
        }};
    }
}
