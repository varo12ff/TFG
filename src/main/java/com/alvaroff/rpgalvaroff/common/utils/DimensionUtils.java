package com.alvaroff.rpgalvaroff.common.utils;

import com.alvaroff.rpgalvaroff.capabilities.dungeonState.DungeonState;
import com.alvaroff.rpgalvaroff.capabilities.dungeonState.DungeonStateProvider;
import com.alvaroff.rpgalvaroff.common.blocks.BlockInit;
import com.alvaroff.rpgalvaroff.common.entities.EntityInit;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit.RPGDIM_KEY;

public class DimensionUtils {

    public static void handleDimensionEntry(ServerPlayer player) {

        CompoundTag playerData = player.getPersistentData();
        CompoundTag data = playerData.getCompound("PlayerData");
        data.putLong("LastOverworldPosition", player.getOnPos().asLong());
        playerData.put("PlayerData", data);
        player.getCommandSenderWorld().getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING).set(false, player.getCommandSenderWorld().getServer());
        player.teleportTo(player.server.getLevel(RPGDIM_KEY), 0, 2, 0, player.yRotO, player.xRotO);
    }

    public static void handleDimensionExit(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();

        player.getCommandSenderWorld().getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING).set(true, player.getCommandSenderWorld().getServer());
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
                    }
                    else if (x == 0 || x == 2 * halfSize || y == 0 || y == 2 * halfSize || z == 0 || z == 2 * halfSize) {
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

    public static void generateProceduralRoom(Level world, BlockPos clickedPos, Random random, Direction facing) {
        int width = 8 + random.nextInt(12); // Ancho de la sala entre 8 y 20
        int height = 5; // Altura de la sala entre 5 y 8
        int depth = 8 + random.nextInt(12); // Profundidad de la sala entre 8 y 20
        int passageWidth = 3; // Ancho del pasillo fijo
        int passageDepth = 3; // Profundidad del pasillo
        int spawnerCount = random.nextInt(4) + 1; // Genera un número entre 1 y 4
        int placedSpawners = 0;

        BlockState lock = BlockInit.UNLOCK_NEW_ROOM_BLOCK.get().defaultBlockState();

        // Determina la posición base ajustada para que el bloque clickeado esté centrado en la cara de la sala
        BlockPos basePos = clickedPos.relative(facing.getOpposite(), passageDepth - 1).below(2);

        // Ajustar basePos horizontalmente para centrar el bloque clickeado
        if (facing.getAxis() == Direction.Axis.Z) {
            basePos = basePos.west(width / 2);
        } else if (facing.getAxis() == Direction.Axis.X) {
            basePos = basePos.north(width / 2);
        }

        if (facing.getOpposite() == Direction.NORTH){
            basePos = basePos.north(depth - 1);
        }
        else if (facing.getOpposite() == Direction.WEST) {
            basePos = basePos.west(width - 1); // Mover al máximo hacia atrás en el eje Oeste
        }



        // Lista de direcciones posibles para colocar el bloque de obsidiana
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
        directions.remove(facing); // Remover la dirección del pasillo

        // Filtrar direcciones que no interferirán con otras salas futuras
        List<Direction> validDirections = new ArrayList<>();
        for (Direction dir : directions) {
            if (!willInterfereWithFutureRoom(world, basePos, dir, width, height, depth)) {
                validDirections.add(dir);
            }
        }

        boolean nextRoom = true;
        Direction obsidianFacing = null;

        if (validDirections.isEmpty()) {
            // Si no hay direcciones viables, no colocar el bloque de obsidiana
            nextRoom = false;
        }
        else{
            obsidianFacing = validDirections.get(random.nextInt(validDirections.size())); // Seleccionar una dirección aleatoria
        }

        float bossProbabilty = world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).getBossRoom();
        boolean bossRoom = false;

        if(random.nextFloat() < bossProbabilty) {
            world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).setBossRoom(0);
            bossRoom = true;
        }
        else{
            world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).addPercentageBossRoom();
        }

        // Generar sala hueca con patrón procedural
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
                    boolean isCenterWall = (x == width / 2 || z == depth / 2) && y > 0 && y < height - 1;
                    BlockPos pos = basePos.offset(x, y, z);

                    if (isEdge) {

                        if (y == 0) {
                            Block block = random.nextFloat() > 0.7 ? Blocks.GLOWSTONE : Blocks.STONE;

                            world.setBlock(pos, block.defaultBlockState(), 3);
                        }
                        else {
                            if (nextRoom && y == 2 && isCenterWall && ((obsidianFacing == Direction.NORTH && z == 0) ||
                                    (obsidianFacing == Direction.SOUTH && z == depth - 1) ||
                                    (obsidianFacing == Direction.WEST && x == 0) ||
                                    (obsidianFacing == Direction.EAST && x == width - 1)) && !bossRoom) {

                                world.setBlock(pos, lock, 3);
                            } else {
                                Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE;

                                world.setBlock(pos, block.defaultBlockState(), 3);
                            }
                        }
                    }
                    else{
                        // Intentar colocar un spawner en una posición aleatoria no en el borde
                        if (y == 1 && placedSpawners < spawnerCount && random.nextFloat() < 0.1 && !bossRoom) { // Probabilidad de colocar un spawner
                            if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
                                world.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
                                SpawnerBlockEntity spawner = (SpawnerBlockEntity) world.getBlockEntity(pos);
                                if (spawner != null) {
                                    spawner.getSpawner().setEntityId(EntityInit.SCARECRAFT.get());
                                }
                                placedSpawners++;
                            }
                        }
                    }

                }
            }
        }

        world.getCapability(DungeonStateProvider.DUNGEON_STATUS).ifPresent(active -> {
            active.setActiveSpawners(spawnerCount);
        });



        // Posición inicial del pasillo centrada con el bloque clickeado
        BlockPos passageStart = clickedPos.relative(facing.getOpposite(), 2).below(2);
        passageStart = passageStart.relative(facing.getClockWise(), -(passageWidth / 2));
        int passageHeight = 4;

        // Generar el pasillo
        for (int x = 0; x < passageWidth; x++) {
            for (int y = 0; y < passageHeight; y++) {
                for (int z = 0; z < passageDepth; z++) {
                    boolean isEdge = x == 0 || x == passageWidth - 1 || y == 0 || y == passageHeight - 1;
                    BlockPos pos = passageStart.relative(facing.getClockWise(), x).above(y).relative(facing, z);

                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE_BRICKS;
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                    else {
                        world.removeBlock(pos, false);

                        if (z == passageDepth - 1) {
                            BlockPos doorPos = pos.relative(facing.getOpposite());
                            if (y == 1) {
                                world.setBlock(doorPos, Blocks.OAK_DOOR.defaultBlockState()
                                        .setValue(DoorBlock.FACING, facing)
                                        .setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER), 3);
                            }
                            else if (y == 2) {
                                world.setBlock(doorPos, Blocks.OAK_DOOR.defaultBlockState()
                                        .setValue(DoorBlock.FACING, facing)
                                        .setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void generateProceduralLavaRoom(Level world, BlockPos clickedPos, Random random, Direction facing) {
        int width = 8 + random.nextInt(12); // Ancho de la sala entre 8 y 20
        int height = 7; // Altura de la sala entre 5 y 8
        int depth = 8 + random.nextInt(12); // Profundidad de la sala entre 8 y 20
        int passageWidth = 3; // Ancho del pasillo fijo
        int passageDepth = 3; // Profundidad del pasillo
        int spawnerCount = random.nextInt(4) + 1; // Genera un número entre 1 y 4
        int placedSpawners = 0;

        BlockState lock = BlockInit.UNLOCK_NEW_ROOM_BLOCK.get().defaultBlockState();

        // Determina la posición base ajustada para que el bloque clickeado esté centrado en la cara de la sala
        BlockPos basePos = clickedPos.relative(facing.getOpposite(), passageDepth - 1).below(3);

        // Ajustar basePos horizontalmente para centrar el bloque clickeado
        if (facing.getAxis() == Direction.Axis.Z) {
            basePos = basePos.west(width / 2);
        } else if (facing.getAxis() == Direction.Axis.X) {
            basePos = basePos.north(width / 2);
        }

        if (facing.getOpposite() == Direction.NORTH){
            basePos = basePos.north(depth - 1);
        }
        else if (facing.getOpposite() == Direction.WEST) {
            basePos = basePos.west(width - 1); // Mover al máximo hacia atrás en el eje Oeste
        }



        // Lista de direcciones posibles para colocar el bloque de obsidiana
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
        directions.remove(facing); // Remover la dirección del pasillo

        // Filtrar direcciones que no interferirán con otras salas futuras
        List<Direction> validDirections = new ArrayList<>();
        for (Direction dir : directions) {
            if (!willInterfereWithFutureRoom(world, basePos, dir, width, height, depth)) {
                validDirections.add(dir);
            }
        }

        boolean nextRoom = true;
        Direction obsidianFacing = null;

        if (validDirections.isEmpty()) {
            // Si no hay direcciones viables, no colocar el bloque de obsidiana
            nextRoom = false;
        }
        else{
            obsidianFacing = validDirections.get(random.nextInt(validDirections.size())); // Seleccionar una dirección aleatoria
        }

        float bossProbabilty = world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).getBossRoom();
        boolean bossRoom = false;

        if(random.nextFloat() < bossProbabilty) {
            world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).setBossRoom(0);
            bossRoom = true;
        }
        else{
            world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).addPercentageBossRoom();
        }

        // Generar sala hueca con patrón procedural
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
                    boolean isCenterWall = (x == width / 2 || z == depth / 2) && y > 0 && y < height - 1;
                    BlockPos pos = basePos.offset(x, y, z);

                    if (isEdge) {

                        if (y == 0) {
                            Block block = random.nextFloat() > 0.7 ? Blocks.GLOWSTONE : Blocks.STONE;

                            world.setBlock(pos, block.defaultBlockState(), 3);
                        }
                        else {
                            if (nextRoom && y == 3 && isCenterWall && ((obsidianFacing == Direction.NORTH && z == 0) ||
                                    (obsidianFacing == Direction.SOUTH && z == depth - 1) ||
                                    (obsidianFacing == Direction.WEST && x == 0) ||
                                    (obsidianFacing == Direction.EAST && x == width - 1)) && !bossRoom) {

                                world.setBlock(pos, lock, 3);
                            }

                            else {
                                Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE;

                                world.setBlock(pos, block.defaultBlockState(), 3);
                            }
                        }
                    }
                    else if(y == 1){

                        int patternIndex = (x + z) % 4;
                        Block block;

                        if (patternIndex == 0)
                            block = Blocks.STONE;
                        else
                            block = Blocks.LAVA;


                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                    else{
                        // Intentar colocar un spawner en una posición aleatoria no en el borde
                        if (y == 2 && placedSpawners < spawnerCount && random.nextFloat() < 0.1 && !bossRoom) { // Probabilidad de colocar un spawner
                            BlockPos belowPos = pos.below();
                            if (world.getBlockState(pos).getBlock() == Blocks.AIR && world.getBlockState(belowPos).getBlock() == Blocks.STONE) {
                                world.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
                                SpawnerBlockEntity spawner = (SpawnerBlockEntity) world.getBlockEntity(pos);
                                if (spawner != null) {
                                    spawner.getSpawner().setEntityId(EntityType.BLAZE);
                                }
                                placedSpawners++;
                            }
                        }
                    }

                }
            }
        }

        world.getCapability(DungeonStateProvider.DUNGEON_STATUS).ifPresent(active -> {
            active.setActiveSpawners(spawnerCount);
        });



        // Posición inicial del pasillo centrada con el bloque clickeado
        BlockPos passageStart = clickedPos.relative(facing.getOpposite(), 2).below(2);
        passageStart = passageStart.relative(facing.getClockWise(), -(passageWidth / 2));
        int passageHeight = 4;

        // Generar el pasillo
        for (int x = 0; x < passageWidth; x++) {
            for (int y = 0; y < passageHeight; y++) {
                for (int z = 0; z < passageDepth; z++) {
                    boolean isEdge = x == 0 || x == passageWidth - 1 || y == 0 || y == passageHeight - 1;
                    BlockPos pos = passageStart.relative(facing.getClockWise(), x).above(y).relative(facing, z);

                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE_BRICKS;
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                    else {
                        world.removeBlock(pos, false);

                        if (z == passageDepth - 1) {
                            BlockPos doorPos = pos.relative(facing.getOpposite());
                            if (y == 1) {
                                world.setBlock(doorPos, Blocks.OAK_DOOR.defaultBlockState()
                                        .setValue(DoorBlock.FACING, facing)
                                        .setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER), 3);
                            }
                            else if (y == 2) {
                                world.setBlock(doorPos, Blocks.OAK_DOOR.defaultBlockState()
                                        .setValue(DoorBlock.FACING, facing)
                                        .setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void generateProceduralWaterRoom(Level world, BlockPos clickedPos, Random random, Direction facing) {
        int width = 8 + random.nextInt(12); // Ancho de la sala entre 8 y 20
        int height = 9; // Altura de la sala entre 5 y 8
        int depth = 8 + random.nextInt(12); // Profundidad de la sala entre 8 y 20
        int passageWidth = 3; // Ancho del pasillo fijo
        int passageDepth = 3; // Profundidad del pasillo
        int spawnerCount = random.nextInt(4) + 1; // Genera un número entre 1 y 4
        int placedSpawners = 0;

        BlockState lock = BlockInit.UNLOCK_NEW_ROOM_BLOCK.get().defaultBlockState();

        // Determina la posición base ajustada para que el bloque clickeado esté centrado en la cara de la sala
        BlockPos basePos = clickedPos.relative(facing.getOpposite(), passageDepth - 1).below(4);

        // Ajustar basePos horizontalmente para centrar el bloque clickeado
        if (facing.getAxis() == Direction.Axis.Z) {
            basePos = basePos.west(width / 2);
        } else if (facing.getAxis() == Direction.Axis.X) {
            basePos = basePos.north(width / 2);
        }

        if (facing.getOpposite() == Direction.NORTH){
            basePos = basePos.north(depth - 1);
        }
        else if (facing.getOpposite() == Direction.WEST) {
            basePos = basePos.west(width - 1); // Mover al máximo hacia atrás en el eje Oeste
        }



        // Lista de direcciones posibles para colocar el bloque de obsidiana
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST));
        directions.remove(facing); // Remover la dirección del pasillo

        // Filtrar direcciones que no interferirán con otras salas futuras
        List<Direction> validDirections = new ArrayList<>();
        for (Direction dir : directions) {
            if (!willInterfereWithFutureRoom(world, basePos, dir, width, height, depth)) {
                validDirections.add(dir);
            }
        }

        boolean nextRoom = true;
        Direction obsidianFacing = null;

        if (validDirections.isEmpty()) {
            // Si no hay direcciones viables, no colocar el bloque de obsidiana
            nextRoom = false;
        }
        else{
            obsidianFacing = validDirections.get(random.nextInt(validDirections.size())); // Seleccionar una dirección aleatoria
        }

        float bossProbabilty = world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).getBossRoom();
        boolean bossRoom = false;

        if(random.nextFloat() < bossProbabilty) {
            world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).setBossRoom(0);
            bossRoom = true;
        }
        else{
            world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).addPercentageBossRoom();
        }

        // Generar sala hueca con patrón procedural
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    boolean isEdge = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
                    boolean isCenterWall = (x == width / 2 || z == depth / 2) && y > 0 && y < height - 1;
                    BlockPos pos = basePos.offset(x, y, z);

                    if (isEdge) {

                        if (y == 0) {
                            Block block = random.nextFloat() > 0.7 ? Blocks.SEA_LANTERN : Blocks.STONE;

                            world.setBlock(pos, block.defaultBlockState(), 3);
                        }
                        else {
                            if (nextRoom && y == 3 && isCenterWall && ((obsidianFacing == Direction.NORTH && z == 0) ||
                                    (obsidianFacing == Direction.SOUTH && z == depth - 1) ||
                                    (obsidianFacing == Direction.WEST && x == 0) ||
                                    (obsidianFacing == Direction.EAST && x == width - 1)) && !bossRoom) {

                                world.setBlock(pos, lock, 3);
                            }

                            else {
                                Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE;

                                world.setBlock(pos, block.defaultBlockState(), 3);
                            }
                        }
                    }
                    else{
                        // Intentar colocar un spawner en una posición aleatoria no en el borde
                        if (placedSpawners < spawnerCount && random.nextFloat() < 0.1 && !bossRoom) { // Probabilidad de colocar un spawner
                            if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
                                world.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
                                SpawnerBlockEntity spawner = (SpawnerBlockEntity) world.getBlockEntity(pos);
                                if (spawner != null) {
                                    spawner.getSpawner().setEntityId(EntityType.GUARDIAN);
                                }
                                placedSpawners++;
                            }
                        }
                        else
                            world.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
                    }
                }
            }
        }

        world.getCapability(DungeonStateProvider.DUNGEON_STATUS).ifPresent(active -> {
            active.setActiveSpawners(spawnerCount);
        });



        // Posición inicial del pasillo centrada con el bloque clickeado
        BlockPos passageStart = clickedPos.relative(facing.getOpposite(), 2).below(2);
        passageStart = passageStart.relative(facing.getClockWise(), -(passageWidth / 2));
        int passageHeight = 4;

        // Generar el pasillo
        for (int x = 0; x < passageWidth; x++) {
            for (int y = 0; y < passageHeight; y++) {
                for (int z = 0; z < passageDepth; z++) {
                    boolean isEdge = x == 0 || x == passageWidth - 1 || y == 0 || y == passageHeight - 1;
                    BlockPos pos = passageStart.relative(facing.getClockWise(), x).above(y).relative(facing, z);

                    if (isEdge) {
                        Block block = random.nextFloat() > 0.7 ? Blocks.COBBLESTONE : Blocks.STONE_BRICKS;
                        world.setBlock(pos, block.defaultBlockState(), 3);
                    }
                    else {
                        world.removeBlock(pos, false);

                        if (z == passageDepth - 1) {
                            BlockPos doorPos = pos.relative(facing.getOpposite());
                            if (y == 1) {
                                world.setBlock(doorPos, Blocks.OAK_DOOR.defaultBlockState()
                                        .setValue(DoorBlock.FACING, facing)
                                        .setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER), 3);
                            }
                            else if (y == 2) {
                                world.setBlock(doorPos, Blocks.OAK_DOOR.defaultBlockState()
                                        .setValue(DoorBlock.FACING, facing)
                                        .setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean willInterfereWithFutureRoom(Level world, BlockPos basePos, Direction direction, int width, int height, int depth) {
        // Determinar el tamaño y la posición del área de la sala futura para la dirección dada
        int futureWidth = 8 + 11; // Ancho máximo de la sala futura
        int futureHeight = 5 + 2; // Altura máxima de la sala futura
        int futureDepth = 8 + 11; // Profundidad máxima de la sala futura

        // Ajustar la posición base para considerar la colocación del bloque de obsidiana
        BlockPos futureBasePos = basePos.relative(direction, direction == Direction.NORTH || direction == Direction.SOUTH ? depth : width);
        futureBasePos = futureBasePos.offset(direction.getStepX() * (width + futureWidth / 2), 0, direction.getStepZ() * (depth + futureDepth / 2));
        if (direction == Direction.NORTH) {
            futureBasePos = futureBasePos.north(futureDepth);
        } else if (direction == Direction.WEST) {
            futureBasePos = futureBasePos.west(futureWidth);
        }

        // Comprobar si hay bloques que no sean aire en el área de la sala futura
        for (int x = 0; x < futureWidth; x++) {
            for (int y = 0; y < futureHeight; y++) {
                for (int z = 0; z < futureDepth; z++) {
                    BlockPos pos = futureBasePos.offset(x, y, z);
                    if (!world.isEmptyBlock(pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
