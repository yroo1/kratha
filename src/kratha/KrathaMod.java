package kratha;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import kratha.content.blocks.KrathaBlocks;
import kratha.content.*;
import kratha.ui.TeamsUI;

public class KrathaMod extends Mod{

    public KrathaMod(){
        Log.info("Loaded KrathaMod constructor.");

        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("Notice");
                dialog.cont.add("This is a very early version of KRATHA. Play at your own risk.").row();
                dialog.cont.image(Core.atlas.find("kratha-signature")).pad(20f).row();
                dialog.cont.button("alr bro", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
        
    }
    @Override
    public void init(){
        super.init();
        TeamsUI.init();
    }
    @Override
    public void loadContent(){
        KrathaTeams.load()
        KrathaItems.load();
        KrathaLiquids.load();
        KrathaUnitTypes.load();
        KrathaBlocks.load();
        KrathaPlanets.load();
        KrathaTechTree.load();
    }

}
