package com.alvaroff.rpgalvaroff.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class RpgGUI extends Screen {

    protected RpgGUI(Component component) {
        super(component);
    }

    public RpgGUI(TextComponent component) {
        super(component);
    }

    @Override
    protected void init() {
        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 - 10, 100, 20, new TextComponent("Hola"), button -> {
            Minecraft.getInstance().player.sendMessage(new TextComponent("Has subido de nivel"), Util.NIL_UUID);
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        this.drawCenteredString(poseStack, this.font, this.title.getString(), this.width / 2, 20, 0xFFFFFF);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }
}
