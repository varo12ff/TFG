package com.alvaroff.rpgalvaroff.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class DimensionUtils {
    public static void teleportToDimension(ServerPlayer player, ResourceKey<Level> dimension, BlockPos dest){
        ServerLevel destlevel = player.server.getLevel(dimension);

        if(destlevel != null){
            player.teleportTo(destlevel, dest.getX() + 0.5, -63.0, dest.getZ() + 0.5,
                    player.yRotO, player.xRotO);
        }
    }
}
