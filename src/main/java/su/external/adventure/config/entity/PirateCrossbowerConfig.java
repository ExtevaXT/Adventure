package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class PirateCrossbowerConfig {
    public final ForgeConfigSpec.ConfigValue<Double> coinMultiplier;
    public PirateCrossbowerConfig(ForgeConfigSpec.Builder builder) {
        builder.push("PirateCrossbower");
        coinMultiplier = builder.define("coinMultiplier", 1.5D);
        builder.pop();
    }
}