package kratha.world.blocks.liquid;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.state;

public class AncientLiquidTube extends LiquidTube{
    public AncientLiquidTube(String name){
        super(name);
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class AncientLiquidTubeBuild extends LiquidTubeBuild{
        
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
