package kratha.type.unit;

import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.world.meta.*;
import kratha.graphics.*;

public class KrathaUnitType extends UnitType{

    public KrathaUnitType(String name){
        super(name);
        outlineColor = KrathaPal.krathaOutline;
        researchCostMultiplier = 5f;
    }
}
