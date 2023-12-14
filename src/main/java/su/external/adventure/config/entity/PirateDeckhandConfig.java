package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class PirateDeckhandConfig {
    public final ForgeConfigSpec.ConfigValue<Double> attackSpeed, movementSpeed;
    public PirateDeckhandConfig(ForgeConfigSpec.Builder builder) {
        builder.push("PirateDeckhand");
        attackSpeed = builder.define("attackSpeed", 10D);
        movementSpeed = builder.define("movementSpeed", 0.6D);
        builder.pop();
    }
}
