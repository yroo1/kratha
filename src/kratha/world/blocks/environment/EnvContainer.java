package kratha.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.Vars;
import kratha.content.blocks.KrathaEnv;

public class EnvContainer extends TallBlock{
    public TextureRegion topRegion,shadowRegion;

    public int sizeX = 13;
    public int sizeY = 7;
    
    public float fadeDist = 70f, fadeDistTo = 50f, fadeAmount=0.75f; //fade amount 1 means 100% 0 means no fade
  
    public EnvContainer(String name){
        super(name);
      
    }
    @Override
    public void load(){
      super.load();
      topRegion=Core.atlas.find(name);
      shadowRegion=Core.atlas.find(name+"-shadow");
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        
        float tAlpha=1f;
        if(Vars.player.unit()!=null&&!Vars.player.unit().dead()){
            tAlpha=Math.max(0,Math.min(fadeDist-fadeDistTo,Mathf.dst(tile.worldx(),tile.worldy(),Vars.player.unit().x,Vars.player.unit().y)-fadeDistTo))/(fadeDist-fadeDistTo)*fadeAmount+(1-fadeAmount);
        }
    
        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(shadowRegion, tile.worldx() + shadowOffset, tile.worldy() + shadowOffset-sizeY/2*8);

        Draw.z(layer);
        Draw.color(1f,1f,1f,tAlpha);
        Draw.rect(topRegion,tile.worldx(), tile.worldy()-sizeY/2*8);
    }
          }
