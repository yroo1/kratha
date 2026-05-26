package kratha.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;
import kratha.graphics.KrathaPal;

public class KrathaItems{
    public static Item
    krathite,guartz;
    public static final Seq<Item> krathaItems = new Seq<>();

    public static void load(){
        krathite = new Item("krathite", KrathaPal.krathite){{
            hardness = 1;
            cost = 0.3f;
        }};
        guartz = new Item("guartz", KrathaPal.guartz){{
            hardness = 2;
            cost = 1f;
        }};
        
        krathaItems.addAll(
        krathite,guartz
        );
    }
}
