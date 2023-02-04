package io.github.wafarm.clickable.component;

import com.google.gson.annotations.SerializedName;

public class ClickEventComponent implements Component {
    @SerializedName("action")
    private final String action;

    @SerializedName("value")
    private final String value;

    public ClickEventComponent(String action, String value) {
        this.action = action;
        this.value = value;
    }
}
