package kratha.world.blocks.liquid;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.liquid.*;

import static mindustry.Vars.state;

public class AncientLiquidRouter extends LiquidRouter{
    public AncientLiquidRouter(String name){
        super(name);
        allowDerelictRepair = false;
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class AncientLiquidRouterBuild extends LiquidRouterBuild{
        
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
