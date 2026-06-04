package kratha.content.terraplasm;

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
            heart, root, eye;
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
                    requirements(Category.distribution, with(KrathaItems.guartz, 2));
                    health = 70;
                }};
            }
        }
    }
}
