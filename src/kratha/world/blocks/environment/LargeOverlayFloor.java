package kratha.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.OverlayFloor;

public class LargeOverlayFloor extends OverlayFloor{
    public LargeOverlayFloor(String name){
        super(name);
    }
    @Override
    public void drawBase(Tile tile){
        Draw.z(Layer.floor+0.01f);
        Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
    }
}
