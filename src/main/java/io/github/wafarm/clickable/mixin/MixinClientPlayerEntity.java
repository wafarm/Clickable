package io.github.wafarm.clickable.mixin;

import io.github.wafarm.clickable.component.ChatTextComponent;
import io.github.wafarm.clickable.config.GlobalConfiguration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    @Shadow
    public abstract boolean sendCommand(String command);

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void beforeSendChatMessage(String message, Text preview, CallbackInfo ci) {
        if (!GlobalConfiguration.Instance.isEnabled()) return;
        try {
            ChatTextComponent component = ChatTextComponent.buildComponent(message);
            String command = String.format("tellraw @a %s", component.getJson());
            if (command.length() > 255) {
                client.inGameHud.getChatHud().addMessage(Text.literal("Failed: Generated command too long."));
                ci.cancel();
                return;
            }
            sendCommand(command);
            ci.cancel();
        } catch (IllegalArgumentException ignored) {
        }
    }
}
