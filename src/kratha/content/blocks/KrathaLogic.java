package kratha.content.blocks;

import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.logic.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.content.*;
import mindustry.gen.*;
import kratha.content.KrathaItems;
import kratha.content.KrathaLiquids;
import kratha.content.KrathaFx;
import kratha.graphics.KrathaPal;
import kratha.world.blocks.power.*;
import kratha.world.blocks.logic.*;

import static mindustry.type.ItemStack.with;

public class KrathaLogic{
    public static Block
    logger,controlPanel;

    public static void load(){
        logger = new PanelLogger("logger"){{
            requirements(Category.logic, with(KrathaItems.krathite,50,KrathaItems.guartz,100,KrathaItems.spurstone,10));
        }};
        controlPanel = new PanelBlock("control-panel"){{
            requirements(Category.logic, with());
            size = 4;
        }};
    }
              }
