package kratha.world.blocks.units;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.units.*;

import static mindustry.Vars.state;

public class AncientPayloadMassDriver extends PayloadMassDriver{
    public AncientPayloadMassDriver(String name){
        super(name);
        allowDerelictRepair = false;
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class AncientPayloadMassDriverBuild extends PayloadMassDriverBuild{
        
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
