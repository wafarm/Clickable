package io.github.wafarm.clickable.config;

public class GlobalConfiguration {
    public static final GlobalConfiguration Instance = new GlobalConfiguration();

    private boolean enabled;

    public GlobalConfiguration() {
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
