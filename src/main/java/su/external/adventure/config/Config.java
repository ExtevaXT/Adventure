package su.external.adventure.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import su.external.adventure.config.entity.*;

public class Config {
    public static ForgeConfigSpec spec;
    public static ClaimConfig claim;
    public static BigSlimeConfig bigSlime;
    public static BrigandConfig brigand;
    public static GoblinArcherConfig goblinArcher;
    public static GoblinPeonConfig goblinPeon;
    public static GoblinWarriorConfig goblinWarrior;
    public static MonsterEntityConfig monsterEntity;
    public static HumanoidEntityConfig humanoidEntity;
    public static MeleeEntityConfig meleeEntity;
    public static PirateCaptainConfig pirateCaptain;
    public static PirateCorsairConfig pirateCorsair;
    public static PirateCrossbowerConfig pirateCrossbower;
    public static PirateDeckhandConfig pirateDeckhand;
    public static RangedEntityConfig rangedEntity;
    public static CrawlerEntityConfig crawlerEntity;
    public static CaveCreepConfig caveCreepEntity;
    public static OgreConfig ogreEntity;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("general");
        claim = new ClaimConfig(builder);
        bigSlime = new BigSlimeConfig(builder);
        brigand = new BrigandConfig(builder);
        goblinArcher = new GoblinArcherConfig(builder);
        goblinPeon = new GoblinPeonConfig(builder);
        goblinWarrior = new GoblinWarriorConfig(builder);
        monsterEntity = new MonsterEntityConfig(builder);
        humanoidEntity = new HumanoidEntityConfig(builder);
        meleeEntity = new MeleeEntityConfig(builder);
        pirateCaptain = new PirateCaptainConfig(builder);
        pirateCorsair = new PirateCorsairConfig(builder);
        pirateCrossbower = new PirateCrossbowerConfig(builder);
        pirateDeckhand = new PirateDeckhandConfig(builder);
        rangedEntity = new RangedEntityConfig(builder);
        crawlerEntity = new CrawlerEntityConfig(builder);
        caveCreepEntity = new CaveCreepConfig(builder);
        ogreEntity = new OgreConfig(builder);

        spec = builder.build();
    }
    public static void setup() {
        CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("adventure-common.toml"))
                .sync()
                .autosave()
                .preserveInsertionOrder()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}
