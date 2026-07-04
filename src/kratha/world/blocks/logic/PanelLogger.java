package kratha.world.blocks.logic;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import kratha.graphics.KrathaPal

public class PanelLogger extends Block{
  public TextureRegion topRegion;
    public HeatConductor(String name){
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
    }

    @Override
    public void setBars(){
        super.setBars(); 
        addBar("progress", (HeatConductorBuild entity) -> new Bar(() -> Core.bundle.format("kratha.hackprogress", (entity.progress)), () -> KrathaPal.arkteraOrange, () -> entity.progress));
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

    public class PanelLoggerBuild extends Building implements HeatBlock, HeatConsumer{
        public float progress = 0;

        @Override
        public void draw(){
            Draw.rect(region,x,y);
            Draw.rect(topRegion,x,y,rotdeg());
        }
    }
}
yes
