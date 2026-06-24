package kratha.world.blocks.terraplasm;

import mindustry.world.blocks.logic.MessageBlock;

public class DPInfoBlock extends MessageBlock {
    public DPInfoBlock(String name){
        super(name);
    }

    public class DPInfoBuild extends MessageBuild {
        @Override
        public void updateTile(){
            super.updateTile();
            message.replace(0, message.length(), "hi lol");
        }
    }
 }     
