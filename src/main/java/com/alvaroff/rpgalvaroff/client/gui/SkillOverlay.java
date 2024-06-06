package com.alvaroff.rpgalvaroff.client.gui;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerSkills.PlayerSkills;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerClass;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, value = Dist.CLIENT)
public class SkillOverlay extends GuiComponent {
    private static final List<ResourceLocation> SKILL_HUD_TEXTURES = new ArrayList<>();
    private static final ResourceLocation THUNDER = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/thunder_skill.png");
    private static final ResourceLocation BACKGROUND = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/skill_hud_background.png");
    private static ResourceLocation currentTexture;
    private static int currentTextureIndex = 0;
    static PlayerClass playerClass = PlayerClass.NONE;
    private static int[] skills = new int[8];

    static {
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_1.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_2.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_3.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_4.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_5.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_6.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_7.png"));
        SKILL_HUD_TEXTURES.add(new ResourceLocation(RPGalvaroff.MOD_ID, "textures/gui/skill_hud/skill_hud_slot_8.png"));

        currentTexture = SKILL_HUD_TEXTURES.get(0);
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (playerClass != PlayerClass.NONE && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {

            Minecraft minecraft = Minecraft.getInstance();
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();
            int manaBarWidth = 91;
            int manaBarHeight = 91;

            int x = 10;
            int y = screenHeight - manaBarHeight - 10;


            RenderSystem.enableBlend();

            RenderSystem.setShaderTexture(0, BACKGROUND);
            blit(event.getMatrixStack(), x, y, 0, 0, manaBarWidth, manaBarHeight, 91, 91);

            for(int i = 0; i < skills.length; i++){
                if(skills[i] >= 0){
                    PlayerSkills actionSkill = new PlayerSkills();
                    actionSkill.initSkillsVector();

                    RenderSystem.setShaderTexture(0, actionSkill.getSkill(skills[i]).getImage());
                    if(i < 3)
                        blit(event.getMatrixStack(), x + 20 + (i * 18), y + 22, 0, 0, 16, 16, 16, 16);
                    else if(i < 6)
                        blit(event.getMatrixStack(), x + (i * 18), y + 22 + 18, 0, 0, 16, 16, 16, 16);
                }
            }

            RenderSystem.setShaderTexture(0, currentTexture);
            blit(event.getMatrixStack(), x, y, 0, 0, manaBarWidth, manaBarHeight, 91, 91);

            RenderSystem.disableBlend();

        }
    }

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseInputEvent event) {
        if (event.getButton() == 2 && event.getAction() == 1) { // Button 2 is the middle mouse button, action 1 is press
            changeHUDTexture();
        }
    }

    public static int getCurrentTextureIndex(){
        return currentTextureIndex;
    }

    private static void changeHUDTexture() {
        currentTextureIndex = (currentTextureIndex + 1) % SKILL_HUD_TEXTURES.size();
        currentTexture = SKILL_HUD_TEXTURES.get(currentTextureIndex);
    }

    public static void drawHud(PlayerClass newPlayerClass){

        playerClass = newPlayerClass;

    }

    public static void updateSkills(int[] newSkills){
        skills = newSkills;
    }

    public static int[] getSkills(){
        return skills;
    }


}
