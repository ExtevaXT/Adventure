package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class RangedEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double> accuracy;
    public RangedEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("RangedEntity");
        accuracy = builder.comment("Default values are by difficulty probably 2, 6, 10").define("accuracy", 12D);
        builder.pop();
    }
}
