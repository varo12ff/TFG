package com.alvaroff.rpgalvaroff.common.items.custom.keys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

import static com.alvaroff.rpgalvaroff.common.blocks.BlockInit.LOCK;
import static com.alvaroff.rpgalvaroff.common.blocks.BlockInit.UNLOCK_NEW_ROOM_BLOCK;
import static com.alvaroff.rpgalvaroff.common.utils.DimensionUtils.*;

public class Key_D extends Item {
    public Key_D(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        Direction faceClicked = context.getClickedFace();
        ItemStack itemStack = context.getItemInHand();

        if(!world.isClientSide && player != null){
            BlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() == UNLOCK_NEW_ROOM_BLOCK.get()){
                Random random = new Random();

                if(random.nextFloat() < 0.33f)
                    generateProceduralLavaRoom(world, pos, random, faceClicked);
                else if(random.nextFloat() >= 0.33f && random.nextFloat() < 0.66f)
                    generateProceduralRoom(world, pos, random, faceClicked);
                else
                    generateProceduralWaterRoom(world, pos, random, faceClicked);

                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
