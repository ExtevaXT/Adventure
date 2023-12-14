package su.external.adventure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.util.Helpers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import su.external.adventure.Adventure;
import su.external.adventure.helpers.TradeHelper;

public class TradesCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("trades").executes(TradesCommand::execute));
    }
    private static int execute(CommandContext<CommandSourceStack> command){
        TradeHelper.update();
        for (Player player : command.getSource().getLevel().players()){
            player.sendMessage(new TranslatableComponent("trades.update", TradeHelper.MESSAGE), player.getUUID());
            Helpers.playSound(player.level, player.blockPosition(), TFCSounds.ANVIL_HIT.get());
        }
        Adventure.LOGGER.info("Updated trade offers: {}", TradeHelper.MESSAGE);
        return Command.SINGLE_SUCCESS;
    }
}