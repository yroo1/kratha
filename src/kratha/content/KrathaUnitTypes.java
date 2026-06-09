package kratha.content;

import arc.graphics.*;
import arc.math.Interp;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import kratha.type.unit.*;
import kratha.graphics.KrathaPal;
import kratha.content.KrathaFx;
import mindustry.type.weapons.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

public class KrathaUnitTypes{
    public static UnitType
    keris,
    rocky,
    settler;
    public static void load(){
        //region special
        //peak name
        keris = new DeearthUnitType("keris"){{
            constructor = MechUnit::create;
            speed = 0.7f;
            hitSize = 9f;
            health = 180;
            alwaysUnlocked = true;
            weapons.add(new Weapon("kratha-keris-weapon"){{
                reload = 70f;
                cooldownTime = 60f;
                x = 6.5f;
                y = 0f;
                top = false;
                ejectEffect = Fx.none;
                shootSound = Sounds.shootFuse;
                shootSoundVolume = 0.75f;
                bullet = new ShrapnelBulletType(){{
                    length = 24f;
                    damage = 42f;
                    toColor = KrathaPal.guartz;
                    serrations = 5;
                    shootEffect = smokeEffect = KrathaFx.kerisShoot;
                }};
            }});
            
        }};
        //region krathian
        rocky = new KrathianUnitType("rocky"){{
            constructor = LegsUnit::create;
            speed = 0.85f;
            drag = 0.3f;
            hitSize = 12f;
            rotateSpeed = 4f;
            health = 290;
            legCount = 5;
            legLength = 25f;
            legForwardScl = 0.8f;
            legMoveSpace = 1f;
            legGroupSize = 1;
            legBaseOffset = 2f;
            hovering = true;
            armor = 10f;

            shadowElevation = 0.3f;
            groundLayer = Layer.legUnit;

            stepSound = Sounds.walkerStepSmall;
            stepSoundPitch = 0.7f;
            stepSoundVolume = 0.35f;

            weapons.add(new Weapon(){{
                reload = 27f;
                x = 0;
                ejectEffect = Fx.none;
                rotate = true;
                shootSound = Sounds.shootSap;
                mirror = false;
                bullet = new BasicBulletType(){{
                    damage = 40;
                    shootEffect = despawnEffect = smokeEffect = Fx.none;
                    width = 0;
                    height = 0;
                    lifetime = 2f;
                    speed = 10;
                }};
            }});
        }};
        //region core
        settler = new KrathaUnitType("settler"){{
            constructor = UnitEntity::create;
            coreUnitDock = true;
            controller = u -> new BuilderAI(true, 500f);
            flying = true;
            isEnemy = false;
            envDisabled = 0;

            range = 80f;
            faceTarget = true;
            mineWalls = true;
            mineFloor = true;
            mineHardnessScaling = false;
            mineSpeed = 6f;
            mineTier = 2;
            buildSpeed = 1.2f;
            drag = 0.03f;
            speed = 4f;
            rotateSpeed = 7f;
            accel = 0.18f;
            itemCapacity = 30;
            health = 300f;
            hitSize = 9f;
            fogRadius = 30;
            engineOffset = 6f;

            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 0f;
                y = 1f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                repairSpeed = 3.1f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;
                mirror = false;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;

                bullet = new BulletType(){{
                    maxRange = 80f;
                }};
            }});
        }};
    }
}
