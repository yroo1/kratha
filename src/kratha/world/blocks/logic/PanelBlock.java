package kratha.world.blocks.logic;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.io.*;
import arc.util.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.content.Fx;
import mindustry.logic.*;
import mindustry.graphics.*;
import kratha.content.blocks.KrathaLogic;
import mindustry.type.*;
import kratha.content.KrathaItems;

import static mindustry.Vars.state;

public class PanelBlock extends Block{
    public TextureRegion onRegion;

    //placeholders
    public Item chip1 = KrathaItems.krathite;
    public Item chip2 = KrathaItems.guartz;
    public Item chip3 = KrathaItems.spurstone;
    public Item chip4 = KrathaItems.cobalt;

    public PanelBlock(String name){
        super(name);
        update = true;
        drawDisabled = false;
        autoResetEnabled = false;
        configureSound = Sounds.none;
        envEnabled = Env.any;
        hasItems = true;
        buildVisibility = BuildVisibility.editorOnly;

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
            if(true){
                String stringf = "";
                for(int i=0;i<4;i++){
                    //reqChip1 and chip1 are placeholder so java doesnt yell at me
                    int reqChip = reqChip1;
                    Item chip = chip1;
                    switch(i){
                        case 0:
                            reqChip=reqChip1;
                            chip=chip1;
                            break;
                        case 1:
                            reqChip=reqChip2;
                            chip=chip2;
                            break;
                        case 2:
                            reqChip=reqChip3;
                            chip=chip3;
                            break;
                        case 3:
                            reqChip=reqChip4;
                            chip=chip4;
                            break;
                    }
                    if(reqChip>0){
                        if(stringf!="")stringf+="\n";
                        stringf+="[white]"+(
                                Core.bundle.get("item."+chip.name+".name")+
                            " : ["+
                            (items.get(chip)<(float)reqChip*0.5f?("red"):(items.get(chip)<(float)reqChip?("accent"):("white")))+
                            "]"+(int)items.get(chip)+"[white]/"+reqChip);
                    }
                }
                
                drawPlaceText(stringf,tile.x,tile.y,true);
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
