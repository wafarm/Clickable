package io.github.wafarm.clickable.component;

import com.google.gson.annotations.SerializedName;

public class CommandComponent implements Component {
    @SerializedName("text")
    private final String text;

    @SerializedName("clickEvent")
    private final ClickEventComponent clickEvent;

    @SerializedName("underlined")
    private final boolean underlined = true;

    @SerializedName("color")
    private final String color = "blue";

    public CommandComponent(String command) {
        clickEvent = new ClickEventComponent("suggest_command", command);
        text = command;
    }

    public static boolean isCommand(String text) {
        return text.startsWith("/");
    }
}
