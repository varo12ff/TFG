package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerSkills.PlayerSkills;
import com.alvaroff.rpgalvaroff.client.KeyBinding;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.KeyBindingC2SPacket;
import com.alvaroff.rpgalvaroff.networking.packet.LaunchSkillC2SPacket;
import com.alvaroff.rpgalvaroff.networking.packet.SummonLightningC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if(KeyBinding.OPEN_INITIAL_GUI.isDown()) {
            ModMessages.sendToServer(new KeyBindingC2SPacket());

        }
    }

    private static boolean wasSkillActivated = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END) {
            Player player = mc.player;
            if (player != null && mc.screen == null) {
                long windowHandle = mc.getWindow().getWindow();
                boolean isAltPressed = GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
                boolean isLeftClickPressed = GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

                if (isAltPressed && isLeftClickPressed) {
                    if (!wasSkillActivated) {
                        PlayerSkills actionSkill = new PlayerSkills();
                        actionSkill.initSkillsVector();

                        int[] skill = SkillOverlay.getSkills();
                        if(skill[SkillOverlay.getCurrentTextureIndex()] >= 0)
                            ModMessages.sendToServer(new LaunchSkillC2SPacket(skill[SkillOverlay.getCurrentTextureIndex()]));
                        else
                            player.displayClientMessage(new TextComponent("No Skill"), true);

                        wasSkillActivated = true; // Marcar como activado
                    }
                } else {
                    wasSkillActivated = false; // Restablecer el estado cuando las teclas se suelten
                }
            }
        }
    }


}
