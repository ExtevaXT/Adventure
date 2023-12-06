package su.external.adventure.mixin;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.client.ClientHelpers;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import su.external.adventure.util.TradeHelper;

@Mixin(ClientHelpers.class)
public class MixinClientHelpers {
    @Inject(method = "hasShiftDown", at = @At("HEAD"), cancellable = true, remap = false)
    private static void inject$hasShiftDown(CallbackInfoReturnable<Boolean> callbackInfo) {
        TerraFirmaCraft.LOGGER.warn("Mixin injected");
        callbackInfo.setReturnValue(Screen.hasShiftDown() || TradeHelper.shiftDown);
    }
}
