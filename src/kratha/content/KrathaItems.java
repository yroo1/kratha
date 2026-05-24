package kratha.content.kratha;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;

public class KrathaItems{
    public static Item
    krathite;
    public static final Seq<Item> krathaItems = new Seq<>();

    public static void load(){
        krathite = new Item("krathite", Color.valueOf("808480")){{
            hardness = 1;
            cost = 0.3f;
        }};
        
        krathaItems.addAll(
        krathite
        );
    }
}
