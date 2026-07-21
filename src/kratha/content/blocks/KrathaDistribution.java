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
import mindustry.world.meta.*;
import kratha.world.blocks.distribution.*;
import kratha.content.KrathaItems;

import static mindustry.type.ItemStack.with;

public class KrathaDistribution {
    public static Block
            itemTube, itemOverpass, filter, splitter, itemGate,
            ancientTunnelIn,ancientTunnelOut,ancientTunnelItemSorter;
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
                filter = new Sorter("filter"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 4, KrathaItems.guartz, 2));
                    researchCost = with(KrathaItems.krathite, 50,KrathaItems.guartz,40);
                }};
                splitter = new Router("splitter"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 5));
                    researchCost = with(KrathaItems.krathite, 50);
                }};
                itemGate = new OverflowGate("item-gate"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 4, KrathaItems.guartz, 2));
                    researchCost = with(KrathaItems.krathite, 50,KrathaItems.guartz,40);
                }};
                
                ancientTunnelIn = new AncientTunnel("ancient-tunnel-in"){{
                    requirements(Category.distribution, BuildVisibility.sandboxOnly, with());
                }};
                ancientTunnelOut = new AncientTunnel("ancient-tunnel-out"){{
                    requirements(Category.distribution, BuildVisibility.sandboxOnly, with());
                    isOutput = true;
                }};
                ancientTunnelItemSorter = new Sorter("ancient-tunnel-item-sorter"){{
                    requirements(Category.distribution, BuildVisibility.sandboxOnly, with());
                }};
            }
        }
    }
                                        }
