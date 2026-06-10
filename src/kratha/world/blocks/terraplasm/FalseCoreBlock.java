package kratha.world.blocks.terraplasm;

import mindustry.world.blocks.storage.CoreBlock;

public class FalseCoreBlock extends CoreBlock {
    public FalseCoreBlock(String name){
        super(name);
    }

    @Override
    public void init(){
        //i made this entire class so i could set lightRadius to zero
        //anuke, you're evil by hardcoding it in the class, i disgust you.
        lightRadius = 0f;
        super.init();
    }
    
    public class FalseCoreBuild extends CoreBuild {
    }
 }     
