package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class GoblinPeonConfig {
    public final ForgeConfigSpec.ConfigValue<Double> attackSpeed, movementSpeed;
    public GoblinPeonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("GoblinPeon");
        attackSpeed = builder.define("attackSpeed", 4D);
        movementSpeed = builder.define("movementSpeed", 0.5D);
        builder.pop();
    }
}