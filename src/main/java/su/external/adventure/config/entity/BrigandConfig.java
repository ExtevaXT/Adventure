package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class BrigandConfig {
    public final ForgeConfigSpec.ConfigValue<Double> speedModifier, attackRadius;
    public final ForgeConfigSpec.ConfigValue<Integer> attackIntervalMin;
    public BrigandConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Brigand");
        speedModifier = builder.comment("Ranged Goal SpeedModifier").define("speedModifier", 1D);
        attackIntervalMin = builder.comment("Ranged Goal AttackIntervalMin").define("attackIntervalMin", 20);
        attackRadius = builder.comment("Ranged Goal AttackRadius").define("attackRadius", 12D);
        builder.pop();
    }
}
