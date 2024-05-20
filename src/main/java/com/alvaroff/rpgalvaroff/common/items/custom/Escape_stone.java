package com.alvaroff.rpgalvaroff.common.items.custom;

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
    public Escape_stone(Properties p) {

        super(p);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack itemStack = player.getItemInHand(hand);

        if(!world.isClientSide()){
            if(world.dimension().equals(DimensionInit.RPGDIM_KEY)) {
                DimensionUtils.handleDimensionExit((ServerPlayer) player);
                itemStack.shrink(1);
            }
            else {
                player.displayClientMessage(new TranslatableComponent("msg.notuse"), true);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        boolean isShiftKeyDown = GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS
                || GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

        if (stack.getItem() == this) {
            if (isShiftKeyDown) {
                String translationKey = this.getDescriptionId(stack) + ".info";
                tooltip.add(new TranslatableComponent(translationKey).withStyle(ChatFormatting.BLUE));

            }
            else{
                String translationKey = "item.noInfo";
                tooltip.add(new TranslatableComponent(translationKey).withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

}
