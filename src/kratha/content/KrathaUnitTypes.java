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
import mindustry.type.weapons.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

public class KrathaUnitTypes{
    public static UnitType
    keris,
    settler;
    public static void load(){
        //region special
        //peak name
        keris = new DeearthUnitType("keris"){{
            constructor = MechUnit::create;
            speed = 0.7f;
            hitSize = 9f;
            health = 180;
            weapons.add(new Weapon("kratha-keris-weapon"){{
                reload = 70f;
                x = 6.5f;
                y = 0f;
                top = false;
                ejectEffect = Fx.none;
                bullet = new ShrapnelBulletType(){{
                    length = 24f;
                    damage = 42f;
                    toColor = KrathaPal.guartzLight;
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
            mineWalls = false;
            mineFloor = true;
            mineHardnessScaling = false;
            mineSpeed = 6f;
            mineTier = 3;
            buildSpeed = 1.2f;
            drag = 0.03f;
            speed = 3.2f;
            rotateSpeed = 7f;
            accel = 0.15f;
            itemCapacity = 30;
            health = 300f;
            hitSize = 9f;
            fogRadius = -1;
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
