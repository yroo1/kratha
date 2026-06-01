package kratha.type.unit;

import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.world.meta.*;
import kratha.graphics.*;

public class DeearthUnitType extends UnitType{

    public DeearthUnitType(String name){
        super(name);
        outlineColor = KrathaPal.deearthOutline;
        researchCostMultiplier = 5f;
    }
}
