package kratha.world.blocks.logic;

import arc.Core;
import arc.audio.*;
import arc.graphics.g2d.*;
import arc.util.io.*
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;

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

        @Override
        public void draw(){
            super.draw();

            if(enabled){
                Draw.rect(onRegion, x, y);
            }
        }

        @Override
        public Boolean config(){
            return enabled;
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            enabled = read.bool();
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.bool(enabled);
        }
    }
              }
