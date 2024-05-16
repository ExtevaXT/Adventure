package su.external.adventure.init;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.external.adventure.Adventure;
import su.external.adventure.command.ClaimCommand;
import su.external.adventure.command.DebugCommand;
import su.external.adventure.command.TradesCommand;
import su.external.adventure.entityrain.command.RainEventCommand;

@Mod.EventBusSubscriber(modid = Adventure.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandInit {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        RainEventCommand.register(event.getDispatcher());
        ClaimCommand.register(event.getDispatcher());
        TradesCommand.register(event.getDispatcher());
        DebugCommand.register(event.getDispatcher());
    }
}