package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class OgreConfig {
    public final ForgeConfigSpec.ConfigValue<Double>  maxHealth, attackDamage, attackSpeed, movementSpeed, followRange, attackRange;
    public OgreConfig(ForgeConfigSpec.Builder builder) {
        builder.push("OgreEntity");
        maxHealth = builder.define("maxHealth", 100D);
        attackDamage = builder.define("attackDamage", 2.5D);
        attackSpeed = builder.define("attackSpeed", 0.5D);
        movementSpeed = builder.define("movementSpeed", 0.2D);
        followRange = builder.define("followRange", 16D);
        attackRange = builder.define("attackRange", 3D);
        builder.pop();
    }
}
