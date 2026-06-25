package kratha.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.OverlayFloor;

public class MarkerOverlay extends OverlayFloor{
    public boolean shouldDraw = true;
    public MarkerOverlay(String name){
        super(name);
    }
    @Override
    public void drawBase(Tile tile){
        if(shouldDraw)super(tile);
    }
}
