package io.github.wafarm.clickable.component;

import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

public class UrlComponent implements Component {
    public static final String urlTestRegex = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)";

    public static final Pattern urlMatchPattern = Pattern.compile(urlTestRegex);

    @SerializedName("clickEvent")
    private final ClickEventComponent clickEvent;
    @SerializedName("underlined")
    private final boolean underlined = true;
    @SerializedName("color")
    private final String color = "blue";
    @SerializedName("text")
    private String text;

    public UrlComponent(String url) {
        clickEvent = new ClickEventComponent("open_url", url);
        text = url;
    }

    public static boolean isUrl(String text) {
        return text.matches(urlTestRegex);
    }

    public void setText(String text) {
        this.text = text;
    }
}
