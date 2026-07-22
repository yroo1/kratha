package kratha.world.blocks.logic;

import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.logic.*;
import arc.graphics.g2d.*;
import arc.graphics.*;
import mindustry.graphics.*;
import kratha.graphics.KrathaPal;

import static mindustry.Vars.state;

public class AncientMessageBlock extends MessageBlock{
    public AncientMessageBlock(String name){
        super(name);
        allowDerelictRepair = false;
    }

    @Override
    public void setBars(){
        //no
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    @Override
    public boolean accessible(){
        return state.rules.editor;
    }
    
    @Override
    public int minimapColor(Tile tile){
        return KrathaPal.deearthBase.rgba();
    }

    public class AncientMessageBuild extends MessageBuild{

        @Override
        public void drawSelect(){
            if(!accessible())return;
            super.drawSelect();
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
