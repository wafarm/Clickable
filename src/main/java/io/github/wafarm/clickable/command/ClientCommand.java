package io.github.wafarm.clickable.command;

import io.github.wafarm.clickable.config.GlobalConfiguration;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;


public class ClientCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                literal("clickable")
                        .then(literal("toggle")
                                .executes(context -> {
                                    GlobalConfiguration.Instance.setEnabled(!GlobalConfiguration.Instance.isEnabled());
                                    if (GlobalConfiguration.Instance.isEnabled()) {
                                        context.getSource().sendFeedback(Text.literal("Clickable is enabled"));
                                    } else {
                                        context.getSource().sendFeedback(Text.literal("Clickable is disabled"));
                                    }
                                    return 0;
                                })
                        )
        ));
    }
}
