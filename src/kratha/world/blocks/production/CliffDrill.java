package kratha.world.blocks.production;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.math.geom.*;
import mindustry.world.blocks.production.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.Tile;
import mindustry.core.*;
import mindustry.type.*;
import mindustry.entities.units.*;
import mindustry.content.Blocks;
import mindustry.game.Team;

import static mindustry.Vars.*;

public class CliffDrill extends BeamDrill {
    public TextureRegion topRegion1, topRegion2, wallHeatRegion;
    public int stackLimit = 2;
    public CliffDrill(String name){
         super(name);
    }
    @Override
    public void load(){
        super.load();
        topRegion1=Core.atlas.find(name+"-top1");
        topRegion2=Core.atlas.find(name+"-top2");
        wallHeatRegion=Core.atlas.find(name+"-wall-heat");
    }
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect((plan.rotation>1?topRegion2:topRegion1), plan.drawx(), plan.drawy(), plan.rotation*90);
    }
    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, topRegion1};
    }
    @Override
    public void setBars(){
        super.setBars();

        addBar("drillspeed", (BeamDrillBuild e) ->
            new Bar(() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed*60, 2)), () -> Pal.ammo, () -> e.warmup));
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Item item = null, invalidItem = null;
        boolean multiple = false;
        int count = 0;
        
        for(int i = 0; i < size; i++){
            nearbySide(x, y, rotation, i, Tmp.p1);

            int j = 0;
            Item found = null;
            int thisCount = 0;
            for(; j < range; j++){
                int rx = Tmp.p1.x + Geometry.d4x(rotation)*j, ry = Tmp.p1.y + Geometry.d4y(rotation)*j;
                Tile other = world.tile(rx, ry);
                if(other != null && other.build != null && other.solid()){
                    break;
                }
                if(other != null && other.solid()){
                    Item drop = other.wallDrop();
                    if(drop != null){
                        if(thisCount >= stackLimit)break;
                        if(drop.hardness <= tier && (blockedItems == null || !blockedItems.contains(drop))){
                            found = drop;
                            count++;
                            thisCount++;
                            if(valid)Drawf.selected(other.x, other.y, Blocks.router, Pal.accent);
                        }else{
                            invalidItem = drop;
                        }
                    }
                }else{
                    if(thisCount>0)break;
                }
            }

            if(found != null){
                //check if multiple items will be drilled
                if(item != found && item != null){
                    multiple = true;
                }
                item = found;
            }

            int len = Math.min(j, range - 1);
            Drawf.dashLine(found == null || !valid ? Pal.remove : Pal.placing,
            Tmp.p1.x * tilesize,
            Tmp.p1.y *tilesize,
            (Tmp.p1.x + Geometry.d4x(rotation)*len) * tilesize,
            (Tmp.p1.y + Geometry.d4y(rotation)*len) * tilesize
            );
        }

        if(item != null){
            float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 60f / getDrillTime(item) * count, 2), x, y, valid);
            if(!multiple){
                float dx = x * tilesize + offset - width/2f - 4f, dy = y * tilesize + offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
                Draw.mixcol(Color.darkGray, 1f);
                Draw.rect(item.fullIcon, dx, dy - 1, s, s);
                Draw.reset();
                Draw.rect(item.fullIcon, dx, dy, s, s);
            }
        }else if(invalidItem != null){
            drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, false);
        }

    }
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for(int i = 0; i < size; i++){
            nearbySide(tile.x, tile.y, rotation, i, Tmp.p1);
            for(int j = 0; j < range; j++){
                Tile other = world.tile(Tmp.p1.x + Geometry.d4x(rotation)*j, Tmp.p1.y + Geometry.d4y(rotation)*j);
                if(other != null && other.build != null && other.solid()){
                    break;
                }
                if(other != null && other.solid()){
                    Item drop = other.wallDrop();
                    if(drop != null && drop.hardness <= tier && (blockedItems == null || !blockedItems.contains(drop))){
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public class CliffDrillBuild extends BeamDrillBuild {
        public Tile[] newFacing = new Tile[size*range];
        @Override
        public void drawSelect(){
            for(Tile tile : newFacing){
                if(tile!=null)Drawf.selected(tile.x, tile.y, Blocks.router, Pal.accent);
            }
            drawItemSelection(lastItem);
        }
        @Override
        public void updateTile(){
            super.updateTile();

            if(lasers[0] == null) updateLasers();

            warmup = Mathf.approachDelta(warmup, Mathf.num(efficiency > 0), 1f / 60f);

            updateFacing();

            float multiplier = Mathf.lerp(1f, optionalBoostIntensity, optionalEfficiency);
            float drillTime = getDrillTime(lastItem);
            boostWarmup = Mathf.lerpDelta(boostWarmup, optionalEfficiency, 0.1f);
            lastDrillSpeed = (facingAmount * multiplier * timeScale) / drillTime * efficiency;

            time += edelta() * multiplier;

            int addAmount = Math.min(facingAmount,itemCapacity-items.total());
            Log.info(addAmount);
            if(time >= drillTime){
                items.add(lastItem, addAmount);
                produced(lastItem, addAmount);
                time %= drillTime;
            }

            if(timer(timerDump, dumpTime / timeScale)){
                dump();
            }
        }
        @Override
        public void draw(){
            Draw.rect(block.region, x, y);
            Draw.rect((rotation>1?topRegion2:topRegion1), x, y, rotdeg());

            if(isPayload()) return;

            var dir = Geometry.d4(rotation);
            int ddx = Geometry.d4x(rotation + 1), ddy = Geometry.d4y(rotation + 1);

            for(int i = 0; i < size; i++){
                Tile face = facing[i];
                if(face != null){
                    Point2 p = lasers[i];
                    float lx = face.worldx() - (dir.x/2f)*tilesize, ly = face.worldy() - (dir.y/2f)*tilesize;

                    float width = (laserWidth + Mathf.absin(Time.time + i*5 + (id % 9)*9, glowScl, pulseIntensity)) * warmup;

                    Draw.z(Layer.power - 1);
                    Draw.mixcol(glowColor, Mathf.absin(Time.time + i*5 + id*9, glowScl, glowIntensity));
                    if(Math.abs(p.x - face.x) + Math.abs(p.y - face.y) == 0){
                        Draw.scl(width);

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Draw.rect(laserCenter, lx, ly);
                        }

                        if(boostWarmup > 0.01f){
                            Draw.alpha(boostWarmup);
                            Draw.rect(laserCenterBoost, lx, ly);
                        }

                        Draw.scl();
                    }else{
                        float lsx = (p.x - dir.x/2f) * tilesize, lsy = (p.y - dir.y/2f) * tilesize;

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Drawf.laser(laser, laserEnd, lsx, lsy, lx, ly, width);
                        }

                        if(boostWarmup > 0.001f){
                            Draw.alpha(boostWarmup);
                            Drawf.laser(laserBoost, laserEndBoost, lsx, lsy, lx, ly, width);
                        }
                    }
                    int depth=0;
                    int startDepth=999999;
                    for(int j=0;j<range;j++){
                        if(newFacing[i*range+j]!=null){
                            startDepth=Math.min(j+1,startDepth);
                            depth=Math.max(j+1,depth);
                        }
                    }
                    depth=depth-startDepth+1;
                    Draw.scl(depth/4f*warmup,1);
                    if(dir.x!=0){
                        Draw.rect(wallHeatRegion, face.worldx()+(depth*tilesize/2f*dir.x*warmup)-dir.x/2f*tilesize, face.worldy(), rotdeg());
                    }else{
                        Draw.rect(wallHeatRegion, face.worldx(), face.worldy()+(depth*tilesize/2f*dir.y*warmup)-dir.y/2f*tilesize, rotdeg());
                    }
                    Draw.scl(1,1);
                    Draw.color();
                    Draw.mixcol();

                    Draw.z(Layer.effect);
                    Lines.stroke(warmup);
                    rand.setState(i, id);
                    Color col = face.wallDrop().color;
                    Color spark = Tmp.c3.set(sparkColor).lerp(boostHeatColor, boostWarmup);
                    for(int j = 0; j < sparks; j++){
                        float fin = (Time.time / sparkLife + rand.random(sparkRecurrence + 1f)) % sparkRecurrence;
                        float or = rand.range(2f);
                        Tmp.v1.set(sparkRange * fin, 0).rotate(rotdeg() + rand.range(sparkSpread));

                        Draw.color(spark, col, fin);
                        float px = Tmp.v1.x, py = Tmp.v1.y;
                        if(fin <= 1f) Lines.lineAngle(lx + px + or * ddx, ly + py + or * ddy, Angles.angle(px, py), Mathf.slope(fin) * sparkSize);
                    }
                    Draw.reset();
                }
            }

            if(glowRegion.found()){
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.color(Tmp.c1.set(heatColor).lerp(boostHeatColor, boostWarmup), warmup * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                Draw.rect(glowRegion, x, y, rotdeg());
                Draw.blend();
                Draw.color();
            }

            Draw.blend();
            Draw.reset();
        }

        @Override
        protected void updateFacing(){
            lastItem = null;
            boolean multiple = false;
            int dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation);
            facingAmount = 0;

            //update facing tiles
            for(int p = 0; p < size; p++){
                Point2 l = lasers[p];
                int thisCount = 0;
                for(int i = 0; i < range; i++){
                    Tile dest = null;
                    int rx = l.x + dx*i, ry = l.y + dy*i;
                    Tile other = world.tile(rx, ry);
                    if(other != null && other.build != null && other.solid()){
                        break;
                    }
                    if(other != null){
                        if(other.solid()){
                            if(thisCount>=stackLimit)break;
                            Item drop = other.wallDrop();
                            if(drop != null && drop.hardness <= tier && (blockedItems == null || !blockedItems.contains(drop))){
                                facingAmount ++;
                                if(lastItem != drop && lastItem != null){
                                    multiple = true;
                                }
                                lastItem = drop;
                                dest = other;
                                thisCount++;
                            }
                        }else{
                            if(thisCount>0)break;
                        }
                    }
                    newFacing[p*range+i] = dest;
                }
            }
            //old facing for drawing
            for(int p = 0; p < size; p++){
                Point2 l = lasers[p];
                Tile dest = null;
                for(int i = 0; i < range; i++){
                    int rx = l.x + dx*i, ry = l.y + dy*i;
                    Tile other = world.tile(rx, ry);
                    if(other != null && other.build != null && other.solid()){
                        break;
                    }
                    if(other != null){
                        if(other.solid()){
                            Item drop = other.wallDrop();
                            if(drop != null && drop.hardness <= tier && (blockedItems == null || !blockedItems.contains(drop))){
                                dest = other;
                                break;
                            }
                        }
                    }
                }
                facing[p] = dest;
            }
            

            //when multiple items are present, count that as no item
            if(multiple){
                lastItem = null;
            }
        }

    }
}
