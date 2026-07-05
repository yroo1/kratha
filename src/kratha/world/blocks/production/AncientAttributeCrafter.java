package kratha.world.blocks.production;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.production.*;

import static mindustry.Vars.state;

public class AncientAttributeCrafter extends AttributeCrafter{
    public AncientAttributeCrafter(String name){
        super(name);
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class AncientAttributeCrafterBuild extends AttributeCrafterBuild{
        
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
