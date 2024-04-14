package com.alvaroff.rpgalvaroff.client.gui;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerClass;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.PlayerStatsC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class RpgGUI extends Screen {

    PlayerStats playerStats;
    private static final ResourceLocation MAGICIAN_IMAGE_LOCATION = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/magician_gui.png");
    private static final ResourceLocation WARRIOR_IMAGE_LOCATION = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/spartan_gui.png");
    private static final ResourceLocation NINJA_IMAGE_LOCATION = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/ninja_gui.png");
    private static final ResourceLocation PRIEST_IMAGE_LOCATION = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/priest_gui.png");

    protected RpgGUI(Component component) {
        super(component);
    }

    public RpgGUI(TextComponent component, PlayerStats playerStats) {
        super(component);
        this.playerStats = playerStats;

    }

    @Override
    protected void init() {


    }

    private void selectClass(PlayerStats newPlayerStats){
        ModMessages.sendToServer(new PlayerStatsC2SPacket(newPlayerStats.getNBT()));
        this.onClose();
    }

    private void renderChooseClass(PoseStack poseStack){
        int totalButtons = 4;
        int buttonWidth = 100;
        int marginX = 20;
        int buttonHeight = 20;
        int yPos = (this.height / 2 + 80) - (buttonHeight / 2);

        int totalWidthAvailable = this.width - (2 * marginX) - (totalButtons * buttonWidth);

        int spaceBetweenButtons = totalWidthAvailable / (totalButtons - 1);

        int buttonCenterX = marginX + buttonWidth / 2;
        int imageX = buttonCenterX - 70 / 2;
        int imageY = yPos - 140 - 20;

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, WARRIOR_IMAGE_LOCATION);
        blit(poseStack, imageX, imageY, 0, 0, 70, 140, 70, 140);

        this.addRenderableWidget(new Button(marginX, yPos, buttonWidth, buttonHeight, new TranslatableComponent("gui.warriorClass"), button -> {
            PlayerStats newPlayerStats = new PlayerStats(1, 14, 5, 3, 3, 3, 1, 1, 3, PlayerClass.GUERRERO);
            selectClass(newPlayerStats);
        }));

        buttonCenterX = marginX + buttonWidth + spaceBetweenButtons + buttonWidth / 2;
        imageX = buttonCenterX - 70 / 2;

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, MAGICIAN_IMAGE_LOCATION);
        blit(poseStack, imageX, imageY, 0, 0, 70, 140, 70, 140);

        this.addRenderableWidget(new Button(marginX + buttonWidth + spaceBetweenButtons, yPos, buttonWidth, buttonHeight, new TranslatableComponent("gui.magicianClass"), button -> {
            PlayerStats newPlayerStats = new PlayerStats(1, 6, 1, 4, 3, 3, 5, 5, 3, PlayerClass.MAGO);
            selectClass(newPlayerStats);
        }));

        buttonCenterX = marginX + buttonWidth * 2 + spaceBetweenButtons * 2 + buttonWidth / 2;
        imageX = buttonCenterX - 70 / 2;

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, NINJA_IMAGE_LOCATION);
        blit(poseStack, imageX, imageY, 0, 0, 70, 140, 70, 140);

        this.addRenderableWidget(new Button(marginX + 2 * (buttonWidth + spaceBetweenButtons), yPos, buttonWidth, buttonHeight, new TranslatableComponent("gui.ninjaClass"), button -> {
            PlayerStats newPlayerStats = new PlayerStats(1, 10, 3, 5,3, 3, 2, 5, 1, PlayerClass.NINJA);
            selectClass(newPlayerStats);
        }));

        buttonCenterX = marginX + buttonWidth * 3 + spaceBetweenButtons * 3 + buttonWidth / 2;
        imageX = buttonCenterX - 70 / 2;

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, PRIEST_IMAGE_LOCATION);
        blit(poseStack, imageX, imageY, 0, 0, 70, 140, 70, 140);

        this.addRenderableWidget(new Button(marginX + 3 * (buttonWidth + spaceBetweenButtons), yPos, buttonWidth, buttonHeight, new TranslatableComponent("gui.priestClass"), button -> {
            PlayerStats newPlayerStats = new PlayerStats(1, 12, 1, 3,3, 3, 4, 1, 5, PlayerClass.CLERIGO);
            selectClass(newPlayerStats);
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        this.drawCenteredString(poseStack, this.font, this.title.getString(), this.width / 2, 20, 0xFFFFFF);
        
        if (playerStats.getPlayerClass() == PlayerClass.NONE) {
            renderChooseClass(poseStack);

        } else {
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.class").getString() + ": " + playerStats.getPlayerClass().toString(), this.width / 2, 50, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.health").getString() + ": " + playerStats.getHealth(), this.width / 2, 70, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.strenght").getString() + ": " + playerStats.getStrength(), this.width / 2, 90, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.ability").getString() + ": " + playerStats.getAbility(), this.width / 2, 110, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.resistance").getString() + ": " + playerStats.getResistance(), this.width / 2, 130, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.mana").getString() + ": " + playerStats.getMana(), this.width / 2, 150, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.magicPower").getString() + ": " + playerStats.getMagicPower(), this.width / 2, 170, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.agility").getString() + ": " + playerStats.getAgility(), this.width / 2, 190, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.sanation").getString() + ": " + playerStats.getSanation(), this.width / 2, 210, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, "Lvl: " + playerStats.getLevel(), this.width / 2, 230, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, "AP: " + playerStats.getAbilityPoints(), this.width / 2, 250, 0xFFFFFF);
            this.drawCenteredString(poseStack, this.font, "XP: " + playerStats.getXp() + "/" + playerStats.getNextLvl(), this.width - 50, this.height - 20, 0xFFFFFF);
        }
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
