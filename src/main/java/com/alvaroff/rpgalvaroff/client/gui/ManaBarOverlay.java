package com.alvaroff.rpgalvaroff.client.gui;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerClass;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, value = Dist.CLIENT)
public class ManaBarOverlay extends GuiComponent {
    private static final ResourceLocation MANA_BAR_EMPTY_TEXTURE = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/mana_empty.png");
    private static final ResourceLocation MANA_BAR_FULL_TEXTURE = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/manabar_full.png");
    static float maxMana = 0;
    static float currentMana = 0;
    static PlayerClass playerClass = PlayerClass.NONE;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (playerClass != PlayerClass.NONE) {
            //return;
        //}

            Minecraft minecraft = Minecraft.getInstance();
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int manaBarWidth = 182; // Ajusta esto según el ancho de tu textura
            int manaBarHeight = 5;  // Ajusta esto según el alto de tu textura

            int x = screenWidth - manaBarWidth - 10;
            int y = screenHeight - manaBarHeight - 10;

            int filledBarWidth = (int) ((double) currentMana / maxMana * manaBarWidth);

            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, MANA_BAR_EMPTY_TEXTURE);
            blit(event.getMatrixStack(), x, y, 0, 0, manaBarWidth, manaBarHeight, 182, 5);

            RenderSystem.setShaderTexture(0, MANA_BAR_FULL_TEXTURE);
            blit(event.getMatrixStack(), x, y, 0, 0, filledBarWidth, manaBarHeight, 182, 5);
            RenderSystem.disableBlend();

            String manaText = currentMana + "/" + maxMana;
            int textWidth = minecraft.font.width(manaText);
            int textX = x + (manaBarWidth - textWidth) / 2;
            int textY = y - 10;

            minecraft.font.draw(event.getMatrixStack(), manaText, textX, textY, 0x3CADFF);
        }
    }

    public static void updateMana(float newMaxMana, float newCurrentMana){
        maxMana = newMaxMana;
        currentMana = newCurrentMana;
    }

    public static void drawBar(PlayerClass newPlayerClass){
        playerClass = newPlayerClass;
    }
}
