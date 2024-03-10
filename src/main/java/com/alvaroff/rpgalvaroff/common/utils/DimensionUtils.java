package com.alvaroff.rpgalvaroff.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class DimensionUtils {
    public static void teleportToDimension(ServerPlayer player, ResourceKey<Level> dimension, BlockPos dest){
        ServerLevel destlevel = player.server.getLevel(dimension);

        if(destlevel != null){
            player.teleportTo(destlevel, 0, 2, 0,
                    player.yRotO, player.xRotO);
        }
    }

    public static void clearBlocksInCubeCentered(Level world, int centerX, int centerY, int centerZ, int halfSize) {
        int startX = centerX - halfSize;
        int startY = centerY - halfSize;
        int startZ = centerZ - halfSize;
        int endX = centerX + halfSize;
        int endY = centerY + halfSize;
        int endZ = centerZ + halfSize;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    world.setBlockAndUpdate(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    public static void generateFlat(Level world, int centerX, int centerY, int centerZ) {
        int halfSize = 2; // Ajusta el tamaño del cuadrado según sea necesario
        int startX = centerX - halfSize;
        int startY = centerY;
        int startZ = centerZ - halfSize;

        for (int i = startX; i < startX + 5; i++) {
            for (int j = startZ; j < startZ + 5; j++) {
                world.setBlockAndUpdate(new BlockPos(i, startY, j), Blocks.STONE.defaultBlockState());
            }
        }
    }
}
