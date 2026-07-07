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
    logger,scanner,controlPanel,ancientMessageVault,

    invisible;

    public static void load(){
        logger = new PanelLogger("logger"){{
            requirements(Category.logic, with(KrathaItems.krathite,50,KrathaItems.guartz,100,KrathaItems.spurstone,10));
            researchCost = with(KrathaItems.krathite, 10, KrathaItems.guartz,10,KrathaItems.spurstone,5);
            consumePower(20/60f);
            hasPower = true;
        }};
        scanner = new ScannerBlock("scanner"){{
            requirements(Category.logic, with(KrathaItems.spurstone,15));
            researchCost = with(KrathaItems.spurstone, 30);
        }};
        controlPanel = new PanelBlock("control-panel"){{
            requirements(Category.logic, BuildVisibility.editorOnly, with());
            size = 4;
        }};
        ancientMessageVault = new AncientMessageBlock("ancient-message-vault"){{
            requirements(Category.logic, BuildVisibility.editorOnly, with());
            size = 2;
        }};

        invisible = new Block("invisible"){{
            requirements(Category.logic, BuildVisibility.editorOnly, with());
            targetable = false;
            forceDark = true;
            update= true;
            solid = true;
        }};
    }
              }
