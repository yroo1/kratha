package kratha.content.blocks;

public class KrathaBlocks{
    public static void load(){
        KrathaEnv.load();
        KrathaProduction.load();
        KrathaDistribution.load();
        KrathaPower.load();
        KrathaUnits.load();
        KrathaStorage.load();
    }
}
