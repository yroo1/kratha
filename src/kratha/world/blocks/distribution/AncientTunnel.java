package kratha.world.blocks.distribution;

import arc.*;
import arc.graphics.*;
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
import mindustry.content.Blocks;
import kratha.content.blocks.KrathaDistribution;
import kratha.graphics.KrathaPal;

import static mindustry.Vars.*;

public class AncientTunnel extends Block{
    public TextureRegion[] rotRegion = new TextureRegion[4];
    public TextureRegion itemRegion;
    public boolean isOutput=false;
    public float speed=1;
    
    public AncientTunnel(String name){
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
        hasItems = true;
        configurable = true;
        itemCapacity = 4;
        squareSprite = false;
    }

    @Override
    public void load(){
        super.load();
        for(int i=0;i<4;i++){
            rotRegion[i]= new TextureRegion(Core.atlas.find(name+"-atlas"),i*32,0,32,32);
        }
        itemRegion = Core.atlas.find(name+"-item");
    }
    
    @Override
    public void setBars(){}

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region};
    }
    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(rotRegion[plan.rotation], plan.drawx(), plan.drawy());
    }
    @Override
    public boolean canBreak(Tile tile){
        return state.rules.editor;
    }
    
    @Override
    public int minimapColor(Tile tile){
        return KrathaPal.deearthBaseDark;
    }
    
    public class AncientTunnelBuild extends Building{
        public int link=-1;
        public float unloadTimer=0f;
        public Item tunnelItem;

        @Override
        public void updateTile(){
            if(isOutput && Float.isNaN(unloadTimer))unloadTimer=0;
            if(isOutput && (unloadTimer += edelta()) >= speed){
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
            if(isOutput){
                Tile linkTile=world.tile(link);
                if(linkTile.build!=null&&linkTile.build instanceof AncientTunnelBuild a&&world.tile(a.link).build!=null&&world.tile(a.link).build==this){
                    tunnelItem=a.tunnelItem;
                }else{
                    link=-1;
                }
            }
            if(!isOutput){
                Building near=front();
                if(near!=null&&near instanceof Sorter.SorterBuild s){
                    if(s.sortItem!=null){
                        tunnelItem=s.sortItem;
                    }
                    near.tile.setBlock(Blocks.air);
                }
            }
        }

        @Override
        public void drawConfigure(){
            if(state.rules.editor){
                Drawf.select(tile.x*tilesize, tile.y*tilesize, tilesize/2f+2f, Pal.accent);
            }
            if(link!=-1&&world.tile(link)!=null&&world.tile(link).block()!=null){
                Tile linkTile = world.tile(link);
                if(linkTile.build==null)return;
                Drawf.line(Pal.accent,x, y, linkTile.build.x,linkTile.build.y);
                if(state.rules.editor){
                    Drawf.select(linkTile.x*tilesize, linkTile.y*tilesize, tilesize/2f+2f, Pal.accent);
                }
            }
            if(tunnelItem!=null)drawItemSelection(tunnelItem);
        }
        
        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(isOutput||!state.rules.editor)return true;
            if(other.block instanceof AncientTunnel){
                link = other.pos();
                if(other instanceof AncientTunnelBuild a)a.link=this.pos();
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
            Draw.rect(rotRegion[rotation],x,y);
            
            if(tunnelItem!=null){
                Draw.color(tunnelItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
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
        public void damage(float damage){
            return; //no damage
        }

        @Override
        public boolean canPickup(){
            return false; //no
        }

        @Override
        public boolean collide(Bullet other){
            return false; //no
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
            unloadTimer = read.f();
            int id = read.s();
            tunnelItem = id == -1 ? null : content.items().get(id);
        }
    }
}
