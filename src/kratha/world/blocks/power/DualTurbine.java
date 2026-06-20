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

//very weird naming...
public class DualTurbine extends ConsumeGenerator{
    public Attribute attribute = Attribute.steam;
    public LiquidStack ventLiquid;

    public DualTurbine(String name){
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashSquare(Pal.accent, x, y, size * tilesize);
    }
  
    public class DualTurbineBuild extends ConsumeGeneratorBuild{

        @Override
        public void updateTile(){
            super.updateTile();
          
            if(sumAttribute(attribute, tile.x, tile.y)>0.99f)liquids.add(ventLiquid.liquid, liquidCapacity - liquids.get(outputLiquid.liquid));
        }
    }
}
