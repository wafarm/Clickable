package io.github.wafarm.clickable.mixin;

import io.github.wafarm.clickable.gui.ContextMenuScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(method = "method_1611([ZLnet/minecraft/client/gui/screen/Screen;DDI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;mouseClicked(DDI)Z", shift = At.Shift.AFTER))
    private static void afterMouseClick(boolean[] bls, Screen screen, double d, double e, int i, CallbackInfo ci) {
        // Close context menu on empty click
        if (bls[0]) return;
        if (MinecraftClient.getInstance() == null) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof ContextMenuScreen contextMenuScreen) {
            if ((int) d == contextMenuScreen.getMouseX() && (int) e == contextMenuScreen.getMouseY()) return;
            client.setScreen(null);
        }
    }
}
