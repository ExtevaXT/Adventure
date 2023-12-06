package su.external.adventure.init;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import su.external.adventure.Adventure;
import su.external.adventure.entity.AdventureEntityTypes;
import su.external.adventure.entity.HumanoidRenderer;

public class ClientSetup {
    @OnlyIn(Dist.CLIENT)
    public static void registerEntityRenderers() {
        EntityRenderers.register(AdventureEntityTypes.BANDIT.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/bandit.png")));
        EntityRenderers.register(AdventureEntityTypes.BRIGAND.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/brigand.png")));
        EntityRenderers.register(AdventureEntityTypes.GOBLIN_ARCHER.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/goblin.png")));
        EntityRenderers.register(AdventureEntityTypes.GOBLIN_PEON.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/goblin.png")));
        EntityRenderers.register(AdventureEntityTypes.GOBLIN_WARRIOR.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/goblin.png")));

        EntityRenderers.register(AdventureEntityTypes.PIRATE_CAPTAIN.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/pirate_captain.png")));
        EntityRenderers.register(AdventureEntityTypes.PIRATE_CORSAIR.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/pirate.png")));
        EntityRenderers.register(AdventureEntityTypes.PIRATE_CROSSBOWER.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/pirate.png")));
        EntityRenderers.register(AdventureEntityTypes.PIRATE_DECKHAND.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/pirate.png")));

        EntityRenderers.register(AdventureEntityTypes.TRADER.get(), (context) -> new HumanoidRenderer(context, Adventure.id("textures/entities/merchant.png")));

        EntityRenderers.register(AdventureEntityTypes.BIG_SLIME.get(), SlimeRenderer::new);
    }
}
