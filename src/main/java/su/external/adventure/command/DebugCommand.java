package su.external.adventure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import su.external.adventure.Adventure;

import java.util.BitSet;

public class DebugCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("adbg").executes(DebugCommand::execute));
    }
    private static int execute(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        Player player = command.getSource().getPlayerOrException();
        Level level = command.getSource().getLevel();
        if(player instanceof ServerPlayer sPlayer) {
            sPlayer.connection.send(new ClientboundLevelChunkWithLightPacket(level.getChunkAt(player.blockPosition()), level.getLightEngine(),
                    new BitSet(), new BitSet(), false));
            Adventure.LOGGER.info("Forced update chunk");
        }
        Adventure.LOGGER.info("Debug");
        return Command.SINGLE_SUCCESS;
    }
}
