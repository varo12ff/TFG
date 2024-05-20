package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.common.items.ItemInit;
import com.alvaroff.rpgalvaroff.common.utils.DimensionUtils;
import com.alvaroff.rpgalvaroff.common.utils.PlayerUtils;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import com.alvaroff.rpgalvaroff.capabilities.dungeonState.DungeonState;
import com.alvaroff.rpgalvaroff.capabilities.dungeonState.DungeonStateProvider;
import com.sun.jna.platform.unix.X11;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.UUID;

import static com.alvaroff.rpgalvaroff.common.utils.DimensionUtils.generateProceduralRoom;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player)
            if(!event.getObject().getCapability(PlayerStatsProvider.PLAYER_STATS).isPresent())
                event.addCapability(new ResourceLocation(RPGalvaroff.MOD_ID, "stats"), new PlayerStatsProvider());


    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){

        event.register(DungeonState.class);
        event.register(PlayerStats.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).syncPlayer(player);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if(!player.getLevel().isClientSide()) {
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).syncPlayer(player);
            PlayerUtils.changeAttributes(player);
        }
    }

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event){
        if(event.getSource().getEntity() instanceof Player){

            Player player = (Player) event.getSource().getEntity();
            LivingEntity mob = event.getEntityLiving();
            int newXp = (int)mob.getMaxHealth() / 2;

            player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).addXp(newXp);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Level world = event.getPlayer().level;
        Player player = event.getPlayer();
        ItemStack key = new ItemStack(ItemInit.KEY_D.get());

        boolean dungeonState = event.getPlayer().getLevel().getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).getStatus();


        if (world.dimension().equals(DimensionInit.RPGDIM_KEY)) {
            if(!dungeonState) {

                boolean added = player.addItem(key);

                if (!added) {
                    player.drop(key, false);
                }

                DimensionUtils.clearBlocksInCubeCentered(world, 0, 0, 0, 100);

                DimensionUtils.generateStartRoom(world, 0, 0, 0, 5);

                world.getCapability(DungeonStateProvider.DUNGEON_STATUS).ifPresent(active -> {
                    active.setStatus(true);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){

        if(event.isWasDeath()){
            Player original = event.getOriginal();
            original.reviveCaps();

            original.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(oldStore -> {
                event.getPlayer().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<Level> event){
        if(event.getObject() instanceof Level)
            if(!event.getObject().getCapability(DungeonStateProvider.DUNGEON_STATUS).isPresent())
                event.addCapability(new ResourceLocation(RPGalvaroff.MOD_ID, "properties"), new DungeonStateProvider());

    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.SPAWNER) {

            Level world = (Level) event.getWorld();
            if (!world.isClientSide && world.dimension().equals(DimensionInit.RPGDIM_KEY)) {

                world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).subtractSpawner();

                if(world.getCapability(DungeonStateProvider.DUNGEON_STATUS).orElse(new DungeonState()).getActiveSpawners() == 0){
                    Player player = event.getPlayer();
                    ItemStack key = new ItemStack(ItemInit.KEY_D.get());

                    boolean added = player.addItem(key);

                    if (!added) {
                        player.drop(key, false);
                    }
                }
            }
        }
    }

}
