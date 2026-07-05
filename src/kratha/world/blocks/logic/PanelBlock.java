package kratha.world.blocks.logic;

import arc.Core;
import arc.graphics.g2d.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.content.Fx;
import mindustry.logic.*;

import static mindustry.Vars.state;

public class PanelBlock extends Block{
    public TextureRegion onRegion;

    public PanelBlock(String name){
        super(name);
        update = true;
        drawDisabled = false;
        autoResetEnabled = false;
        configureSound = Sounds.none;
        envEnabled = Env.any;
        hasItems = true;

        config(Boolean.class, (PanelBuild entity, Boolean b) -> entity.enabled = b);
    }

    @Override
    public void setBars(){
        //no
    }

    public boolean isAccessible(){
        return false;
    }
    
    @Override
    public void load(){
      super.load();
      onRegion = Core.atlas.find(name+"-on");
    }

    public boolean accessible(){
        return state.rules.editor;
    }

    @Override
    public boolean synthetic(){
        return false;
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class PanelBuild extends Building{
        boolean active = false;
        int progress = 0;
        int hackTime = 10;

        int reqChip1 = 0, reqChip2 = 0, reqChip3 = 0, reqChip4 = 0;

        @Override
        public void damage(float damage){
            return; //no damage
        }

        public void activate(){
            active = true;
        }

        @Override
        public boolean canPickup(){
            return false; //no
        }

        @Override
        public boolean collide(Bullet other){
            return false; //no
        }

        @Override
        public void draw(){
            super.draw();

            if(active){
                Draw.rect(onRegion, x, y);
            }
        }

        @Override
        public Boolean config(){
            return active;
        }

        @Override
        public double sense(LAccess sensor){
            return switch(sensor){
                case enabled -> active ? 1 : 0;
                default -> super.sense(sensor);
            };
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            active = read.bool();
            progress = read.i();
            hackTime = read.i();
            reqChip1 = read.i();
            reqChip2 = read.i();
            reqChip3 = read.i();
            reqChip4 = read.i();
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.bool(active);
            write.i(progress);
            write.i(hackTime);
            write.i(reqChip1);
            write.i(reqChip2);
            write.i(reqChip3);
            write.i(reqChip4);
        }
    }
              }
