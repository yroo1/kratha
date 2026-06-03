package kratha.content;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.world.meta.*;
import mindustry.type.Weather;
import mindustry.type.weather.*;
import kratha.graphics.KrathaPal;

public class KrathaWeathers {
    public static Weather krathagenFog;

    public static void load(){
        krathagenFog = new ParticleWeather("krathagen-fog"){{
            color = noiseColor = KrathaPal.krathagenLight;
            particleRegion = "circle-small";
            drawNoise = true;
            statusGround = false;
            useWindVector = true;
            sizeMax = 4f;
            sizeMin = 2f;
            minAlpha = 0.1f;
            maxAlpha = 0.8f;
            density = 2000f;
            baseSpeed = 4.5f;
            attrs.set(Attribute.light, -0.15f);
            opacityMultiplier = 0.5f;
            force = 0.1f;
            sound = Sounds.wind;
            soundVol = 0.7f;
        }};
    }
}
