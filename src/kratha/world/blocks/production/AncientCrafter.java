package kratha.world.blocks.production;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.production.*;

import static mindustry.Vars.state;

public class AncientCrafter extends GenericCrafter{
    public AncientCrafter(String name){
        super(name);
        allowDerelictRepair = false;
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class AncientCrafterBuild extends GenericCrafterBuild{
        
        @Override
        public void damage(float damage){
            return; //no damage
        }

        @Override
        public boolean canPickup(){
            return false; //no
        }

        @Override
        public boolean collide(Bullet other){
            return false; //no
        }
    }
              }
