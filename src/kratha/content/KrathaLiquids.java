package kratha.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.content.*;
import kratha.graphics.KrathaPal;

public class KrathaLiquids{
    public static Liquid krathagen, watra, muddyWatra, terraplasm, akacyte, steam, terac;
    public static final Seq<Liquid> krathaLiquids = new Seq<>();
    
    public static void load(){

        krathagen = new Liquid("krathagen", KrathaPal.krathagenLight){{
            heatCapacity = 0.8f;
            effect = StatusEffects.wet;
            boilPoint = 1f;
            gasColor = Color.grays(0.9f);
            alwaysUnlocked = true;
        }};

        watra = new Liquid("watra", KrathaPal.watraLight){{
            heatCapacity = 1f;
            effect = StatusEffects.wet;
            boilPoint = 0.9f;
            gasColor = Color.grays(0.8f);
            alwaysUnlocked = true;
            viscosity = 0.75f;
        }};

        muddyWatra = new Liquid("muddy-watra", KrathaPal.muddyWatraLight){{
            heatCapacity = 0.4f;
            effect = StatusEffects.wet;
            boilPoint = 0.8f;
            gasColor = Color.grays(0.8f);
            alwaysUnlocked = true;
            viscosity = 0.3f;
        }};

        terraplasm = new CellLiquid("terraplasm", KrathaPal.terraplasmLight){{
            heatCapacity = 0.3f;
            temperature = 0.7f;
            viscosity = 0.4f;
            flammability = 0f;
            capPuddles = false;
            spreadTarget = watra;
            moveThroughBlocks = true;
            incinerable = false;
            blockReactive = false;
            canStayOn.addAll(Liquids.water,muddyWatra,watra, Liquids.oil, Liquids.cryofluid);

            colorFrom = KrathaPal.terraplasmDark;
            colorTo = KrathaPal.terraplasmLight;
        }};

        akacyte = new Liquid("akacyte", KrathaPal.akacyteLight){{
            heatCapacity = 0.2f;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.8f);
            alwaysUnlocked = true;
            viscosity = 0.7f;
        }};

        steam = new Liquid("steam", KrathaPal.steamLight){{
            gas = true;
        }};

        terac = new Liquid("terac", KrathaPal.teracLight){{
            gas = true;
        }};

        krathaLiquids.addAll(
        krathagen,watra,muddyWatra,terraplasm,akacyte,steam,terac
        );
    }
}
