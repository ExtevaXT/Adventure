package su.external.adventure;

import dev.ftb.mods.ftbchunks.FTBChunksCommands;
import dev.ftb.mods.ftbchunks.data.FTBChunksAPI;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.ConnectionType;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import oshi.util.tuples.Pair;
import su.external.adventure.entity.AdventureEntityTypes;
import su.external.adventure.entity.HumanoidRenderer;
import su.external.adventure.entityrain.command.RainArgumentType;
import su.external.adventure.entityrain.data.EntityRainLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import su.external.adventure.init.ClientSetup;
import su.external.adventure.init.EntitySpawnRegistration;
import su.external.adventure.init.TradeOffersLoader;
import su.external.adventure.item.AdventureItems;
import su.external.adventure.util.TradeOffer;

import java.util.List;
import java.util.concurrent.CompletableFuture;


// The value here should match an entry in the META-INF/mods.toml file
@Mod("adventure")
public class Adventure
{
    public static final String MOD_ID = "adventure";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogManager.getLogger();
    public static EntityRainLoader ENTITY_RAIN_LOADER = null;
    public Adventure()
    {
        LOGGER.info("Initializing Adventure");
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ArgumentTypes.register("tribe", RainArgumentType.class, new EmptyArgumentSerializer<>(RainArgumentType::new));
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AdventureEntityTypes.register(eventBus);
        AdventureItems.register(eventBus);
        eventBus.addListener(this::setup);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(this::clientSetup));
    }
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Adventure Common Setup");
        EntitySpawnRegistration.registerEntityWorldSpawns();
    }
    private void clientSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Adventure Client Setup");
        ClientSetup.registerEntityRenderers();
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // MORE CRUTCHES \o/
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