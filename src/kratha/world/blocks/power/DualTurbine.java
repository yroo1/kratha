package kratha.world.blocks.power;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.power.ConsumeGenerator;

import static mindustry.Vars.*;

//very weird naming...
public class DualTurbine extends ConsumeGenerator{
    public Attribute attribute = Attribute.steam;
    public Liquid ventLiquid;

    public DualTurbine(String name){
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        if(sumAttribute(attribute, x, y)>(9f-0.001f))Drawf.selected(x*tilesize, y*tilesize, this, Pal.accent);
    }
  
    public class DualTurbineBuild extends ConsumeGeneratorBuild{

        @Override
        public void updateTile(){
            super.updateTile();
          
            if(sumAttribute(attribute, tile.x, tile.y)>(9f-0.001f))liquids.add(ventLiquid, liquidCapacity - liquids.get(ventLiquid));
        }
    }
}
