package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import mindustry.content.*;
import kratha.world.blocks.distribution.*;
import kratha.content.KrathaItems;

import static mindustry.type.ItemStack.with;

public class KrathaDistribution {
    public static Block
            itemTube, itemOverpass, splitter;
    public static void load() {
        {
            {
                itemTube = new ItemTube("item-tube"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 1));
                    speed = 0.065f;
                    displayedSpeed = 9f;
                    bridgeReplacement=itemOverpass;
                    researchCost = with(KrathaItems.krathite, 5);
                }};
                itemOverpass = new ItemOverpass("item-overpass"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 6, KrathaItems.guartz, 3));
                    range=4;
                    researchCost = with(KrathaItems.krathite, 50,KrathaItems.guartz,40);
                }};
                splitter = new Router("splitter"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 5));
                    researchCost = with(KrathaItems.krathite, 50);
                }};
            }
        }
    }
                                        }
