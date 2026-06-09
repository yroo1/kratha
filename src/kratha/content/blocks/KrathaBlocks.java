package kratha.content.blocks;
import kratha.content.terraplasm.Terraplasm;

public class KrathaBlocks{
    public static void load(){
        KrathaEnv.load();
        KrathaTurrets.load();
        KrathaDefense.load();
        KrathaProduction.load();
        KrathaDistribution.load();
        KrathaPower.load();
        KrathaUnits.load();
        KrathaStorage.load();
        Terraplasm.load();
    }
}
