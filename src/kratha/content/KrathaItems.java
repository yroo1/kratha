package kratha.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;
import kratha.graphics.KrathaPal;

public class KrathaItems{
    public static Item
    krathite,guartz,terrasand,spurstone,cobalt,kitegite,anemite,arkscrap;
    public static final Seq<Item> krathaItems = new Seq<>();

    public static void load(){
        krathite = new Item("krathite", KrathaPal.krathite){{
            hardness = 1;
            cost = 0.5f;
        }};
        guartz = new Item("guartz", KrathaPal.guartzLight){{
            hardness = 2;
            cost = 0.8f;
        }};
        terrasand = new Item("terrasand", KrathaPal.terrasand){{
            hardness = 1;
            cost = 0.2f;
        }};
        spurstone = new Item("spurstone", KrathaPal.spurstone){{
            hardness = 4;
            cost = 1f;
        }};
        cobalt = new Item("cobalt", KrathaPal.cobalt){{
            hardness = 3;
            cost = 1.4f;
        }};
        kitegite = new Item("kitegite", KrathaPal.kitegite){{
            hardness = 3;
            cost = 1.2f;
        }};
        anemite = new Item("anemite", KrathaPal.anemite){{
            hardness = 1;
            cost = 3f;
        }};
        arkscrap = new Item("arkscrap", KrathaPal.deearthBase){{
            hardness = 3;
            cost = 0.5f;
        }};
        
        krathaItems.addAll(
        krathite,guartz,terrasand,spurstone,cobalt,kitegite,anemite,arkscrap
        );
    }
}
