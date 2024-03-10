package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.utils.DimensionUtils;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import com.alvaroff.rpgalvaroff.dungeonState.DungeonState;
import com.alvaroff.rpgalvaroff.dungeonState.DungeonStateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID)
public class ServerEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<Level> event){
        if(event.getObject() instanceof Level)
            if(!event.getObject().getCapability(DungeonStateProvider.DUNGEON_STATUS).isPresent())
                event.addCapability(new ResourceLocation(RPGalvaroff.MOD_ID, "properties"), new DungeonStateProvider());
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
        event.register(DungeonState.class);
    }
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Level world = event.getPlayer().level;
        boolean dungeonState = event.getPlayer().getLevel().getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).getStatus();


        if (world.dimension().equals(DimensionInit.RPGDIM_KEY)) {
            System.out.println(dungeonState);
            if(!dungeonState) {

                DimensionUtils.clearBlocksInCubeCentered(world, 0, 0, 0, 20);

                DimensionUtils.generateFlat(world, 0, 0, 0);

                world.getCapability(DungeonStateProvider.DUNGEON_STATUS).ifPresent(active -> {
                    active.setStatus(true);
                });
            }

        }
    }
}
