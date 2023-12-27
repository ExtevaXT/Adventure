package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class CrawlerEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double> maxHealth, attackDamage, attackSpeed, movementSpeed, followRange, maxBrightness;
    public final ForgeConfigSpec.ConfigValue<Integer> scanRadius, maxDrop;
    public CrawlerEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("CrawlerEntity");
        scanRadius = builder.define("scanRadius", 24);
        maxDrop = builder.define("maxDrop", 4);
        maxHealth = builder.define("maxHealth", 30D);
        attackDamage = builder.define("attackDamage", 4D);
        attackSpeed = builder.define("attackSpeed", 1.5D);
        movementSpeed = builder.define("movementSpeed", 0.35D);
        followRange = builder.define("followRange", 16D);
        maxBrightness = builder.define("maxBrightness", 4D);
        builder.pop();
    }
}
