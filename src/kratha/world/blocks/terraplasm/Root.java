package kratha.world.blocks.terraplasm;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.*;
import arc.func.*;
import arc.util.io.*;
import arc.math.geom.*;
import arc.struct.*;
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
import mindustry.world.meta.*;
import mindustry.gen.*;
import java.util.Random;
import kratha.graphics.*;
import kratha.content.terraplasm.Terraplasm;

import mindustry.Vars;
import static mindustry.Vars.*;

//if you're looking for how roots spread and the pattern, go to BioBlock.java
public class Root extends BioBlock {
    public TextureRegion[][] atlasRegion = new TextureRegion[12][4];
    private Seq<Building> heartArray = new Seq<>(Building.class);
    
    //dont worry about those two bitmask thing, its only for texture
    //100% handwritten btw, beautiful isnt it?
    public int[] horBitmask = {
        3,0,3,0,3,4,3,0,3,0,3,0,3,4,3,0,2,1,2,1,5,5,5,7,2,1,2,1,2,9,2,1,3,0,3,0,3,4,3,0,3,0,3,0,3,4,3,0,2,1,2,1,5,5,5,7,2,1,2,1,2,9,2,1,3,4,3,4,3,4,3,8,3,4,3,4,3,4,3,8,5,4,5,4,5,10,5,11,5,4,5,4,7,11,7,8,3,4,3,4,3,4,3,8,3,4,3,4,3,4,3,8,2,6,2,6,9,11,9,10,2,6,2,6,2,8,2,6,3,0,3,0,3,4,3,0,3,0,3,0,3,4,3,0,2,1,2,1,5,5,5,7,2,1,2,1,2,9,2,1,3,0,3,0,3,4,3,0,3,0,3,0,3,4,3,0,2,1,2,1,5,5,5,7,2,1,2,1,2,9,2,1,3,0,3,0,3,6,3,0,3,0,3,0,3,6,3,0,5,8,5,8,5,11,5,9,5,8,5,8,7,10,7,7,3,0,3,0,3,6,3,0,3,0,3,0,3,6,3,0,2,1,2,1,9,9,9,7,2,1,2,1,2,6,2,1
    };
    public int[] verBitmask = {
        3,3,3,3,2,1,2,2,3,3,3,3,2,1,2,2,3,3,3,3,1,3,1,3,3,3,3,3,2,1,2,2,3,3,3,3,2,1,2,2,3,3,3,3,2,1,2,2,3,3,3,3,1,3,1,3,3,3,3,3,2,1,2,2,0,0,0,0,1,3,1,1,0,0,0,0,1,3,1,1,0,2,0,2,2,0,2,1,0,2,0,2,2,0,2,2,0,0,0,0,1,3,1,1,0,0,0,0,1,3,1,1,0,2,0,2,0,3,0,1,0,2,0,2,1,3,1,0,3,3,3,3,2,1,2,2,3,3,3,3,2,1,2,2,3,3,3,3,1,3,1,3,3,3,3,3,2,1,2,2,3,3,3,3,2,1,2,2,3,3,3,3,2,1,2,2,3,3,3,3,1,3,1,3,3,3,3,3,2,1,2,2,0,0,0,0,1,3,1,1,0,0,0,0,1,3,1,1,0,0,0,0,2,2,2,2,0,0,0,0,2,2,2,0,0,0,0,0,1,3,1,1,0,0,0,0,1,3,1,1,0,0,0,0,0,3,0,1,0,0,0,0,1,1,1,1
        
    };
    public Root(String name){
        super(name);
        update=true;
        isRoot=true;
        pulseScale=0.5f;
        priority = TargetPriority.under;
        solid = false;
        underBullets = true;
        hasItems = true;
        itemCapacity = 1;
        unloadable = false;
    }
    @Override
    public void load(){
        super.load();
        int y = 0;
        for(int cy = 0; cy < 4; cy++, y += 32){
            int x = 0;
            for(int cx = 0; cx < 12; cx++, x += 32){
                atlasRegion[cx][cy] = new TextureRegion(Core.atlas.find(name+"-atlas"), x, y, 32, 32);
            }
        }
    }
    public static float xyRand(float x,float y) {
        int xi=Float.floatToIntBits(x);
        int yi=Float.floatToIntBits(y);
        long seed=((long)xi*179424691)^((long)yi*19349663);
        Random rand=new Random(seed);
        return rand.nextFloat();
    }

    //pack or unpack 2 ints into 1 float (i am genius)
    public static float pack(int x, int y) {
        int packed = (x<<12)|y;
        return Float.intBitsToFloat(packed);
    }
    public static int[] unpack(float f) {
        int packed = Float.floatToIntBits(f);
        int a = (packed>>12)&0xFFF;
        int b = packed&0xFFF;
        return new int[]{a, b};
    }
    //set a specific bit in a float. can store up to 21 booleans in one float
    public static float setbit(float f, int bit, boolean value) {
        int bits = Float.floatToRawIntBits(f);
        if(value){
            bits = bits | (1 << bit);
        }else{
            bits = bits & ~(1 << bit);
        }
        return Float.intBitsToFloat(bits);
    }
    public static boolean getbit(float f, int bit) {
        int bits = Float.floatToRawIntBits(f);
        return ((bits >> bit) & 1)==1;
    }
    
    public class RootBuild extends BioBuilding {
        public int blending;
        public Item lastItem;
        public int itemTargetX = -1, itemTargetY = -1;
        public Building itemFrom=null;
        
        @Override
        public void updateTile(){
            super.updateTile();
            blending = 0;
            for(int i = 0; i < 8; i++){
                if(blends(world.tile(tile.x + Geometry.d8[i].x, tile.y + Geometry.d8[i].y))){
                    blending |= (1 << i);
                }
            }
        }
        
        public boolean grow(Block growBlock){
            boolean success = false;
            if(false){
                //absolutely not
            }else{
                Building heart=getNearestHeart();
                if(heart!=null&&heart.items.has(growBlock.requirements)){
                    tile.setBlock(growBlock,team);
                    heart.items.remove(growBlock.requirements);
                    success = true;
                }
            }
            return success;
        }
        public boolean sameNear(Block sBlock,float maxDist){
            boolean sameNear = false;
            float maxDistSquared=maxDist*maxDist;
            int ceilDist = (int)Math.ceil(maxDist);
            for(int i=-ceilDist;i<=ceilDist;i++){
                for(int j=-ceilDist;j<=ceilDist;j++){
                    Tile adj;
                    adj = tile.nearby(i,j);
                    float dist=i*i+j*j;
                    if (dist<maxDistSquared&&adj != null && adj.build!=null && (adj.build.block==sBlock)) {                        
                        sameNear = true;
                    }
                }
            }
            return sameNear;
        }
        public boolean passiveGrow(Block growBlock, float maxDist, float rate){
            //try to grow a block if the same block isn't nearby
            Random random = new Random();
            boolean sameNear = sameNear(growBlock, maxDist);
            if(!sameNear&&random.nextFloat()<rate){
                grow(growBlock);
            }
            return sameNear;
        }
        
        @Override
        public void updatePulse(){
            super.updatePulse();
            
            Random random = new Random();
            
            boolean clear2 = true;
            for(int i=0;i<=1;i++){
                for(int j=0;j<=1;j++){
                    Tile adj;
                    adj = tile.nearby(i,j);
                    if (adj != null && adj.block() != null && (adj.block().solid || (!(adj.build instanceof RootBuild) && adj.build instanceof BioBuilding))){   
                        clear2 = false;
                    }
                }
            }
            boolean clear3 = true;
            for(int i=-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    Tile adj;
                    adj = tile.nearby(i,j);
                    if (adj != null && adj.block() != null && (adj.block().solid || (!(adj.build instanceof RootBuild) && adj.build instanceof BioBuilding))){   
                        clear3 = false;
                    }
                }
            }

            //growing drill
            if(tile != null && tile.drop() != null && allowDrill && clear2){
                tile.setBlock(Terraplasm.harvester,team);
            }

            if(allowEye&&!getbit(extraFloat3,0)){
                boolean sameNear = passiveGrow(Terraplasm.eye,eyeSpacing,eyeRate);
                if(sameNear)extraFloat3=setbit(extraFloat3,0,true);
            }
            if(allowSkewer&&!getbit(extraFloat3,1)&&clear3){
                boolean sameNear = passiveGrow(Terraplasm.skewer,skewerSpacing,skewerRate);
                if(sameNear)extraFloat3=setbit(extraFloat3,1,true);
            }

            //item movement
            
            if(lastItem == null && items.any()){
                lastItem = items.first();
            }
            if(itemTargetX == -1 || itemTargetY == -1){
                if(unpack(extraFloat2)[0]>0&&unpack(extraFloat2)[1]>0){
                    itemTargetX = unpack(extraFloat2)[0];
                    itemTargetY = unpack(extraFloat2)[1];
                }
            }
            if(itemTargetX == -1 || itemTargetY == -1){
                if(getNearestHeart()!=null){
                    itemTargetX = getNearestHeart().tile.x;
                    itemTargetY = getNearestHeart().tile.y;
                }
            }
            extraFloat2 = pack(itemTargetX,itemTargetY);
            if(lastItem != null && itemTargetX != -1 && itemTargetY != -1 && extraFloat1<=0) {
                Building target = null;
                Building randomTarget = null;
                int randomDirI=(int)Math.floor(random.nextFloat()*4);
                float bestDist = Float.POSITIVE_INFINITY; //FEAR THE INFINITE POWER
                for(int i=0;i<4;i++){
                    Building adj;
                    Tile itemTargetTile = world.tile(itemTargetX,itemTargetY);
                    Block itemTargetBlock = null;
                    if(itemTargetTile!=null&&itemTargetTile.build!=null&&itemTargetTile.build.block!=null)itemTargetBlock = itemTargetTile.build.block;
                    adj = tile.nearby(Geometry.d4(i).x,Geometry.d4(i).y).build;
                    
                    if(i==randomDirI)randomTarget=adj;
                    if(itemTargetBlock!=null&&itemTargetBlock instanceof BioHeart){
                        if(adj != null && (adj.block instanceof Root || adj.block instanceof BioHeart)){
                            float dist = Mathf.dst(itemTargetX, itemTargetY, adj.tile.x, adj.tile.y);
                            if(dist<bestDist&&adj.acceptItem(this, lastItem)){
                                target = adj;
                                bestDist = dist;
                            }
                        }
                    }
                    if(itemTargetBlock!=null&&itemTargetBlock instanceof BioTurret){
                        if(adj != null && (adj.block instanceof Root || adj.block instanceof BioTurret)){
                            float dist = Mathf.dst(itemTargetX, itemTargetY, adj.tile.x, adj.tile.y);
                            if(dist<bestDist&&adj.acceptItem(this, lastItem)){
                                target = adj;
                                bestDist = dist;
                            }
                        }
                    }
                    if(itemTargetBlock!=null&&itemTargetBlock instanceof BioSpawner){
                        if(adj != null && (adj.block instanceof Root || adj.block instanceof BioSpawner)){
                            float dist = Mathf.dst(itemTargetX, itemTargetY, adj.tile.x, adj.tile.y);
                            if(dist<bestDist&&adj.acceptItem(this, lastItem)){
                                target = adj;
                                bestDist = dist;
                            }
                        }
                    }
                    if(itemTargetBlock==null||(itemTargetBlock!=null&&itemTargetBlock instanceof Root)){
                        //if the destinated block is destroyed or null -> item is lost -> reset to default destination (nearest heart)
                        //Or, if the destinated block isn't null but instead is a root, its not the actual target and the actual target is destroyed because root never request items.
                        if(getNearestHeart()!=null){
                            itemTargetX = getNearestHeart().tile.x;
                            itemTargetY = getNearestHeart().tile.y;
                        }
                    }
                }
                if(target != null && randomTarget != null && target == itemFrom){
                    //if the target turns out to be where this item come from, its just looping around and is stuck.
                    //pick random target and hope to get unstuck
                    target=randomTarget;
                }
                //for root
                if(target != null && target instanceof RootBuild targetr){
                    if(target.acceptItem(this, lastItem)){
                        target.handleItem(this, lastItem);
                        targetbuild.itemTargetX = itemTargetX;
                        targetbuild.itemTargetY = itemTargetY;
                        extraFloat2 = 0;
                        itemTargetX = -1;
                        itemTargetY = -1;
                        items.remove(lastItem, 1);
                        lastItem = null;
                    }else{
                        //swap item
                        Item ti=targetr.lastItem;
                        target.items.remove(lastItem, 1);
                        target.handleItem(this, lastItem);
                        int tx=targetr.itemTargetX;
                        int ty=targetr.itemTargetY;
                        targetr.itemTargetX = itemTargetX;
                        targetr.itemTargetY = itemTargetY;
                        extraFloat2 = 0;
                        itemTargetX = tx;
                        itemTargetY = ty;
                        items.remove(lastItem, 1);
                        handleItem(target,ti)
                        lastItem = ti;
                    }
                }
                //for biobuilding that is not root
                if(target != null && !target instanceof RootBuild && target instanceof BioBuilding && target.acceptItem(this, lastItem)){
                    target.handleItem(this, lastItem);
                    if(target instanceof RootBuild targetbuild){
                        targetbuild.itemTargetX = itemTargetX;
                        targetbuild.itemTargetY = itemTargetY;
                    }
                    extraFloat2 = 0;
                    itemTargetX = -1;
                    itemTargetY = -1;
                    items.remove(lastItem, 1);
                    lastItem = null;
                }
                //for bioturret (it doesnt extend biobuilding)
                if(target != null && target instanceof BioTurret.BioTurretBuild && target.acceptItem(this, lastItem)){
                    target.handleItem(this, lastItem);
                    items.remove(lastItem, 1);
                    lastItem = null;
                }
            }
        }
        boolean blends(Tile other){
            if(other.build instanceof BioBuilding otherbuild){
                return other != null && other.build != null && other.build.tileX() == other.x && other.build.tileY() == other.y && otherbuild.fullyGrown;
            }
            return false;
        }
        @Override
        public void draw(){
            Draw.z(Layer.blockUnder);
            if(fullyGrown){
                drawPulse(atlasRegion[horBitmask[blending]][verBitmask[blending]],drawPulseScale);
            } else {
                drawPulse(atlasRegion[3][3],drawPulseScale);
            }
             if (xyRand(x,y)<0.08f) {
                Draw.z(Layer.power-1.1f);
            }
            Draw.z(Layer.blockUnder+0.1f);
            if(lastItem!=null){
                Draw.rect(lastItem.fullIcon, x, y, itemSize, itemSize);
            }
        }

        //item mechanic

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return 0;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return team == source.team && lastItem == null && items.total() == 0;
        }

        @Override
        public void handleItem(Building source, Item item){
            items.add(item, 1);
            lastItem = item;
            extraFloat1 = 5f;
            itemFrom = source;
        }

        @Override
        public int removeStack(Item item, int amount){
            int result = super.removeStack(item, amount);
            if(result != 0 && item == lastItem){
                lastItem = null;
            }
            return result;
        }
    }
}
