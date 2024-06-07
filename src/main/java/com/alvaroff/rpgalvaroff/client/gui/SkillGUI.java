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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;

public class SkillGUI extends Screen {
    PlayerStats playerStats;
    private boolean isMouseClicked = false;
    private final Button[] buttons = new Button[8];
    private int selectedButtonIndex = -1; // Índice del botón seleccionado
    private long[] buttonClickTimes = new long[8];

    protected SkillGUI(Component component) {
        super(component);
    }

    public SkillGUI(TextComponent component, PlayerStats playerStats) {
        super(component);
        this.playerStats = playerStats;

    }


    public void openScreen() {
        if(!playerStats.getPlayerClass().equals(PlayerClass.NONE))
            Minecraft.getInstance().setScreen(this);
    }

    @Override
    protected void init() {
        super.init();

        // Crear botones numerados del 1 al 8 en la parte inferior de la pantalla
        int buttonWidth = 60;
        int buttonHeight = 20;
        int gap = 20;
        int startX = (this.width - (buttonWidth * 8 + gap * 7)) / 2;
        int startY = this.height - 30;
        int[] skillAction = playerStats.getActionSkills();
        PlayerSkills skills = new PlayerSkills();
        skills.initSkillsVector();

        for (int i = 0; i < 8; i++) {
            final int index = i;

            if(skillAction[i] == -1)
                buttons[i] = new Button(startX + (buttonWidth + gap) * i, startY, buttonWidth, buttonHeight, new TextComponent("slot " + (i + 1)), button -> {
                    handleButtonClick(index);
                });
            else{
                buttons[i] = new Button(startX + (buttonWidth + gap) * i, startY, buttonWidth, buttonHeight, new TextComponent(skills.getSkill(skillAction[i]).getName()), button -> {
                    handleButtonClick(index);
                });
            }
            this.addRenderableWidget(buttons[i]);
        }
    }

    private void handleButtonClick(int index) {
        long currentTime = System.currentTimeMillis();
        int[] skillAction = playerStats.getActionSkills();

        // Manejar doble clic
        if (currentTime - buttonClickTimes[index] < 250) {
            // Restaurar el nombre original del botón
            buttons[index].setMessage(new TextComponent("slot " + (index + 1)));
            skillAction[index] = -1;
            playerStats.setActionSkills(skillAction);
        } else {
            // Manejar clic simple
            selectedButtonIndex = index; // Al hacer clic en un botón, se selecciona

        }

        buttonClickTimes[index] = currentTime;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        int[] skillAction = playerStats.getActionSkills();
        ArrayList<Integer> totalSkills = playerStats.getTotalSkills();
        PlayerSkills skills = new PlayerSkills();
        skills.initSkillsVector();

        int screenWidth = this.width; // Ancho de la pantalla
        int imageSize = 32; // Tamaño de la imagen de la habilidad
        int gap = 10; // Espacio entre las imágenes

        int x = gap; // Posición X inicial
        int y = gap; // Posición Y inicial
        int rowHeight = imageSize + 10 + (int)(Minecraft.getInstance().font.lineHeight * 0.75); // Altura de la fila, incluyendo el texto

        for (int i = 0; i < totalSkills.size(); i++) {
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
            int textWidth = (int) (Minecraft.getInstance().font.width(skillName) * textScale);
            int textX = x + (imageSize / 2) - (textWidth / 2); // Centrar el texto con respecto a la imagen
            drawScaledText(poseStack, skillName, textX, y + imageSize + 2, textScale, 0xFFFFFF);

            // Verificar si se hizo clic en la imagen de la habilidad
            if (isMouseOver(mouseX, mouseY, x, y, imageSize, imageSize) && this.isMouseClicked) {
                if (selectedButtonIndex != -1) {
                    // Verificar si la habilidad ya está en uno de los botones
                    boolean alreadyExists = false;
                    for (Button button : buttons) {
                        if (button.getMessage().getString().equals(skillName)) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) {
                        // Cambiar el nombre del botón previamente seleccionado al nombre de la habilidad
                        buttons[selectedButtonIndex].setMessage(new TextComponent(skillName));
                        skillAction[selectedButtonIndex] = skills.getSkill(totalSkills.get(i)).getId();

                        playerStats.setActionSkills(skillAction);

                        // Renombrar el botón previamente seleccionado a su nombre original
                        for (int j = 0; j < buttons.length; j++) {
                            if (j != selectedButtonIndex && buttons[j].getMessage().getString().equals(skillName)) {
                                buttons[j].setMessage(new TextComponent("slot " + (j + 1)));
                                break;
                            }
                        }
                    } else {
                        // Encontrar y renombrar el botón que tiene la habilidad a su nombre original
                        for (int j = 0; j < buttons.length; j++) {
                            if (buttons[j].getMessage().getString().equals(skillName)) {
                                buttons[j].setMessage(new TextComponent("slot " + (j + 1)));
                                buttons[selectedButtonIndex].setMessage(new TextComponent(skillName));
                                skillAction[j] = -1;
                                skillAction[selectedButtonIndex] = skills.getSkill(totalSkills.get(i)).getId();

                                playerStats.setActionSkills(skillAction);
                                break;
                            }
                        }
                    }
                    selectedButtonIndex = -1; // Deseleccionar el botón después de actualizar el texto
                }
            }

            x += imageSize + gap; // Mover la posición X para la siguiente imagen
        }

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void drawScaledText(PoseStack poseStack, String text, int x, int y, float scale, int color) {
        poseStack.pushPose();
        poseStack.translate(x, y, 0);
        poseStack.scale(scale, scale, scale);
        Minecraft.getInstance().font.draw(poseStack, text, 0, 0, color);
        poseStack.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.isMouseClicked = true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.isMouseClicked = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {

        super.onClose();
        ModMessages.sendToServer(new PlayerStatsC2SPacket(playerStats.getNBT()));

        SkillOverlay.updateSkills(playerStats.getActionSkills());
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
