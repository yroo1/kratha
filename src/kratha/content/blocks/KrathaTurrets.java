package kratha.content.blocks;

import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import kratha.graphics.*;
import kratha.content.*;
import mindustry.content.*;
import mindustry.entities.effect.MultiEffect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.RegionPart.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.pattern.*;
import mindustry.content.Fx.*;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.consumers.ConsumeLiquids;
import mindustry.world.draw.DrawTurret;
import mindustry.entities.effect.*;

import static mindustry.type.ItemStack.with;
import static mindustry.Vars.*;

public class KrathaTurrets {
    public static Block
            impede,debase;
    public static void load() {
        {
            {
                impede = new ItemTurret("impede"){{
                    requirements(Category.turret, with(KrathaItems.krathite, 50, KrathaItems.guartz, 40, KrathaItems.spurstone, 20));
                    researchCost = with(KrathaItems.krathite, 100, KrathaItems.guartz, 80, KrathaItems.spurstone,40);
  
                    health = 180;
                    fogRadiusMultiplier = 0f;
                    maxAmmo = 5;
                    outlineColor = KrathaPal.krathaOutline;
                    reload = 50f;
                    inaccuracy = 0.5f;
                    size = 2;
                    recoil = 1f;
                    range = 14 * Vars.tilesize;
                    rotateSpeed = 2.7f;
                    squareSprite = true;
                    shootSound = Sounds.shootTank;
                    minWarmup = 0.8f;
                    shootWarmupSpeed = 0.07f;
                    shootY = 1f;

                    ammo(
                        KrathaItems.krathite, new BasicBulletType(3.8f, 32) {{
                            lifetime = 30f;
                            width = 8f;
                            height = 14f;
                            weaveMag = 2;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                            hitColor = backColor = trailColor = KrathaPal.krathiteDark;
                            frontColor = KrathaPal.krathiteLight;
                            trailWidth = 2.1f;
                            trailLength = 7;
                            shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
                            smokeEffect = Fx.shootBigSmoke;
                        }}
                    );
                    drawer = new DrawTurret(){{
                        parts.add(
                            new RegionPart("-mid"){{
                                progress = PartProgress.recoil;
                                mirror = false;
                                under = true;
                                moveY = -2.5f;
                            }}
                        );
                    }};
                }};
                debase = new ItemTurret("debase"){{
                    requirements(Category.turret, with(KrathaItems.krathite, 30, KrathaItems.guartz, 70, KrathaItems.spurstone, 30));
                    researchCost = with(KrathaItems.krathite, 250, KrathaItems.guartz, 300, KrathaItems.spurstone,100);
  
                    health = 150;
                    fogRadiusMultiplier = 0f;
                    maxAmmo = 5;
                    outlineColor = KrathaPal.krathaOutline;
                    reload = 80f;
                    inaccuracy = 0f;
                    size = 2;
                    recoil = 1.5f;
                    range = 19 * Vars.tilesize;
                    rotateSpeed = 2.4f;
                    squareSprite = true;
                    shootSound = Sounds.shootDisperse;
                    minWarmup = 0.9f;
                    shootWarmupSpeed = 0.05f;
                    shootY = 1f;

                    ammo(
                        KrathaItems.guartz, new ArtilleryBulletType(3.1f, 40) {{
                            lifetime = 50f;
                            width = 8f;
                            height = 14f;
                            weaveMag = 2;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                            hitColor = backColor = trailColor = KrathaPal.guartzDark;
                            frontColor = KrathaPal.guartzLight;
                            trailWidth = 2.1f;
                            trailLength = 7;
                            trailLength = 20;
                            trailWidth = 3.35f;
                            trailSinScl = 2.5f;
                            trailSinMag = 0.5f;
                            trailEffect = Fx.none;
                            despawnShake = 0.5f;
                            splashDamageRadius = 8f;
                            splashDamage = 45f;
                            scaledSplashDamage = true;
                            shootEffect = KrathaFx.shootSkewer;
                            smokeEffect = KrathaFx.shootSmokeSkewer;
                        }}
                    );
                    drawer = new DrawTurret(){{
                        parts.add(
                            new RegionPart("-side"){{
                                progress = PartProgress.warmup;
                                mirror = true;
                                under = true;
                                moveX = 1f;
                            }}
                            new RegionPart("-outer"){{
                                progress = PartProgress.warmup;
                                mirror = true;
                                under = true;
                                moveX = 2f;
                            }}
                        );
                    }};
                }};
            }
        }
    }
}
