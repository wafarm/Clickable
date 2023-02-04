package io.github.wafarm.clickable.component;

import com.google.gson.annotations.SerializedName;

public class UrlComponent implements Component {
    public static final String urlTestRegex = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)";

    @SerializedName("clickEvent")
    private final ClickEventComponent clickEvent;

    @SerializedName("text")
    private final String text;

    @SerializedName("underlined")
    private final boolean underlined = true;

    @SerializedName("color")
    private final String color = "blue";

    public UrlComponent(String url) {
        clickEvent = new ClickEventComponent("open_url", url);
        text = url;
    }

    public static boolean isUrl(String text) {
        return text.matches(urlTestRegex);
    }
}
