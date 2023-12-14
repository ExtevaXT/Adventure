package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class PirateCaptainConfig {
    public final ForgeConfigSpec.ConfigValue<Double> coinMultiplier, maxHealth, attackDamage, movementSpeed;
    public PirateCaptainConfig(ForgeConfigSpec.Builder builder) {
        builder.push("PirateCaptain");
        coinMultiplier = builder.define("coinMultiplier", 2D);
        maxHealth = builder.define("maxHealth", 60D);
        attackDamage = builder.define("attackDamage", 5D);
        movementSpeed = builder.define("movementSpeed", 0.4D);
        builder.pop();
    }
}