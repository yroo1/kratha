package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.*;

public class ParallaxFloor extends Floor{
    public block parallaxBlock
    public ParallaxFloor(String name){
        super(name);
    }
    @Override
    public void drawMain(Tile tile){
        Draw.z(Layer.floor-0.03f);
        super.drawMain(tile);
        tile.setBlock(parallaxBlock);
    }
}
