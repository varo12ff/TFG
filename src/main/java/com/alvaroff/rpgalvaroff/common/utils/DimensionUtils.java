package com.alvaroff.rpgalvaroff.common.utils;

import com.alvaroff.rpgalvaroff.common.blocks.BlockInit;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

        BlockState glowstone = Blocks.GLOWSTONE.defaultBlockState();
        BlockState lock = BlockInit.UNLOCK_NEW_ROOM_BLOCK.get().defaultBlockState();
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
                            world.setBlock(pos, lock, 3);
                        } else {
                            BlockState stone = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.STONE.defaultBlockState();
                            world.setBlock(pos, stone, 3);
                        }
                    } else {
                        world.setBlock(pos, air, 3);
                    }
                }
            }
        }
    }

    /*public static void generateProceduralRoom(Level world, BlockPos basePos, Random random) {
        int width = 5 + random.nextInt(6); // Ancho de la sala entre 5 y 10
        int height = 4 + random.nextInt(3); // Altura de la sala entre 4 y 6
        int depth = 5 + random.nextInt(6); // Profundidad de la sala entre 5 y 10
        int passageWidth = 3; // Ancho del pasillo fijo
        int passageDepth = 3; // Longitud del pasillo


        // Generar sala hueca con patrón procedural
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    // Comprobar si el bloque actual está en el borde
                    boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
                    // Comprobar si el bloque actual está en la posición del hueco para el pasillo
                    boolean isPassageOpening = (z == depth - 1 && x >= (width - passageWidth + 2) / 2 && x < (width + passageWidth - 2) / 2) && y > 0 && y < height - 1;
                    if (isEdge && !isPassageOpening) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE; // 30% de posibilidad de cobblestone, 70% piedra
                        BlockPos pos = basePos.offset(x, y, z);
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                }
            }
        }

        // Generar pasillo hueco centrado en la cara norte con patrón procedural

        // Calcula el inicio del pasillo para que esté centrado en la cara norte de la sala
        BlockPos passageStart = basePos.offset((width - passageWidth) / 2, 0, depth);

        for (int x = 0; x < passageWidth; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < passageDepth; z++) {
                    // Colocar bloques solo en los bordes del pasillo
                    boolean isEdge = x != passageWidth / 2 || y == 0 || y == height - 1;
                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE_BRICKS; // 30% de posibilidad de cobblestone, 70% piedra
                        BlockPos pos = passageStart.offset(x, y, z);
                        world.setBlock(pos, block.defaultBlockState(), 3); // Material del pasillo
                    }
                }
            }
        }
    }*/
    //segunda version
    /*public static void generateProceduralRoom(Level world, BlockPos clickedPos, Random random, Direction facing) {
        int width = 5 + random.nextInt(6); // Ancho de la sala entre 5 y 10
        int height = 4 + random.nextInt(3); // Altura de la sala entre 4 y 6
        int depth = 5 + random.nextInt(6); // Profundidad de la sala entre 5 y 10
        int passageWidth = 3; // Ancho del pasillo fijo
        int passageDepth = 3; // Profundidad del pasillo

        // Determina la posición base ajustada para que el bloque clickeado esté centrado en la cara de la sala
        BlockPos basePos = clickedPos.relative(facing.getOpposite(), depth);

        // Ajustar basePos horizontalmente para centrar el bloque clickeado
        if (facing.getAxis() == Direction.Axis.Z) {
            basePos = basePos.west(width / 2);
        } else if (facing.getAxis() == Direction.Axis.X) {
            basePos = basePos.north(width / 2);
        }

        // Generar sala hueca con patrón procedural
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE;
                        BlockPos pos = basePos.offset(x, y, z);
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                }
            }
        }

        // Calcula la posición de inicio del pasillo directamente desde el bloque clickeado
        BlockPos passageStart = clickedPos.relative(facing.getOpposite());

        // Generar pasillo
        for (int x = 0; x < passageWidth; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < passageDepth; z++) {
                    boolean isEdge = x == 0 || x == passageWidth - 1 || y == 0 || y == height - 1 || z == 0 || z == passageDepth - 1;
                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE_BRICKS;
                        BlockPos pos = passageStart.offset(x - passageWidth / 2 + 1, y, z);
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                }
            }
        }
    }*/

    public static void generateProceduralRoom(Level world, BlockPos clickedPos, Random random, Direction facing) {
        int width = 5 + random.nextInt(6); // Ancho de la sala entre 5 y 10
        int height = 5 + random.nextInt(3); // Altura de la sala entre 5 y 6
        int depth = 5 + random.nextInt(6); // Profundidad de la sala entre 5 y 10
        int passageWidth = 3; // Ancho del pasillo fijo
        int passageDepth = 3; // Profundidad del pasillo

        // Ajusta la posición base de la sala para que comience justo al final del pasillo
        /*BlockPos basePos = clickedPos.relative(facing.getOpposite(), passageDepth + 1);

        // Ajustar basePos horizontalmente para centrar el bloque clickeado en la entrada de la sala
        if (facing.getAxis() == Direction.Axis.Z) {
            basePos = basePos.west((width - 1) / 2);
        } else if (facing.getAxis() == Direction.Axis.X) {
            basePos = basePos.north((width - 1) / 2);
        }

        // Generar sala hueca con patrón procedural
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE;
                        BlockPos pos = basePos.offset(x, y, z);
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                }
            }
        }*/



        // Posición inicial del pasillo centrada con el bloque clickeado
        BlockPos passageStart = clickedPos.relative(facing.getOpposite(), 2).below(2);
        passageStart = passageStart.relative(facing.getClockWise(), -(passageWidth / 2));

        // Generar el pasillo
        for (int x = 0; x < passageWidth; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < passageDepth; z++) {
                    boolean isEdge = x == 0 || x == passageWidth - 1 || y == 0 || y == height - 1;
                    BlockPos pos = passageStart.relative(facing.getClockWise(), x).above(y).relative(facing, z);

                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE_BRICKS;
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                    else {
                        world.removeBlock(pos, false);
                    }
                }
            }
        }
    }

    private static boolean isEdge(int x, int y, int z, int passageWidth, int height, int passageDepth, Direction facing) {
        // General edge conditions
        boolean isVerticalEdge = y == 0 || y == height - 1;
        boolean isHorizontalEdge = false;

        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            // For north and south, the depth is along z-axis
            isHorizontalEdge = x == 0 || x == passageWidth - 1;
        } else if (facing == Direction.EAST || facing == Direction.WEST) {
            // For east and west, the depth is along x-axis
            isHorizontalEdge = x == 0 || x == passageWidth - 1;
        }

        return isVerticalEdge || isHorizontalEdge;
    }
}
