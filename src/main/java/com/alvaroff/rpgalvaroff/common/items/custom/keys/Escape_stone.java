package com.alvaroff.rpgalvaroff.common.items.custom.keys;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.utils.DimensionUtils;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit.RPGDIM_KEY;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Escape_stone extends Item {
    private boolean shiftPressed = false;
    public Escape_stone(Properties p) {

        super(p);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        if(!world.isClientSide()){
            if(world.dimension().equals(DimensionInit.RPGDIM_KEY)) {
                //player.sendMessage(Component.nullToEmpty("estas en la dimension"), player.getUUID());
                BlockPos playerPos = player.blockPosition();
                DimensionUtils.teleportToDimension((ServerPlayer) player, Level.OVERWORLD, playerPos);
            }
            else {
                player.displayClientMessage(new TranslatableComponent("msg.notuse"), true);
                //player.sendMessage(Component.nullToEmpty("No puedes usar esto aquí."), player.getUUID());
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        // Verificar si el jugador está presionando la tecla Mayúsculas
        boolean isShiftKeyDown = GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS
                || GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

        if (stack.getItem() == this) {
            if (isShiftKeyDown) {
                // Agregar la descripción detallada solo cuando se mantiene presionada la tecla Mayúsculas
                String translationKey = this.getDescriptionId(stack) + ".info";
                tooltip.add(new TranslatableComponent(translationKey).withStyle(ChatFormatting.BLUE));

            }
            else{
                String translationKey = this.getDescriptionId(stack) + ".noInfo";
                tooltip.add(new TranslatableComponent(translationKey).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

    /*@Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level world, List<Component> tooltip, TooltipFlag flag) {

        // Actualizar el estado de la tecla Shift
        shiftPressed = Minecraft.getInstance().options.keyShift.isDown();

        // Limpiar descripciones anteriores si la tecla Shift está presionada
        tooltip.removeIf(component -> component.getString().startsWith(this.getName(stack).getString()));

        // Añadir el nombre del objeto como la primera línea del tooltip, en verde
        tooltip.add(this.getName(stack));

        if (shiftPressed) {
            // Agregar la descripción detallada solo cuando se mantiene presionada la tecla Mayúsculas
            String translationKey = "Pulsa Mayus para más info";
            tooltip.add(new TranslatableComponent(translationKey).withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, world, tooltip, flag);
    }*/

}
