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

        ArrayList<Integer> totalSkills = playerStats.getTotalSkills();
        PlayerSkills skills = new PlayerSkills();
        skills.initSkillsVector();

        int screenWidth = this.width; // Ancho de la pantalla
        int imageSize = 32; // Tamaño de la imagen de la habilidad
        int gap = 25; // Espacio entre las imágenes

        int x = gap; // Posición X inicial
        int y = gap; // Posición Y inicial
        int rowHeight = imageSize + 10 + (int)(Minecraft.getInstance().font.lineHeight * 0.75); // Altura de la fila, incluyendo el texto

        for(int i = 0; i < totalSkills.size(); i++) {
            // Si la siguiente imagen se va a salir del ancho de la pantalla, mover a la siguiente fila
            if (x + imageSize > screenWidth) {
                x = gap; // Reiniciar posición X
                y += rowHeight; // Mover hacia abajo
            }

            // Renderizar la imagen de la habilidad
            RenderSystem.setShaderTexture(0, skills.getSkill(totalSkills.get(i)).getImage());
            blit(poseStack, x, y, 0, 0, imageSize, imageSize, imageSize, imageSize);

            // Dibujar el nombre de la habilidad justo debajo de la imagen y centrado
            String skillName = skills.getSkill(totalSkills.get(i)).getName();
            float textScale = 0.75f;
            int textWidth = (int)(Minecraft.getInstance().font.width(skillName) * textScale);
            int textX = x + (imageSize / 2) - (textWidth / 2); // Centrar el texto con respecto a la imagen
            drawScaledText(poseStack, skillName, textX, y + imageSize + 2, textScale, 0xFFFFFF);

            x += imageSize + gap; // Mover la posición X para la siguiente imagen
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
