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
    controlPanel;

    public static void load(){
        controlPanel = new PanelBlock("control-panel"){{
            requirements(Category.logic, with());
            size = 4;
        }};
    }
              }
