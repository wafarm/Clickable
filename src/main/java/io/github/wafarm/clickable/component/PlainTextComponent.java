package io.github.wafarm.clickable.component;

import com.google.gson.annotations.SerializedName;

public class PlainTextComponent implements Component {
    @SerializedName("text")
    private final String text;

    public PlainTextComponent(String text) {
        this.text = text;
    }

}
