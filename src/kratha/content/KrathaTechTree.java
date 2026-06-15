package kratha.content;

import arc.struct.*;
import kratha.content.KrathaPlanets;
import kratha.content.blocks.*;
import kratha.content.*;
import mindustry.game.Objectives;
import kratha.game.KrathaObjectives;
import mindustry.type.Item;

import static kratha.content.KrathaSectorPresets.*;
import static mindustry.Vars.content;
import static mindustry.content.TechTree.*;

public class KrathaTechTree {
    public static void load(){
        KrathaPlanets.kratha.techTree = nodeRoot("KRATHA", KrathaStorage.coreRelic, () -> {
            node(KrathaItems.krathite, Seq.with(new Objectives.Produce(KrathaItems.krathite)), () -> {
                node(KrathaItems.guartz, Seq.with(new Objectives.Produce(KrathaItems.guartz)), () ->{
                    node(KrathaItems.terrasand, Seq.with(new Objectives.Produce(KrathaItems.terrasand)), () ->{
                        node(KrathaItems.spurstone, Seq.with(new Objectives.Produce(KrathaItems.spurstone)), () ->{
                    
                        });
                    });
                    node(KrathaItems.cobalt, Seq.with(new Objectives.Produce(KrathaItems.cobalt)), () ->{
                        
                    });
                });
            });
            node(KrathaDistribution.itemTube, () -> {
                node(KrathaDistribution.splitter, () -> {
                   node(KrathaDistribution.filter, () -> {
                   
                   });
                   node(KrathaDistribution.itemGate, () -> {
                   
                   });
                });
                node(KrathaDistribution.itemOverpass);
            });
            node(KrathaProduction.thermicDrill, () -> {
                node(KrathaProduction.laserBore, () -> {
                        
                });
                node(KrathaProduction.cliffBore, () -> {
                        
                });
            });
            node(KrathaProduction.spurstoneSmelter, () -> {
            });
            
            node(KrathaPower.windTurbine, () -> {
                node(KrathaPower.relay, () -> {
                    
                });
                node(KrathaPower.candle, () -> {
                    
                });
            });

            node(KrathaDefense.radar, () -> {
                
            });

            node(KrathaTurrets.impede, Seq.with(new Objectives.OnSector(oasis)),() -> {
                node(KrathaTurrets.debase, Seq.with(new Objectives.OnSector(harbor)),() -> {
                
                });
            });
            
            node(KrathaSectorPresets.theFountain, () -> {
                node(KrathaSectorPresets.oasis, Seq.with(new Objectives.SectorComplete(theFountain)),() -> {
                    node(KrathaSectorPresets.harbor, Seq.with(new Objectives.SectorComplete(oasis)),() -> {
                
                    });
                });
            });

            node(KrathaUnits.nauticAssembler, Seq.with(new Objectives.OnSector(oasis)),() -> {
                node(KrathaUnitTypes.sail, () -> {
                
                });
            });

            node(KrathaUnitTypes.keris, () -> {
                
            });
        });
    }
}
