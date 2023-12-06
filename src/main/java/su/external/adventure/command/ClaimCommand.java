package su.external.adventure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.ftb.mods.ftbchunks.data.FTBChunksAPI;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import su.external.adventure.util.CoinHelper;

public class ClaimCommand {
    // No configs?
    public static final int DEFAULT_PRICE = 10000;
    public static final double DEFAULT_MODIFIER = 1.5D;
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("claim").executes(ClaimCommand::execute));
    }
    private static int execute(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof ServerPlayer player){
            double modifier = Math.pow(DEFAULT_MODIFIER, FTBChunksAPI.getManager().getData(player).getClaimedChunks().size());
            int price = (int)(DEFAULT_PRICE * modifier);
            // First check then claim
            if(CoinHelper.countCoins(player) >= price && FTBChunksAPI.claimAsPlayer(player, player.getLevel().dimension(), player.chunkPosition(), false).isSuccess())
                CoinHelper.takeCoins(player, price);
            else player.sendMessage(new TranslatableComponent("command.claim.fail", price), Util.NIL_UUID);
        }
        return Command.SINGLE_SUCCESS;
    }
}
