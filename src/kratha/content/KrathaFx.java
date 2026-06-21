package kratha.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.gen.Bullet;
import mindustry.graphics.*;
import kratha.graphics.KrathaPal;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class KrathaFx{
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Effect
    bulbPop = new Effect(10f, 80f, e -> {
        color(KrathaPal.cobalt, KrathaPal.cobaltLight, e.fin());
        stroke(e.fout() * 3f);
        Lines.circle(e.x, e.y, e.fin() * 32f);
    }),
    kerisShoot = new Effect(12f, e -> {
        color(Color.white, KrathaPal.guartzLight, e.fin());
        stroke(e.fout() * 1.2f + 0.5f);

        randLenVectors(e.id, 7, 25f * e.finpow(), e.rotation, 50f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 5f + 2f);
        });
    }),
    shootSkewer = new Effect(10, e -> {
        color(KrathaPal.guartzLight, e.color, e.fin());
        float w = 1.3f + 10 * e.fout();
        Drawf.tri(e.x, e.y, w, 35f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
    }),
    shootSmokeSkewer = new Effect(70f, e -> {
        rand.setSeed(e.id);
        for(int i = 0; i < 13; i++){
            v.trns(e.rotation + rand.range(30f), rand.random(e.finpow() * 40f));
            e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                color(e.color, Pal.lightishGray, b.fin());
                Fill.circle(e.x + v.x, e.y + v.y, b.fout() * 3.4f + 0.3f);
            });
        }
    }),
    skewerSmoke = new Effect(300f, 300f, b -> {
        float intensity = 1f;

        color(b.color, 0.7f);
        for(int i = 0; i < 4; i++){
            rand.setSeed(b.id*2 + i);
            float lenScl = rand.random(0.5f, 1f);
            int fi = i;
            b.scaled(b.lifetime * lenScl, e -> {
                randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 22f * intensity, (x, y, in, out) -> {
                    float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                    float rad = fout * ((2f + intensity) * 2.35f);

                    Fill.circle(e.x + x, e.y + y, rad);
                    Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                });
            });
        }
    }),
    turbinegenerateSteam = new Effect(100, e -> {
        color(KrathaPal.steamLight);
        alpha(e.fslope() * 0.8f);

        rand.setSeed(e.id);
        for(int i = 0; i < 3; i++){
            v.trns(rand.random(360f), rand.random(e.finpow() * 14f)).add(e.x, e.y);
            Fill.circle(v.x, v.y, rand.random(1.4f, 3.4f));
        }
    }).layer(Layer.bullet - 1f),
    //forget it ill use ParticleEffect instead
    shootTerraSmoke = new ParticleEffect(){{
        colorFrom = KrathaPal.terraplasmLight;
        colorTo = KrathaPal.terraplasmDark.alpha(0.5f);
        cone = 60;
        sizeFrom = 8;
        sizeTo = 0;
        length = 50;
    }};
}
