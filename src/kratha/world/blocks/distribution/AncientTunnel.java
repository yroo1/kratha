package kratha.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.math.geom.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.game.*;
import kratha.content.*;
import mindustry.type.*;
import mindustry.world.blocks.distribution.*;

import static mindustry.Vars.*;

public class AncientTunnel extends Block{
    public TextureRegion topRegion1, topRegion2;
    public boolean isOutput=false;
    public float speed=1;
    
    public AncientTunnel(String name){
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
        hasItems = true;
        configurable = true;
    }

    @Override
    public void load(){
        super.load();
        topRegion1 = Core.atlas.find(name+"-top1");
        topRegion2 = Core.atlas.find(name+"-top2");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region,topRegion1};
    }
    
    public class AncientTunnelBuild extends Building{
        public int link=-1;
        public float unloadTimer=0f;
        public Item tunnelItem;

        @Override
        public void updateTile(){
            if((unloadTimer += edelta()) >= speed){
                Building front = front();

                if(tunnelItem != null && front != null){
                    if(items.has(tunnelItem) && front.acceptItem(this, tunnelItem)){
                        front.handleItem(this, tunnelItem);
                        items.remove(tunnelItem, 1);
                        itemTaken(tunnelItem);
                    }
                }

                unloadTimer %= speed;
            }
        }

        @Override
        public void drawConfigure(){
            Drawf.select(x, y, tile.block().size * tilesize / 2f + 2f, Pal.accent);
            if(link!=-1&&world.tile(link)!=null&&world.tile(link).block()!=null){
                Tile linkTile = world.tile(link);
                float offset = linkTile.block().size%2!=0?0:tilesize/2f;
                Drawf.select(linkTile.x*tilesize+offset, linkTile.y*tilesize+offset, linkTile.block().size*tilesize/2f+2f, Pal.remove);
            }
        }
        
        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(isOutput)return true;
            if(other.block instanceof AncientTunnel){
                link = other.pos();
                return false;
            }
            if (world.tile(link)!=null&&(other == this || link == other.pos())){
                link = -1;
                return false;
            }
            return true;
        }
        
        @Override
        public void draw(){
            Draw.rect(region,x,y);
            if (rotation<2){
                Draw.rect(topRegion1, x, y, rotation*90);
            } else {
                Draw.rect(topRegion2, x, y, rotation*90);
            }
        }
        
        @Override
        public void handleItem(Building source, Item item){
            if(!isOutput&&world.tile(link).build!=null){
                world.tile(link).build.items.add(item, 1);
            }else{
                items.add(item, 1);
            }
        }
        
        @Override
        public boolean acceptItem(Building source, Item item){
            if(!isOutput&&world.tile(link).build!=null){
                return source==this.nearby(rotation)&&world.tile(link).build.acceptItem(this,item);
            }
            if(tunnelItem!=null&&source instanceof AncientTunnelBuild&&items.get(item)<getMaximumAccepted(item)){
                return item==tunnelItem;
            }
            return false;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(link);
            write.f(unloadTimer);
            write.s(tunnelItem == null ? -1 : tunnelItem.id);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            link = read.i();
            int id = read.s();
            tunnelItem = id == -1 ? null : content.items().get(id);
            unloadTimer = read.f();
        }
    }
}
