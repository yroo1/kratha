package kratha.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Tmp;
import arc.util.noise.*;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import kratha.content.blocks.KrathaEnv;

public class KrathaPlanetGenerator extends PlanetGenerator {
    public float heightScl = 1.2f, octaves = 9, persistence = 0.8f, heightPow = 2.5f, heightMult = 1.1f;

    @Override
    public float getHeight(Vec3 position) {
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    float rawHeight(Vec3 position) {
        return Simplex.noise3d(seed+12, octaves, persistence, 1f / heightScl, 10f + position.x, 10f + position.y, 10f + position.z)-Math.abs(position.y)*0.2f;
    }

    @Override
    public void getColor(Vec3 position, Color out) {
        Block block = rawHeight(position) < 0.3f ? KrathaEnv.krathagenDeep : rawHeight(position) < 0.36f ? KrathaEnv.krathagenFloor : rawHeight(position) < 0.4f ? KrathaEnv.krathiteKrathagen : rawHeight(position) < 0.48f ? KrathaEnv.krathiteFloor : rawHeight(position) < 0.53f ? KrathaEnv.terrastoneFloor : KrathaEnv.terrastoneEroded;

        out.set(block.mapColor).a(1f - block.albedo);
    }
}
