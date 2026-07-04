package kratha.world.blocks.logic;

import arc.Core;
import arc.audio.*;
import arc.graphics.g2d.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.content.Fx;
import mindustry.logic.*;

import static mindustry.Vars.state;

public class PanelBlock extends Block{
    public Sound onSound = Sounds.click;
    
    public TextureRegion onRegion;

    public PanelBlock(String name){
        super(name);
        update = true;
        drawDisabled = false;
        autoResetEnabled = false;
        configureSound = Sounds.none;
        envEnabled = Env.any;

        config(Boolean.class, (PanelBuild entity, Boolean b) -> entity.enabled = b);
    }

    @Override
    public void setBars(){
        //no
    }

    @Override
    public void load(){
      super.load();
      onRegion = Core.atlas.find(name+"-on");
    }

    public boolean accessible(){
        return !privileged || state.rules.editor || state.rules.allowEditWorldProcessors;
    }

    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }

    public class PanelBuild extends Building{
        boolean active = false;
        float progress = 0;
        float hackTime = 300;
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
                case enabled -> active;
                default -> super.sense(sensor);
            };
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            active = read.bool();
            progress = read.f();
            hackTime = read.f();
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.bool(active);
            write.f(progress);
            write.f(hackTime);
        }
    }
              }
