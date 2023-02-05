package io.github.wafarm.clickable.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ContextMenuScreen extends Screen {

    private final int mouseX;
    private final int mouseY;
    private final String text;

    public ContextMenuScreen(double mouseX, double mouseY, String text) {
        super(Text.literal("Copy to clipboard"));
        this.mouseX = (int) mouseX;
        this.mouseY = (int) mouseY;
        this.text = text;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void init() {
        this.addDrawableChild(new ButtonWidget(mouseX, mouseY, 60, 20, Text.literal("Copy line"), button -> {
            if (this.client != null) {
                this.client.keyboard.setClipboard(text);
                this.client.setScreen(null);
            }
        }));
    }
}
