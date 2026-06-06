package kratha.content.terraplasm;

import arc.math.*;
import arc.graphics.Color;
import mindustry.world.Block;
import mindustry.world.blocks.storage.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.gen.Sounds;
import mindustry.content.*;
import kratha.world.blocks.terraplasm.*;
import kratha.content.*;
import kratha.graphics.KrathaPal;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.storage.*;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.consumers.ConsumeLiquids;
import mindustry.world.draw.DrawTurret;
import mindustry.entities.effect.*;
import mindustry.entities.effect.MultiEffect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.RegionPart.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.content.Fx.*;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSpread;

import static mindustry.type.ItemStack.with;

public class Terraplasm {
    public static Block
            falseCore,
            heart, root, eye, harvester, skewer;
    public static void load() {
        {
            {
                falseCore = new CoreBlock("false-core"){{
                    //fake core to imitate attack mode, to be destroyed by wproc
                    targetable = false;
                    forceDark = true;
                    privileged = true;
                    size = 1;
                }};
                heart = new BioHeart("heart"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 10));
                    size = 3;
                    health = 700;
                }};
                root = new Root("root"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 1));
                    health = 10;
                }};
                eye = new BioEye("eye"){{
                    requirements(Category.distribution, with(KrathaItems.guartz, 1));
                    health = 70;
                }};
                harvester = new BioDrill("harvester"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 1));
                    health = 140;
                    size = 2;
                }};
                skewer = new BioTurret("skewer"){{
                    requirements(Category.turret, with(KrathaItems.guartz, 5));
                    reload = 85f;
                    inaccuracy = 2f;
                    size = 3;
                    recoil = 7f;
                    range = 26 * Vars.tilesize;
                    rotateSpeed = 1f;
                    squareSprite = false;
                    minWarmup = 0.8f;
                    shootWarmupSpeed = 0.07f;
                    shootY = 2;
                    shake = 2f;
                    newTargetInterval = 40f;
                    targetAir = false;

                    ammoItem = KrathaItems.guartz;
                    maxAmmo = 5;
                    ammoItemMultiplier = 3;
                    
                    shootType = new ArtilleryBulletType(4.3f, 50f, "shell"){{
                        hitEffect = KrathaFx.skewerSmoke;
                        despawnEffect = Fx.none;
                        knockback = 2f;
                        height = 19f;
                        width = 17f;
                        splashDamageRadius = 10f;
                        splashDamage = 60f;
                        scaledSplashDamage = true;
                        backColor = hitColor = trailColor = KrathaPal.guartz.lerp(KrathaPal.guartzDark, 0.5f);
                        frontColor = Color.white;
                        trailLength = 32;
                        trailWidth = 3.35f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = Fx.none;
                        despawnShake = 2f;
,
                        shootEffect = KrathaFx.shootSkewer;
                        smokeEffect = KrathaFx.shootSmokeSkewer;
                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        buildingDamageMultiplier = 1f;
                    }};
                    drawer = new DrawTurret(){{
                        parts.add(
                            new RegionPart("-side-right"){{
                                progress = PartProgress.warmup;
                                under = false;
                                moveRot = -4;
                                moveX = 4;
                            }}
                        );
                        parts.add(
                            new RegionPart("-side-left"){{
                                progress = PartProgress.warmup;
                                under = false;
                                moveRot = 4;
                                moveX = -4;
                            }}
                        );
                    }};
                }};
            }
        }
    }
}
