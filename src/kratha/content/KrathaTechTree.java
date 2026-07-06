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
                    node(KrathaItems.kitegite, Seq.with(new Objectives.Produce(KrathaItems.kitegite)), () ->{
                        
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
            node(KrathaLiquid.gyralPump, Seq.with(new Objectives.SectorComplete(comingSoon)), () -> {
                node(KrathaLiquid.liquidTube, () -> {
                   node(KrathaLiquid.liquidCell, () -> {
                   
                   });
                   node(KrathaLiquid.liquidOverpass, () -> {
                   
                   });
                });
            });
            node(KrathaProduction.crusherDrill, () -> {
                node(KrathaProduction.laserBore, () -> {
                        
                });
                node(KrathaProduction.cliffBore, () -> {
                        
                });
            });
            node(KrathaProduction.spurstoneSmelter, () -> {
                node(KrathaProduction.crystallizationBasin, Seq.with(new Objectives.SectorComplete(comingSoon)),() -> {
                
                });
                node(KrathaProduction.earthenExtractor, Seq.with(new Objectives.SectorComplete(comingSoon)),() -> {
                
                });
            });
            
            node(KrathaPower.windTurbine, () -> {
                node(KrathaPower.relay, () -> {
                    
                });
                node(KrathaPower.turbine, Seq.with(new Objectives.SectorComplete(comingSoon)),() -> {
                    
                });
                node(KrathaPower.candle, () -> {
                    
                });
            });

            node(KrathaDefense.radar, () -> {
                
            });

            node(KrathaTurrets.impede, Seq.with(new Objectives.SectorComplete(comingSoon)),() -> {
                node(KrathaTurrets.debase, Seq.with(new Objectives.SectorComplete(comingSoon)),() -> {
                
                });
                node(KrathaDefense.krathiteWall, () -> {
                    node(KrathaDefense.krathiteWallLarge, () -> {
                    
                    });
                    node(KrathaDefense.spurstoneWall, () -> {
                        node(KrathaDefense.spurstoneWallLarge, () -> {
                        
                        });
                    });
                    node(KrathaDefense.cobaltWall, () -> {
                        node(KrathaDefense.cobaltWallLarge, () -> {
                        
                        });
                    });
                });
            });

            node(KrathaLogic.logger, () -> {
                node(KrathaLogic.scanner, () -> {
                
                });
            });
            
            node(KrathaSectorPresets.theFountain, () -> {
                node(KrathaSectorPresets.comingSoon, Seq.with(new Objectives.SectorComplete(theFountain)),() -> {
                    
                });
            });

            node(KrathaUnits.nauticAssembler, Seq.with(new Objectives.SectorComplete(comingSoon)),() -> {
                node(KrathaUnitTypes.sail, () -> {
                
                });
            });

            node(KrathaUnitTypes.keris, () -> {
                
            });
        });
    }
}
