package io.github.wafarm.clickable.component;

import com.google.gson.Gson;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatTextComponent implements Component {
    private static final Pattern pattern = Pattern.compile("\\{(.*?)}");
    private final List<Component> components = new ArrayList<>();

    public static ChatTextComponent buildComponent(String text) throws IllegalArgumentException {
        ChatTextComponent chatTextComponent = new ChatTextComponent();
        Matcher m = pattern.matcher(text);

        String[] split = text.split(pattern.pattern(), -1);
        int i = 0;

        if (split.length == 1) throw new IllegalArgumentException();

        // add non {} text
        chatTextComponent.components.add(new PlainTextComponent(split[i++]));
        while (m.find()) {
            String fullComponent = m.group();
            String component = fullComponent.substring(1, fullComponent.length() - 1);

            if (component.isBlank()) throw new IllegalArgumentException();
            if (UrlComponent.isUrl(component)) {
                chatTextComponent.components.add(new UrlComponent(component));
            } else if (CommandComponent.isCommand(component)) {
                chatTextComponent.components.add(new CommandComponent(component));
            } else {
                throw new IllegalArgumentException();
            }

            // add non {} text
            chatTextComponent.components.add(new PlainTextComponent(split[i++]));
        }

        // add chat prefix
        if (MinecraftClient.getInstance().player == null) throw new IllegalArgumentException();
        String playerName = MinecraftClient.getInstance().player.getGameProfile().getName();
        chatTextComponent.components.add(0, new PlainTextComponent(String.format("<%s> ", playerName)));

        return chatTextComponent;
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(components);
    }
}
