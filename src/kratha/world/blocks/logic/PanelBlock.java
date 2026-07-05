package kratha.world.blocks.logic;

import arc.Core;
import arc.graphics.g2d.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.content.Fx;
import mindustry.logic.*;
import mindustry.graphics.*;
import kratha.content.blocks.KrathaLogic;

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
        addBar("progress", (PanelBuild entity) -> new Bar(() -> Core.bundle.format("kratha.hackprogress", Strings.fixed(entity.pprogress*100, 0)), () -> Pal.accent, () -> entity.pprogress));
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
        public boolean active = false;
        public int progress = 0;
        public int hackTime = 10;
        protected float pprogress = 0;

        public int reqChip1 = 0, reqChip2 = 0, reqChip3 = 0, reqChip4 = 0;

        @Override
        public void updateTile(){
            super.updateTile();
            pprogress=((float)(progress))/hackTime;
        }
        
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
        public void drawSelect(){
            if(KrathaLogic.logger instanceof PanelLogger l){
                if(reqChip1>0&&items.get(l.chip1)<reqChip1){
                    drawPlaceText((Core.bundle.get("item."+l.chip1.name+".name")+" : "+(int)items.get(l.chip1)+"/"+reqChip1),tile.x,tile.y,true);
                }
                if(reqChip2>0&&items.get(l.chip2)<reqChip2){
                    drawPlaceText((Core.bundle.get("item."+l.chip2.name+".name")+" : "+(int)items.get(l.chip2)+"/"+reqChip2),tile.x,tile.y,true);
                }
                if(reqChip3>0&&items.get(l.chip3)<reqChip3){
                    drawPlaceText((Core.bundle.get("item."+l.chip3.name+".name")+" : "+(int)items.get(l.chip3)+"/"+reqChip3),tile.x,tile.y,true);
                }
                if(reqChip4>0&&items.get(l.chip4)<reqChip4){
                    drawPlaceText((Core.bundle.get("item."+l.chip4.name+".name")+" : "+(int)items.get(l.chip4)+"/"+reqChip4),tile.x,tile.y,true);
                }
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
