package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.SummonLightningC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)

public class ThunderSkill extends PlayerSkills{


    private final int id = 0;
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/thunder_skill.png");

    public ThunderSkill() {
        super();
    }
    public int getId() {
        return id;
    }

    @Override
    public ResourceLocation getImage(){
        return icon;
    }


    @Override
    public void launch(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END) {
            Player player = mc.player;
            if (player != null && mc.screen == null) {
                long windowHandle = mc.getWindow().getWindow();
                boolean isAltPressed = GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
                boolean isLeftClickPressed = GLFW.glfwGetMouseButton(windowHandle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

                if (isAltPressed && isLeftClickPressed) {

                    HitResult result = rayTrace(player, 5);

                    if (result.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockResult = (BlockHitResult) result;
                        ModMessages.sendToServer(new SummonLightningC2SPacket(blockResult.getBlockPos().getX(), blockResult.getBlockPos().getY(), blockResult.getBlockPos().getZ()));
                    }
                }
            }
        }
    }

    private static HitResult rayTrace(Player player, double range) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F).scale(range);
        Vec3 targetPosition = eyePosition.add(lookVec);
        return player.level.clip(new net.minecraft.world.level.ClipContext(eyePosition, targetPosition, net.minecraft.world.level.ClipContext.Block.OUTLINE, net.minecraft.world.level.ClipContext.Fluid.NONE, player));
    }
    @Override
    public void mostrar(){
        System.out.println("soy thunder");
    }

}
