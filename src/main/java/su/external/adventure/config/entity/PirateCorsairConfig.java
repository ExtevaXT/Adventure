package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class PirateCorsairConfig {
    public final ForgeConfigSpec.ConfigValue<Double> coinMultiplier, maxHealth, attackDamage;
    public PirateCorsairConfig(ForgeConfigSpec.Builder builder) {
        builder.push("PirateCorsair");
        coinMultiplier = builder.define("coinMultiplier", 1.25D);
        maxHealth = builder.define("maxHealth", 30D);
        attackDamage = builder.define("attackDamage", 3D);
        builder.pop();
    }
}