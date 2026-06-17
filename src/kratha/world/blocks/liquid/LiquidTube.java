package kratha.world.blocks.liquid;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.math.geom.*;
import mindustry.world.blocks.liquid.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.Tile;
import mindustry.entities.units.BuildPlan;

import static mindustry.Vars.*;

public class LiquidTube extends Conduit {
    static final float rotatePad = 6, hpad = rotatePad / 2f / 4f;
    static final float[][] rotateOffsets = {{hpad, hpad}, {-hpad, hpad}, {-hpad, -hpad}, {hpad, -hpad}};
    //why shadedTopRegions and not topRegions?~
    public TextureRegion[][] shadedTopRegions = new TextureRegion[4][4];
    public LiquidTube(String name){
        super(name);
    }
    @Override
    public void load(){
        super.load();
        
        int y = 0;
        for(int cy = 0; cy < 4; cy++, y+=32){
            int x = 0;
            for(int cx = 0; cx < 4; cx++, x+=32){
                shadedTopRegions[cy][cx]=new TextureRegion(Core.atlas.find(name+"-top-atlas"), x, y, 32, 32);
            }
        }
    }
    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return return otherblock.hasLiquids && (otherblock.outputsLiquid || (lookingAt(tile, rotation, otherx, othery, otherblock))) && lookingAtEither(tile, rotation, otherx, othery, otherrot, otherblock);
    }
    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{Core.atlas.find(name+"-preview")};
    }
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        int[] bits = getTiling(plan, list);

        if(bits == null) return;

        TextureRegion region = Core.atlas.find(name+"-preview");
        Draw.rect(region, plan.drawx(), plan.drawy(), region.width * bits[1] * region.scl(), region.height * bits[2] * region.scl(), plan.rotation * 90);
    }
    public class LiquidTubeBuild extends ConduitBuild {
        
        public void draw(boolean under){
            int r = this.rotation;

            if(under) Draw.color(botColor);

            //draw extra conduits facing this one for tiling purposes
            Draw.z(Layer.blockUnder);
            for(int i = 0; i < 4; i++){
                if((blending & (1 << i)) != 0){
                    int dir = r - i;
                    drawAt(x + Geometry.d4x(dir) * tilesize*0.75f, y + Geometry.d4y(dir) * tilesize*0.75f, 0, i == 0 ? r : dir, i != 0 ? SliceMode.bottom : SliceMode.top, under);
                }
            }

            Draw.z(Layer.block);

            Draw.scl(xscl, yscl);
            drawAt(x, y, blendbits, r, SliceMode.none, under);
            Draw.reset();

            if(!under) return;

            if(capped && capRegion.found()) Draw.rect(capRegion, x, y, rotdeg());
            if(backCapped && capRegion.found()) Draw.rect(capRegion, x, y, rotdeg() + 180);
        }
        protected void drawAt(float x, float y, int bits, int rotation, SliceMode slice, boolean under){
            float angle = rotation * 90f;
            if(under){
                Draw.rect(sliced(botRegions[bits], slice), x, y, angle);
            }else{
                int offset = yscl == -1 ? 3 : 0;

                int frame = liquids.current().getAnimationFrame();
                int gas = liquids.current().gas ? 1 : 0;
                float ox = 0f, oy = 0f;
                int wrapRot = (rotation + offset) % 4;
                TextureRegion liquidr = bits == 1 && padCorners ? rotateRegions[wrapRot][gas][frame] : renderer.fluidFrames[gas][frame];

                if(bits == 1 && padCorners){
                    ox = rotateOffsets[wrapRot][0];
                    oy = rotateOffsets[wrapRot][1];
                }

                //the drawing state machine sure was a great design choice with no downsides or hidden behavior!!!
                float xscl = Draw.xscl, yscl = Draw.yscl;
                Draw.scl(1f, 1f);
                Drawf.liquid(sliced(liquidr, slice), x + ox, y + oy, smoothLiquid, liquids.current().color.write(Tmp.c1).a(1f));
                Draw.scl(xscl, yscl);

                int drawrot = (blendbits==1?(yscl!=-1?rotation:rotation-1)
                        :blendbits==2?(xscl!=-1?rotation:rotation-2)
                        :blendbits==4?rotation-2:rotation);
                if (blendbits==4) drawrot-=1;
                drawrot%=4;
                if (drawrot<0) drawrot+=4;
                int drawbits = blendbits==4?2:blendbits;
                //draw extra conveyors facing this one for non-square tiling purposes
                Draw.z(Layer.blockUnder);
                for(int i = 0; i < 4; i++){
                    if((blending & (1 << i)) != 0){
                        int dir = rotation-i;
                        Draw.rect(sliced(shadedTopRegions[drawbits][drawrot], i != 0 ? SliceMode.bottom : SliceMode.top), x + Geometry.d4x(dir) * tilesize*0.75f, y + Geometry.d4y(dir) * tilesize*0.75f,0);
                    }
                }
                Draw.z(Layer.block);
                Draw.rect(sliced(shadedTopRegions[drawbits][drawrot],slice), x, y, 0);
            }
        }
    }
}
