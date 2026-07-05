package kratha.world.blocks.logic;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.math.geom.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.game.*;
import kratha.graphics.KrathaPal;
import kratha.content.*;
import mindustry.type.*;

import static mindustry.Vars.*;

//kratha code is bullshit pretending to be shit
public class PanelLogger extends Block{
    public TextureRegion topRegion;
    //placeholders
    public Item chip1 = KrathaItems.krathite;
    public Item chip2 = KrathaItems.guartz;
    public Item chip3 = KrathaItems.spurstone;
    public Item chip4 = KrathaItems.cobalt;
    public final static Point2[][] d4x2 = {{
        new Point2(2, 0),
        new Point2(2, 1)},{
        new Point2(1, 2),
        new Point2(0, 2)},{
        new Point2(-1, 1),
        new Point2(-1, 0)},{
        new Point2(0, -1),
        new Point2(1, -1)
    }};
    public PanelLogger(String name){
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
        size = 2;
        hasItems = true;
    }

    @Override
    public void setBars(){
        super.setBars(); 
        addBar("progress", (PanelLoggerBuild entity) -> new Bar(() -> Core.bundle.format("kratha.hackprogress", Strings.fixed(entity.progress*100, 0)), () -> Pal.accent, () -> entity.progress));
    }

    @Override
    public void load(){
        super.load();
        topRegion = Core.atlas.find(name+"-top");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region,topRegion};
    }

    public Building connectedTo(Tile tile, int rot){
        Tile tile1=tile.nearby(d4x2[rot][0]);
        Tile tile2=tile.nearby(d4x2[rot][1]);
        //insane amount of returns
        if(tile1==null||tile2==null)return null;
        if(tile1.build==null||tile2.build==null)return null;
        Building b=tile1.build;
        if(!(b instanceof PanelBlock.PanelBuild p))return null;
        if(tile2.build!=b)return null;
        return b;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Building b = connectedTo(world.tile(x,y),rotation);
        if(valid&&b!=null&&b instanceof PanelBlock.PanelBuild){
            float offset = b.block.size%2!=0?0:tilesize/2f;
            Drawf.select(b.tile.x*tilesize+offset, b.tile.y*tilesize+offset, b.block.size*tilesize/2f+2f, Pal.accent);
        }
    }
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(connectedTo(tile,rotation)!=null)return true;
        return false;
    }
    

    public class PanelLoggerBuild extends Building{
        public float progress = 0;
        public float delay = 60;

        @Override
        public void draw(){
            Draw.rect(region,x,y);
            Draw.rect(topRegion,x,y,rotdeg());
        }
        @Override
        public void drawSelect(){
            Building b = connectedTo(tile,rotation);
            if(b!=null&&b instanceof PanelBlock.PanelBuild){
                float offset = b.block.size%2!=0?0:tilesize/2f;
                Drawf.select(b.tile.x*tilesize+offset, b.tile.y*tilesize+offset, b.block.size*tilesize/2f+2f, Pal.accent);
            }
        }
        @Override
        public void updateTile(){
            super.updateTile();
            Building b = connectedTo(tile,rotation);
            if(b==null||!(b instanceof PanelBlock.PanelBuild p))return;
            progress = ((float)p.progress)/p.hackTime;
            if(p.active)return;
            if(p.progress>=p.hackTime){
                p.progress=p.hackTime;
                progress=1;
                p.activate();
                return;
            }
            if(p.reqChip1>0){
                if((float)(p.items.get(chip1))/p.reqChip1<=p.progress/(float)p.hackTime){
                    if(items.get(chip1)>0){
                        items.remove(chip1,1);
                        p.handleItem(this,chip1);
                    }else{
                        return;
                    }
                }
            }
            if(p.reqChip2>0){
                if((float)(p.items.get(chip2))/p.reqChip2<=p.progress/(float)p.hackTime){
                    if(items.get(chip2)>0){
                        items.remove(chip2,1);
                        p.handleItem(this,chip2);
                    }else{
                        return;
                    }
                }
            }
            if(p.reqChip3>0){
                if((float)(p.items.get(chip3))/p.reqChip3<=p.progress/(float)p.hackTime){
                    if(items.get(chip1)>0){
                        items.remove(chip3,1);
                        p.handleItem(this,chip3);
                    }else{
                        return;
                    }
                }
            }
            if(p.reqChip4>0){
                if((float)(p.items.get(chip4))/p.reqChip4<=p.progress/(float)p.hackTime){
                    if(items.get(chip1)>0){
                        items.remove(chip4,1);
                        p.handleItem(this,chip4);
                    }else{
                        return;
                    }
                }
            }
            delay -= delta()*efficiency;
            
            if(delay<=0){
                delay=60;
                p.progress++;
            }
        }

        public boolean acceptChip(Building panel, Item item){
            if(!(panel instanceof PanelBlock.PanelBuild p))return false;
            if(item==chip1&&p.reqChip1>0)return true;
            if(item==chip2&&p.reqChip2>0)return true;
            if(item==chip3&&p.reqChip3>0)return true;
            if(item==chip4&&p.reqChip4>0)return true;
            return false;
        }

        @Override
        public void handleItem(Building source, Item item){
            items.add(item, 1);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return acceptChip(connectedTo(tile,rotation),item) && items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(delay);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            delay = read.f();
        }
    }
}
