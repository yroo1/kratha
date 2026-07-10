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
import kratha.world.blocks.environment.MarkerOverlay;
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
            skewerMarker,cradleMarker,
            falseCore,dataPatchInfo,
            heart, root, eye, harvester, cradle, skewer, trim;
    public static void load() {
        {
            {
                skewerMarker = new MarkerOverlay("skewer-marker");
                cradleMarker = new MarkerOverlay("cradle-marker");
                falseCore = new FalseCoreBlock("false-core"){{
                    //fake core to imitate attack mode, to be destroyed by wproc
                    targetable = false;
                    forceDark = true;
                    privileged = true;
                    size = 1;
                }};
                dataPatchInfo = new DPInfoBlock("data-patch-info");
                heart = new BioHeart("heart"){{
                    requirements(Category.distribution, with(KrathaItems.krathite, 10));
                    size = 3;
                    health = 800;
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
                    variants = 2;
                }};
                cradle = new BioSpawner("cradle"){{
                    requirements(Category.distribution, with(KrathaItems.guartz, 5,KrathaItems.cobalt,5));
                    health = 350;
                    size = 3;
                    unitType = KrathaUnitTypes.terrapillar;
                    inputItem1 = KrathaItems.guartz;
                    requiredItem1 = 3;
                    inputItem2 = KrathaItems.cobalt;
                    requiredItem2 = 2;
                }};
                skewer = new BioTurret("skewer"){{
                    health = 300;
                    requirements(Category.turret, with(KrathaItems.guartz, 5));
                    reload = 85f;
                    inaccuracy = 2f;
                    size = 3;
                    recoil = 7f;
                    range = 26 * Vars.tilesize;
                    rotateSpeed = 1f;
                    squareSprite = false;
                    minWarmup = 0.93f;
                    shootWarmupSpeed = 0.07f;
                    shootY = 2;
                    shake = 2f;
                    newTargetInterval = 40f;
                    targetAir = false;
                    shootSound = Sounds.shootArtillery;

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
                trim = new BioTurret("trim"){{
                    requirements(Category.turret, with(KrathaItems.krathite,4));
                    range = 165f;

                    shoot.firstShotDelay = 30;

                    recoil = 2f;
                    reload = 50f;
                    shake = 0f;
                    shootEffect = new ParticleEffect(){{
                        colorFrom = colorTo = KrathaPal.krathiteLight;
                        cone = 90;
                        particles = 7;
                        sizeFrom = 3;
                        sizeTo = 0;
                        length = 50;
                        lifetime = 80;
                        interp = Interp.pow3Out;
                    }};
                    smokeEffect = Fx.none;
                    heatColor = KrathaPal.krathiteLight
                    size = 2;
                    targetAir = false;
                    moveWhileCharging = false;
                    accurateDelay = false;
                    shootSound = Sounds.shootLancer;
            
                    chargeSound = Sounds.chargeLancer;

                    ammoItem = KrathaItems.krathite;
                    maxAmmo = 5;
                    ammoItemMultiplier = 5;
                    range = 120;
                    shootY = 11;

            
                    shootType = new BasicBulletType(){{
                        chargeEffect = new MultiEffect(
                            new ParticleEffect(){{
                                colorFrom = KrathaPal.krathiteLight;
                                colorTo = KrathaPal.krathite;
                                cone = 180;
                                particles = 7;
                                sizeFrom = 0;
                                sizeTo = 3;
                                length = -30;
                                baseLength = 30;
                                lifetime = 30;
                                interp = Interp.pow2In;
                            }},
                            new WaveEffect(){{
                                colorFrom = KrathaPal.krathiteLight;
                                colorTo = KrathaPal.krathiteLight;
                                sizeFrom = 15;
                                sizeTo = 0;
                                lightColor = KrathaPal.krathiteLight;
                                lightInterp = Interp.linear;
                                lifetime = 30;
                            }}
                        );

                        shootEffect = Fx.none;
                        smokeEffect = Fx.none;

                        despawnEffect = hitEffect = new ParticleEffect(){{
                            colorFrom = KrathaPal.krathiteLight;
                            colorTo = KrathaPal.krathiteDark.a(0.1f);
                            cone = 180;
                            particles = 7;
                            sizeFrom = 3;
                            sizeTo = 0;
                            length = 40;
                            lifetime = 60;
                        }};
                        knockback = 4f;
                        height = 19f;
                        width = 5f;
                        damage = 40;
                        speed = 24;
                        lifetime = 5;
                        backColor = hitColor = trailColor = KrathaPal.krathiteLight;
                        frontColor = KrathaPal.krathiteLight;
                        trailLength = 30;
                        trailWidth = 2f;
                        trailSinScl = 2.5f;
                        trailSinMag = 0.5f;
                        trailEffect = new ParticleEffect(){{
                            colorFrom = colorTo = KrathaPal.krathite;
                            cone = 45;
                            particles = 2;
                            sizeFrom = 2;
                            sizeTo = 0;
                            length = 10;
                            lifetime = 60;
                            interp = Interp.pow2Out;
                        }}; 
                        despawnShake = 0.5f;
                        trailInterval = 1;
                        trailChance = 1;
                        
                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        buildingDamageMultiplier = 1f;
                        pierceCap=8;
                    }};
                    drawer = new DrawTurret(){{
                        parts.add(
                            new RegionPart("-side-r"){{
                                progress = PartProgress.warmup;
                                under = false;
                                moveRot = 4;
                                moveX = -1;
                                heatColor = KrathaPal.krathiteLight;
                            }}
                        );
                        parts.add(
                            new RegionPart("-side-l"){{
                                progress = PartProgress.warmup;
                                under = false;
                                moveRot = -4;
                                moveX = 2;
                                heatColor = KrathaPal.krathiteLight;
                            }}
                        );
                    }};
                }};    
            }
        }
    }
}
