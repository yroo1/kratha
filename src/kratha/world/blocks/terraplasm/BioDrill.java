package kratha.world.blocks.terraplasm;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import arc.math.geom.*;
import mindustry.world.blocks.defense.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.Tile;
import mindustry.graphics.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;
import java.util.Random;
import kratha.graphics.KrathaPal;

import static mindustry.Vars.*;

public class BioDrill extends BioBlock {
    protected Item returnItem;
    protected int returnCount;
    
    public int pulseToDrill=12;
    
    public BioDrill(String name){
        super(name);
        update=true;
        isRoot=false;
    }

    protected void countOre(Tile tile){
        returnItem = null;
        returnCount = 0;
        
        for(Tile other : tile.getLinkedTilesAs(this, tempTiles)){
            if(canMine(other)){
                returnCount++;
                returnItem = other.drop();
            }
        }
        //might conflict between mining wall and floor, dont care though:3
        for(Point2 pos : d4x2){
            Tile other = tile.nearby(pos);
            if(other==null)continue;
            if(canMineWall(other)){
                returnCount++;
                returnItem = other.wallDrop();
            }
        }
    }
    
    public boolean canMine(Tile other){
        return other.drop()!=null;
    }

    public boolean canMineWall(Tile other){
        return other.wallDrop()!=null;
    }
    
    public class BioDrillBuild extends BioBuilding {
        public int drillProgress = 0;
        
        @Override
        public void updatePulse() {
            //SPECIFICALLY FOR 2x2, smh my head
            if (true) {
                drillProgress++;
                
                countOre(tile);
                if(pulseSource != null && drillProgress >= pulseToDrill-returnCount && returnItem != null) {
                    drillProgress = 0;
                    Building target = pulseSource.build;
                    if(target != null && target instanceof BioBuilding && target.acceptItem(this, returnItem)){
                        target.handleItem(this, returnItem);
                    }
                }
            }
        }
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(drillProgress);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            drillProgress=read.i();
        }
    }
 }     
