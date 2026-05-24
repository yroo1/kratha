package kratha.content.kratha;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;
import kratha.graphics.KrathaPal;

public class KrathaItems{
    public static Item
    krathite;
    public static final Seq<Item> krathaItems = new Seq<>();

    public static void load(){
        krathite = new Item("krathite", KrathaPal.krathite){{
            hardness = 1;
            cost = 0.3f;
        }};
        
        krathaItems.addAll(
        krathite
        );
    }
}
