package su.external.adventure;

import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import su.external.adventure.config.Config;
import su.external.adventure.entity.AdventureEntities;
import su.external.adventure.entityrain.command.RainArgumentType;
import su.external.adventure.entityrain.data.EntityRainLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import su.external.adventure.init.AdventureSounds;
import su.external.adventure.network.PacketHandler;
import su.external.adventure.trade.TradeOffersLoader;
import su.external.adventure.item.AdventureItems;

import java.util.concurrent.CompletableFuture;


// The value here should match an entry in the META-INF/mods.toml file
@Mod("adventure")
public class Adventure
{
    public static final String MOD_ID = "adventure";
    public static final String MOD_VERSION = "${version}";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogManager.getLogger();
    public static EntityRainLoader ENTITY_RAIN_LOADER = null;
    public Adventure()
    {
        LOGGER.info("Initializing Adventure");
        Config.setup();
        Adventure.LOGGER.info("Loaded Adventure Config");
        Adventure.LOGGER.info(Config.pirateCaptain.maxHealth.get());
        Adventure.LOGGER.info(Config.pirateCaptain.coinMultiplier.get());
        ArgumentTypes.register("tribe", RainArgumentType.class, new EmptyArgumentSerializer<>(RainArgumentType::new));
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        AdventureEntities.register(eventBus);
        AdventureSounds.register(eventBus);
        AdventureItems.register(eventBus);
        eventBus.addListener(AdventureEntities::createAttributes);
        eventBus.addListener(this::setup);
        PacketHandler.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(this::clientSetup));

        GeckoLib.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Adventure Common Setup");
        AdventureEntities.registerEntityWorldSpawns();
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Adventure Client Setup");
        AdventureEntities.registerEntityRenderers();
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(35000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            TradeOffersLoader.load();
            event.getServer().getPlayerList().getPlayers().forEach(player -> player.sendMessage(new TextComponent("Loaded trade offers"), player.getUUID()));
        });
    }
    public static ResourceLocation id(String path) {
        return new ResourceLocation("adventure", path);
    }
}
