package kratha.content;

import arc.func.Cons;
import arc.graphics.Color;
import arc.math.Rand;
import arc.math.geom.Mat3D;
import arc.util.Tmp;
import arc.struct.Seq;
import kratha.content.blocks.KrathaStorage;
import mindustry.content.Blocks;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.maps.planet.ErekirPlanetGenerator;
import kratha.planet.*;
import kratha.content.KrathaTeams;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.meta.Env;
import mindustry.maps.planet.*;
import mindustry.graphics.g3d.*;

public class KrathaPlanets{
    public static Planet
            //the 4 stars...
            ryii,njii,khethar,hitroi,

    //planets
    deearth,kratha;

    public static void load(){
        // regions stars
        ryii = new Planet("ryii", null, 6f, 0){{
            accessible = true;
            hasAtmosphere = true;
            solarSystem = this;
            iconColor = Color.valueOf("B8D9DD");
            meshLoader = () -> new SunMesh(
                    this, 7, 8, 0.4f, 0.7f, 1.4f, 1.6f, 1.2f,

                    Color.valueOf("8EAFC3"),
                    Color.valueOf("A1C6CF"),
                    Color.valueOf("B8D9DD"),
                    Color.valueOf("D4EDEF"),
                    Color.valueOf("FFFFFF")
            );
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 7, 2.7f, 0.1f, 5, Color.valueOf("EEF3FF").a(0.4f),3,0.42f, 1f, 0.43f)
            );
        }};
        njii = new Planet("njii", ryii, 2.5f, 0){{
            accessible = false;
            hasAtmosphere = true;
            solarSystem = ryii;
            orbitRadius = 30f;
            iconColor = Color.valueOf("E6E1BE");

            meshLoader = () -> new SunMesh(
                    this, 6, 8, 0.6f, 1.5f, 1.4f, 1.6f, 1.2f,

                    Color.valueOf("E4D563"),
                    Color.valueOf("DED595"),
                    Color.valueOf("E6E1BE"),
                    Color.valueOf("FFFEFB")
            );
        }};
        khethar = new Planet("khethar", ryii, 2f, 0){{
            accessible = true;
            hasAtmosphere = true;
            solarSystem = ryii;
            orbitRadius = 70f;
            iconColor = Color.valueOf("D09287");

            meshLoader = () -> new SunMesh(
                    this, 5, 8, 0.4f, 0.7f, 1.4f, 1.6f, 1.2f,

                    Color.valueOf("A2615D"),
                    Color.valueOf("BD7771"),
                    Color.valueOf("D09287"),
                    Color.valueOf("EFC4B1"),
                    Color.valueOf("FFDFCB")
            );
        }};
        hitroi = new Planet("hitroi", ryii, 1.5f, 0){{
            accessible = false;
            hasAtmosphere = true;
            solarSystem = ryii;
            orbitRadius = 140f;
            iconColor = Color.valueOf("D696DE");

            meshLoader = () -> new SunMesh(
                    this, 5, 7, 0.7f, 0.7f, 1.4f, 1.6f, 1.2f,

                    Color.valueOf("6930B3"),
                    Color.valueOf("A25CC3"),
                    Color.valueOf("D696DE"),
                    Color.valueOf("FEDCFF")
            );
        }};

        // region planets
        kratha = new Planet("kratha", hitroi, 1f, 4){{
            accessible = true;
            hasAtmosphere = true;
            orbitSpacing = 1;
            iconColor = atmosphereColor = Color.valueOf("ED93FF");
            solarSystem = ryii;
            alwaysUnlocked = false;
            generator = new KrathaPlanetGenerator();
            alwaysUnlocked = clearSectorOnLose = true;
            allowLaunchLoadout = allowLaunchSchematics = false;
            defaultCore = KrathaStorage.coreRelic;
            ruleSetter = r -> {
              r.fog = true;
              r.staticFog = false;
              r.onlyDepositCore = true;
              r.waveTeam = KrathaTeams.terraplasm;
            };
            allowLaunchToNumbered = false;
            updateLighting = true;
            campaignRuleDefaults.fog = true;
            startSector = 569;
            minZoom = 0.3f;
            bloom = true;
            meshLoader = () -> new MultiMesh(
                new HexMesh(this, 7)
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 11, 2.7f, 0.1f, 5, Color.valueOf("D696DE").a(0.4f), 7, 0.4f, 2f, 0.43f)             
            );
        }};
        deearth = new Planet("deearth", khethar, 1f, 4){{
            accessible = false;
            hasAtmosphere = true;
            landCloudColor = Color.valueOf("DBF3FF");
            atmosphereColor = Color.valueOf("9AC0DB");
            atmosphereRadIn = 0.01f;
            atmosphereRadOut = 0.3f;
            orbitTime = 60f*20f;
            rotateTime = 60f*12.3f;
            orbitSpacing = 1;
            orbitRadius = 15f;
            iconColor = Color.valueOf("9AC0DB");
            solarSystem = ryii;
            generator = new DeearthPlanetGenerator();
            meshLoader = () -> new MultiMesh(
                new HexMesh(this, 6)
            );
            cloudMeshLoader = () -> new MultiMesh(
                new HexSkyMesh(this, 11, 2.7f, 0.1f, 5, Color.valueOf("BAD1D4").a(0.4f), 7, 0.4f, 2f, 0.43f)             
            );
        }};
    }
    private static Planet makeAsteroid(String name, Planet parent, Block base, Block tint, int seed, float tintThresh, int pieces, float scale, Cons<AsteroidGenerator> cgen){
        return new Planet(name, parent, 0.12f){{
            hasAtmosphere = false;
            updateLighting = false;
            camRadius = 0.68f * scale;
            minZoom = 0.6f;
            drawOrbit = false;
            accessible = false;
            clipRadius = 2f;
            defaultEnv = Env.space;
            icon = "commandRally";
            generator = new AsteroidGenerator();
            cgen.get((AsteroidGenerator)generator);

            meshLoader = () -> {
                iconColor = tint.mapColor;
                Color tinted = tint.mapColor.cpy().a(1f - tint.mapColor.a);
                Seq<GenericMesh> meshes = new Seq<>();
                Color color = base.mapColor;
                Rand rand = new Rand(id + 2);

                meshes.add(new NoiseMesh(
                    this, seed, 2, radius, 2, 0.55f, 0.45f, 14f,
                    color, tinted, 3, 0.6f, 0.38f, tintThresh
                ));

                for(int j = 0; j < pieces; j++){
                    meshes.add(new MatMesh(
                        new NoiseMesh(this, seed + j + 1, 1, 0.022f + rand.random(0.039f) * scale, 2, 0.6f, 0.38f, 20f,
                        color, tinted, 3, 0.6f, 0.38f, tintThresh),
                        new Mat3D().setToTranslation(Tmp.v31.setToRandomDirection(rand).setLength(rand.random(0.44f, 1.4f) * scale)))
                    );
                }

                return new MultiMesh(meshes.toArray(GenericMesh.class));
            };
        }};
    }
              }
