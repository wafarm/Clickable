package io.github.wafarm.clickable;

import io.github.wafarm.clickable.command.ClientCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class ClickableClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("clickable");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Clickable loading.");
        ClientCommand.register();
    }
}
