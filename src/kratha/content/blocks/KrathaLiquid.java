package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
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
            gyralPump,liquidTube,liquidOverpass,liquidCell,

            ancientPipeline, ancientPipelineRouter;
    public static void load() {
        {
            {
                gyralPump = new Pump("gyral-pump"){{
                    requirements(Category.liquid, with(KrathaItems.guartz, 25, KrathaItems.spurstone, 20));
                    
                    drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPumpLiquid(), new DrawRegion("-rotator",-2,true), new DrawDefault());
                    size = 2;
                    
                    consumePower(15/60f);
                    hasPower = true;
                    liquidCapacity = 20f;
                    pumpAmount = 12/60f/4+0.0001f;
                    squareSprite = false;
                }};
                liquidTube = new LiquidTube("liquid-tube"){{
                    requirements(Category.liquid, with(KrathaItems.kitegite, 1));
                    botColor = KrathaPal.kitegiteDarker;
                    researchCost = with(KrathaItems.kitegite, 10);
                }};
                liquidOverpass = new LiquidOverpass("liquid-overpass"){{
                    requirements(Category.liquid, with(KrathaItems.kitegite, 8, KrathaItems.spurstone,4));
                    researchCost = with(KrathaItems.kitegite, 80, KrathaItems.spurstone, 40);
                }};
                liquidCell = new LiquidRouter("liquid-cell"){{
                    requirements(Category.liquid, with(KrathaItems.kitegite, 5));
                    researchCost = with(KrathaItems.kitegite, 50);
                }};

                ancientPipeline = new AncientLiquidTube("ancient-pipeline"){{
                    requirements(Category.liquid, BuildVisibility.sandboxOnly, with(KrathaItems.arkscrap, 1));
                    botColor = KrathaPal.arkteraBrownDarker;
                }};
                ancientPipelineRouter = new AncientLiquidRouter("ancient-pipeline-router"){{
                    requirements(Category.liquid, BuildVisibility.sandboxOnly, with(KrathaItems.arkscrap, 1));
                }};
            }
        }
    }
                      }
