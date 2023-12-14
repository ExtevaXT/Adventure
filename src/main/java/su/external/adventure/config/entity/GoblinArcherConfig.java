package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class GoblinArcherConfig {
    public final ForgeConfigSpec.ConfigValue<Double> coinMultiplier, speedModifier, attackRadius;
    public final ForgeConfigSpec.ConfigValue<Integer> attackIntervalMin;
    public GoblinArcherConfig(ForgeConfigSpec.Builder builder) {
        builder.push("GoblinArcher");
        coinMultiplier = builder.define("coinMultiplier", 1.25D);
        speedModifier = builder.comment("Ranged Goal SpeedModifier").define("speedModifier", 1D);
        attackIntervalMin = builder.comment("Ranged Goal AttackIntervalMin").define("attackIntervalMin", 20);
        attackRadius = builder.comment("Ranged Goal AttackRadius").define("attackRadius", 15D);
        builder.pop();
    }
}