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
import kratha.graphics.KrathaPal;

import static mindustry.Vars.*;

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
    }

    @Override
    public void setBars(){
        super.setBars(); 
        addBar("progress", (PanelLoggerBuild entity) -> new Bar(() -> Core.bundle.format("kratha.hackprogress", (entity.progress)), () -> KrathaPal.arkteraOrange, () -> entity.progress));
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

    public class PanelLoggerBuild extends Building{
        public float progress = 0;
        public float hackTime = 120;

        @Override
        public void draw(){
            Draw.rect(region,x,y);
            Draw.rect(topRegion,x,y,rotdeg());
        }
        @Override
        public void updateTile(){
            super.updateTile();
            Tile tile1=world.tile(d4x2[rotation][0]);
            Tile tile2=world.tile(d4x2[rotation][1]);
            //insane amount of returns
            if(tile1==null||tile2==null)return;
            if(tile1.build==null||tile2.build==null)return;
            Building b=tile1.build;
            if(!(b instanceof PanelBlock.PanelBuild p))return;
            if(tile2.build!=b)return;
            if(p.enabled)return;
            if(progress<1){
                progress=1;
                p.enable();
                return;
            }
            progress += delta() / hackTime;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progress);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
        }
    }
}
