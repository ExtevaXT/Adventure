package su.external.adventure.command;

import com.google.common.collect.Sets;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import su.external.adventure.config.Config;
import su.external.adventure.helpers.CoinHelper;
import xaero.pac.common.claims.player.IPlayerChunkClaim;
import xaero.pac.common.claims.player.IPlayerClaimPosList;
import xaero.pac.common.claims.player.IPlayerDimensionClaims;
import xaero.pac.common.claims.result.api.AreaClaimResult;
import xaero.pac.common.claims.result.api.ClaimResult;
import xaero.pac.common.parties.party.IPartyPlayerInfo;
import xaero.pac.common.parties.party.ally.IPartyAlly;
import xaero.pac.common.parties.party.member.IPartyMember;
import xaero.pac.common.server.IServerData;
import xaero.pac.common.server.ServerData;
import xaero.pac.common.server.claims.IServerClaimsManager;
import xaero.pac.common.server.claims.IServerDimensionClaimsManager;
import xaero.pac.common.server.claims.IServerRegionClaims;
import xaero.pac.common.server.claims.player.IServerPlayerClaimInfo;
import xaero.pac.common.server.claims.sync.ClaimsManagerSynchronizer;
import xaero.pac.common.server.parties.party.IServerParty;
import xaero.pac.common.server.player.config.IPlayerConfig;
import xaero.pac.common.server.player.localization.AdaptiveLocalizer;

public class ClaimCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("claim").executes(ClaimCommand::execute));
    }
    private static int execute(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof ServerPlayer player){
            MinecraftServer server = command.getSource().getServer();
            IServerData<IServerClaimsManager<IPlayerChunkClaim, IServerPlayerClaimInfo<IPlayerDimensionClaims<IPlayerClaimPosList>>, IServerDimensionClaimsManager<IServerRegionClaims>>, IServerParty<IPartyMember, IPartyPlayerInfo, IPartyAlly>> serverData = ServerData.from(server);
            IServerClaimsManager<IPlayerChunkClaim, IServerPlayerClaimInfo<IPlayerDimensionClaims<IPlayerClaimPosList>>, IServerDimensionClaimsManager<IServerRegionClaims>> manager = serverData.getServerClaimsManager();
            double modifier = Math.pow(Config.claim.defaultModifier.get(), manager.getPlayerInfo(player.getUUID()).getClaimCount());
            int price = (int)(Config.claim.defaultPrice.get() * modifier);
            int x = player.chunkPosition().x;
            int z = player.chunkPosition().z;

            IPlayerConfig config = ServerData.from(server).getPlayerConfigs().getLoadedConfig(player.getUUID()).getUsedSubConfig();
            AdaptiveLocalizer adaptiveLocalizer = ServerData.from(server).getAdaptiveLocalizer();
            // First check then claim
            if(CoinHelper.countCoins(player) >= price){
                ClaimResult<?> result = manager.tryToClaim(player.getLevel().dimension().location(), player.getUUID(), config.getSubIndex(), x, z, x, z, false);
                if(result.getResultType().success){
                    CoinHelper.takeCoins(player, price);
                    player.sendMessage(adaptiveLocalizer.getFor(player, "gui.xaero_claims_claimed_at", x, z), player.getUUID());
                }
                else player.sendMessage(new TranslatableComponent("command.claim.fail"), Util.NIL_UUID);
                ((ClaimsManagerSynchronizer)manager.getClaimsManagerSynchronizer()).syncToPlayerClaimActionResult(
                        new AreaClaimResult(Sets.newHashSet(result.getResultType()), x, z, x, z),
                        player);
            }
            else player.sendMessage(new TranslatableComponent("command.claim.price", price), Util.NIL_UUID);
        }
        return Command.SINGLE_SUCCESS;
    }
}
