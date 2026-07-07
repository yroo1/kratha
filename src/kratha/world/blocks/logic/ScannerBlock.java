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
import kratha.world.blocks.liquid.*;
import kratha.world.blocks.units.*;
import kratha.world.blocks.production.*;
import kratha.world.blocks.payloads.*;
import kratha.world.blocks.logic.*;

import static mindustry.Vars.*;

public class ScannerBlock extends Block{
    public TextureRegion topRegion1,topRegion2;
    public ScannerBlock(String name){
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
    }

    @Override
    public void load(){
        super.load();
        topRegion1 = Core.atlas.find(name+"-top1");
        topRegion2 = Core.atlas.find(name+"-top2");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region,topRegion1};
    }

    public Building connectedTo(Tile tile, int rot){
        Building b=tile.nearbyBuild(rot);
        return b;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Building b = connectedTo(world.tile(x,y),rotation);
        if(b==null||b.block==null)return;
        Block bb = b.block;
        if(valid&&isAncient(bb)){
            float offset = bb.size%2!=0?0:tilesize/2f;
            Drawf.select(b.tile.x*tilesize+offset, b.tile.y*tilesize+offset, b.block.size*tilesize/2f+2f, Pal.accent);
        }
    }
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        Building b = connectedTo(tile,rotation);
        if(b!=null&&b.block!=null&&isAncient(b.block))return true;
        return false;
    }
    public boolean isAncient(Block bb){
        //maybe theres a better way to do this...?
        return (bb instanceof AncientCrafter||bb instanceof AncientAttributeCrafter||bb instanceof AncientLiquidTube||bb instanceof AncientLiquidRouter||bb instanceof AncientUnitFactory||bb instanceof AncientPayloadMassDriver||bb instanceof AncientMessageBlock);
    }

    public class ScannerBuild extends Building{

        @Override
        public void draw(){
            Draw.rect(region,x,y);
            Draw.rect(rotation<2?topRegion1:topRegion2,x,y,rotdeg());
        }
        @Override
        public void drawSelect(){
            Building b = connectedTo(tile,rotation);
            if(b==null||b.block==null)return;
            Block bb = b.block;
            if(b!=null&&isAncient(bb)){
                float offset = b.block.size%2!=0?0:tilesize/2f;
                Drawf.select(b.tile.x*tilesize+offset, b.tile.y*tilesize+offset, b.block.size*tilesize/2f+2f, Pal.accent);
            }
        }

        @Override
        public void tapped(){
            Building b = connectedTo(tile,rotation);
            if(b==null||b.block==null||!isAncient(b.block))return;
            if(b instanceof AncientMessageBlock.AncientMessageBuild msg){
                ui.showInfo(msg.message.toString());
            }else{
                ui.content.show(b.block);
            }
        }
    }
}
