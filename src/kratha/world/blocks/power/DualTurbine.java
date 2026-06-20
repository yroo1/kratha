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

        if(sumAttribute(attribute, x, y)>(9f-0.001f))drawPlaceText("Vent Available", x, y, valid);
    }
  
    public class DualTurbineBuild extends ConsumeGeneratorBuild{

        @Override
        public void updateTile(){
            boolean valid = efficiency > 0;

            warmup = Mathf.lerpDelta(warmup, valid ? 1f : 0f, warmupSpeed);

            productionEfficiency = efficiency * efficiencyMultiplier;
            totalTime += warmup * Time.delta;

            //randomly produce the effect
            if(valid && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
            }

            //make sure the multiplier doesn't change when there is nothing to consume while it's still running
            if(filterItem != null && valid && itemDurationMultipliers.size > 0 && filterItem.getConsumed(this) != null){
                itemDurationMultiplier = itemDurationMultipliers.get(filterItem.getConsumed(this), 1);
            }

            //take in items periodically
            if(hasItems && valid && generateTime <= 0f){
                consume();
                consumeEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
                generateTime = 1f;
            }

            if(outputLiquid != null && !(sumAttribute(attribute, tile.x, tile.y)>(9f-0.001f))){
                float added = Math.min(productionEfficiency * delta() * outputLiquid.amount, liquidCapacity - liquids.get(outputLiquid.liquid));
                liquids.add(outputLiquid.liquid, added);
                dumpLiquid(outputLiquid.liquid);

                if(explodeOnFull && liquids.get(outputLiquid.liquid) >= liquidCapacity - 0.01f){
                    kill();
                    Events.fire(new GeneratorPressureExplodeEvent(this));
                }
            }

            //generation time always goes down, but only at the end so consumeTriggerValid doesn't assume fake items
            generateTime -= delta() / (itemDuration * itemDurationMultiplier);
            
            if(sumAttribute(attribute, tile.x, tile.y)>(9f-0.001f))liquids.add(ventLiquid, liquidCapacity - liquids.get(ventLiquid));
        }
    }
}
