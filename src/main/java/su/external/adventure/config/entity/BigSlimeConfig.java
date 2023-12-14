package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class BigSlimeConfig {
    public final ForgeConfigSpec.ConfigValue<Double> maxHealth, attackDamage, attackSpeed, movementSpeed, followRange;
    public BigSlimeConfig(ForgeConfigSpec.Builder builder) {
        builder.pop().push("BigSlime");
        maxHealth = builder.define("maxHealth", 100D);
        attackDamage = builder.define("attackDamage", 10D);
        attackSpeed = builder.define("attackSpeed", 2D);
        movementSpeed = builder.define("movementSpeed", 0.5D);
        followRange = builder.define("followRange", 32D);
        builder.pop();
    }
}
