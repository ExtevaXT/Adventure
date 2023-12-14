package su.external.adventure.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import su.external.adventure.Adventure;

public class PacketHandler {
    private static final String VERSION = Adventure.MOD_VERSION;
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation("adventure", "network"), () -> VERSION, VERSION::equals, VERSION::equals);
    public static void init(){
        // Server -> Client
    }
}