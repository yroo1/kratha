package kratha.type.unit;

import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import kratha.graphics.*;

public class KrathaUnitType extends UnitType{

    public KrathaUnitType(String name){
        super(name);
        outlineColor = KrathaPal.krathaOutline;
        mechLegColor = KrathaPal.krathaBaseDark;
        researchCostMultiplier = 5f;
    }
}
