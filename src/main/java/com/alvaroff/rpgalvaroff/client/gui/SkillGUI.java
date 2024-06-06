package com.alvaroff.rpgalvaroff.client.gui;

import com.alvaroff.rpgalvaroff.capabilities.playerSkills.PlayerSkills;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerClass;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.PlayerStatsC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;

public class SkillGUI extends Screen {
    PlayerStats playerStats;

    protected SkillGUI(Component component) {
        super(component);
    }

    public SkillGUI(TextComponent component, PlayerStats playerStats) {
        super(component);
        this.playerStats = playerStats;

    }

    @Override
    protected void init() {

    }

    public void openScreen() {
        Minecraft.getInstance().setScreen(this);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        // Calcula el ancho y la separación de las imágenes
        ArrayList<Integer> totalSkills = playerStats.getTotalSkills();
        PlayerSkills skills = new PlayerSkills();
        skills.initSkillsVector();
        for(int i = 0; i < totalSkills.size(); i++) {
            RenderSystem.setShaderTexture(0, skills.getSkill(totalSkills.get(i)).getImage());
            blit(poseStack, 0 + (32 * i) + (i * 10), 0, 0, 0, 32, 32, 32, 32);
            drawScaledText(poseStack, skills.getSkill(totalSkills.get(i)).getName(), 0 + (32 * i) + (i * 10), 42, 0.5f,0xFFFFFF);
        }
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void drawScaledText(PoseStack poseStack, String text, int x, int y, float scale, int color) {
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, scale);
        Minecraft.getInstance().font.draw(poseStack, text, 0, 0, color);
        poseStack.popPose();
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
