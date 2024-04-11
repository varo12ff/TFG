package com.alvaroff.rpgalvaroff.common.utils;

import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Random;

import static com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit.RPGDIM_KEY;

public class DimensionUtils {

    public static void handleDimensionEntry(ServerPlayer player) {

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = playerData.getCompound("PlayerData");
        data.putLong("LastOverworldPosition", player.getOnPos().asLong());
        playerData.put("PlayerData", data);
        player.teleportTo(player.server.getLevel(RPGDIM_KEY), 0, 2, 0, player.yRotO, player.xRotO);
    }

    public static void handleDimensionExit(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();
        if (playerData.contains("PlayerData", 10)) {
            CompoundTag data = playerData.getCompound("PlayerData");
            if (data.contains("LastOverworldPosition")) {
                long posLong = data.getLong("LastOverworldPosition");
                BlockPos pos = BlockPos.of(posLong);
                player.teleportTo(player.server.getLevel(Level.OVERWORLD), pos.getX(), pos.getY() + 1, pos.getZ(), player.yRotO, player.xRotO);
            }
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

    public static void generateStartRoom(Level world, int centerX, int centerY, int centerZ, int halfSize) {
        BlockState stone = Blocks.STONE.defaultBlockState();
        BlockState glowstone = Blocks.GLOWSTONE.defaultBlockState();
        BlockState obsidian = Blocks.OBSIDIAN.defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        Random random = new Random();

        int startX = centerX - halfSize;
        int startY = centerY - halfSize;
        int startZ = centerZ - halfSize;

        int obsidianFace = random.nextInt(4);

        int centerFaceX = centerX + (obsidianFace == 1 ? halfSize : obsidianFace == 3 ? -halfSize : 0);
        int centerFaceZ = centerZ + (obsidianFace == 0 ? -halfSize : obsidianFace == 2 ? halfSize : 0);

        for (int x = 0; x <= 2 * halfSize; x++) {
            for (int y = 0; y <= 2 * halfSize; y++) {
                for (int z = 0; z <= 2 * halfSize; z++) {
                    BlockPos pos = new BlockPos(startX + x, startY + y, startZ + z);

                    if (y == 0 && (x == z || x + z == 2 * halfSize)) {
                        world.setBlock(pos, glowstone, 3);
                    } else if (x == 0 || x == 2 * halfSize || y == 0 || y == 2 * halfSize || z == 0 || z == 2 * halfSize) {
                        if (y == 2 && (x + startX == centerFaceX && z + startZ == centerFaceZ)) {
                            world.setBlock(pos, obsidian, 3);
                        } else {
                            world.setBlock(pos, stone, 3);
                        }
                    } else {
                        world.setBlock(pos, air, 3);
                    }
                }
            }
        }
    }
}
