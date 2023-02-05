package io.github.wafarm.clickable.mixin;

import io.github.wafarm.clickable.gui.ContextMenuScreen;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen extends Screen {

    private static final Logger LOGGER = LoggerFactory.getLogger("clickable");

    @Shadow
    ChatInputSuggestor chatInputSuggestor;

    protected MixinChatScreen(Text title) {
        super(title);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        // code from original method
        if (chatInputSuggestor.mouseClicked((int) mouseX, (int) mouseY, button)) {
            cir.setReturnValue(true);
            return;
        }

        if (button == 1) { // right button click
            if (client == null) return;
            ChatHud chatHud = client.inGameHud.getChatHud();
            double e = chatHud.toChatLineY(mouseY);
            int i = chatHud.getMessageIndex(e);
            if (i >= 0 && i < chatHud.visibleMessages.size()) {
                ChatHudLine.Visible visible = chatHud.visibleMessages.get(i);
                OrderedText text = visible.content();
                StringBuilder sb = new StringBuilder();
                text.accept(((index, style, codePoint) -> {
                    sb.append(Character.toString(codePoint));
                    return true;
                }));
                LOGGER.info("clicked line: {}", sb);
                client.setScreen(new ContextMenuScreen(mouseX, mouseY, sb.toString()));
                cir.setReturnValue(true);
            }
        }
    }
}
