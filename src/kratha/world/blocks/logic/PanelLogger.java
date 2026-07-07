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
        public boolean needChip = false;

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
            if(needChip){
                drawPlaceText(Core.bundle.get("kratha.needchip"),tile.x,tile.y,false);
            }
        }
        
        @Override
        public void updateTile(){
            super.updateTile();
            Building b = connectedTo(tile,rotation);
            if(b==null||!(b instanceof PanelBlock.PanelBuild p))return;
            if(b.block==null||!(b.block instanceof PanelBlock pb))return;
            progress = ((float)p.progress)/p.hackTime;
            if(p.active){
                needChip=false;
                return;
            }
            if(p.progress>=p.hackTime){
                p.progress=p.hackTime;
                progress=1;
                p.activate();
                return;
            }
            int reqChipTotal=0;
            int reqChipAmount=0;
            for(int i=0;i<4;i++){
                Item chip=pb.chip1;
                int reqChip=p.reqChip1;
                switch(i){
                    case 0:
                        chip=pb.chip1;
                        reqChip=p.reqChip1;
                        break;
                    case 1:
                        chip=pb.chip2;
                        reqChip=p.reqChip2;
                        break;
                    case 2:
                        chip=pb.chip3;
                        reqChip=p.reqChip3;
                        break;
                    case 3:
                        chip=pb.chip4;
                        reqChip=p.reqChip4;
                        break;
                }
                
                reqChipAmount+=(reqChip-p.items.get(chip));
                reqChipTotal+=reqChip;
            }
            int reqChipHave = reqChipTotal-reqChipAmount;
            boolean needChipT=false;
            if(reqChipTotal>0&&((float)reqChipHave)/reqChipTotal<p.progress/p.hackTime+0.1f){
                for(int i=0;i<4;i++){
                    Item chip=pb.chip1;
                    int reqChip=p.reqChip1;
                    switch(i){
                        case 0:
                            chip=pb.chip1;
                            reqChip=p.reqChip1;
                            break;
                        case 1:
                            chip=pb.chip2;
                            reqChip=p.reqChip2;
                            break;
                        case 2:
                            chip=pb.chip3;
                            reqChip=p.reqChip3;
                            break;
                        case 3:
                            chip=pb.chip4;
                            reqChip=p.reqChip4;
                            break;
                    }
                    if(reqChip-p.items.get(chip)>0){
                        if(items.get(chip)>0){
                            items.remove(chip,1);
                            p.handleItem(this,chip);
                        }else{
                            needChipT=true;
                        }
                    }
                }
            }
            if(needChipT){
                needChip=true;
                return;
            }
            needChip=false;
            delay -= delta()*efficiency;
            
            if(delay<=0){
                delay=60;
                p.progress++;
            }
        }

        public boolean acceptChip(Building panel, Item item){
            if(!(panel.block instanceof PanelBlock pb))return false;
            if(!(panel instanceof PanelBlock.PanelBuild p))return false;
            if(item==pb.chip1&&p.reqChip1>0)return true;
            if(item==pb.chip2&&p.reqChip2>0)return true;
            if(item==pb.chip3&&p.reqChip3>0)return true;
            if(item==pb.chip4&&p.reqChip4>0)return true;
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
