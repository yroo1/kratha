package kratha.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.content.*;
import kratha.graphics.KrathaPal;

public class KrathaLiquids{
    public static Liquid krathagen, biomass;
    public static void load(){

        krathagen = new Liquid("krathagen", KrathaPal.krathagen){{
            heatCapacity = 0.8f;
            effect = StatusEffects.wet;
            boilPoint = 0.9f;
            gasColor = Color.grays(0.9f);
            alwaysUnlocked = true;
        }};

        biomass = new CellLiquid("biomass", Color.valueOf("98915E")){{
            heatCapacity = 0.3f;
            temperature = 0.7f;
            viscosity = 0.4f;
            flammability = 0f;
            capPuddles = false;
            spreadTarget = Liquids.water;
            moveThroughBlocks = true;
            incinerable = false;
            blockReactive = false;
            canStayOn.addAll(Liquids.water, watergel, Liquids.oil, Liquids.cryofluid);

            colorFrom = Color.valueOf("524809");
            colorTo = Color.valueOf("D4C98A");
        }};
    }
}
