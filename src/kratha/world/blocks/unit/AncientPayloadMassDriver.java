package kratha.world.blocks.payloads;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.payloads.*;

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

    public class AncientPayloadDriverBuild extends PayloadDriverBuild{

        @Override
        public boolean onConfigureBuildTapped(Building other){

            if(state.rules.editor) return true;
                
            if(this == other){
                if(link == -1) deselect();
                configure(-1);
                return false;
            }

            if(link == other.pos()){
                configure(-1);
                return false;
            }else if(other.block instanceof PayloadMassDriver && other.dst(tile) <= range && other.team == team){
                configure(other.pos());
                return false;
            }

            return true;
        }
        
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
