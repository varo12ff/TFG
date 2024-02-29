package com.alvaroff.rpgalvaroff.common.items.custom.keys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.alvaroff.rpgalvaroff.common.blocks.BlockInit.LOCK;
import static com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit.RPGDIM_KEY;

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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if(!world.isClientSide && player != null){
            BlockState blockState = world.getBlockState(pos);

            if (blockState.getBlock() == LOCK.get()){
                BlockPos playerPos = player.blockPosition();
                teleportToDimension((ServerPlayer) player, RPGDIM_KEY, playerPos);
            }
        }

        return InteractionResult.PASS;
    }

    private void teleportToDimension(ServerPlayer player, ResourceKey<Level> dimension, BlockPos dest){
        ServerLevel destlevel = player.server.getLevel(dimension);

        if(destlevel != null){
            player.teleportTo(destlevel, dest.getX() + 0.5, -63.0, dest.getZ() + 0.5,
                    player.yRotO, player.xRotO);
        }
    }

}
