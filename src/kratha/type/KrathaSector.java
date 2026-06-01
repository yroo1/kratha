package kratha.type;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.graphics.g3d.PlanetGrid;
import mindustry.type.*;

public class KrathaSector extends Sector{
    public KrathaSector(Planet planet, PlanetGrid.Ptile tile){
        super(planet, tile);
    }

    @Override
    public String displayThreat(){
        float step = 0.11F;
        String color = Tmp.c1.set(Color.acid).lerp(Color.scarlet, Mathf.round(this.threat, step)).toString();
        String[] threats = new String[]{"safe", "low", "moderate", "high", "extreme", "grand"};
        int index = Math.min((int)(this.threat / step), threats.length - 1);
        return "[#" + color + "]" + Core.bundle.get("threat." + threats[index]);
    }
}
