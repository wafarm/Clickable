package io.github.wafarm.clickable.mixin;

import io.github.wafarm.clickable.component.ChatTextComponent;
import io.github.wafarm.clickable.component.UrlComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Shadow
    protected abstract void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh);

    @Inject(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
            at = @At(value = "TAIL")
    )
    private void beforeAddMessage(Text message, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        String string = message.getString();
        Matcher m = UrlComponent.urlMatchPattern.matcher(string);
        List<String> urls = new ArrayList<>();
        while (m.find()) {
            urls.add(m.group());
        }
        if (urls.size() == 0) return;
        ChatTextComponent component = ChatTextComponent.buildReceivedUrlComponent(urls);
        Text text = Text.Serializer.fromJson(component.getJson());
        this.addMessage(text, null, MinecraftClient.getInstance().inGameHud.getTicks(), indicator, false);
    }
}
