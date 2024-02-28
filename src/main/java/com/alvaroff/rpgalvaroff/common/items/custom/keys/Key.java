package com.alvaroff.rpgalvaroff.common.items.custom.keys;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Key extends Item {
    public Key(Properties p) {
        super(p);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        if(world.isClientSide()){
            ItemStack heldItem = player.getItemInHand(hand);

            if(heldItem.getItem() == this){
                player.sendMessage(Component.nullToEmpty("La llave para abrir la dimensión"), player.getUUID());
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

}
