package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.common.utils.DimensionUtils;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import com.alvaroff.rpgalvaroff.capabilities.dungeonState.DungeonState;
import com.alvaroff.rpgalvaroff.capabilities.dungeonState.DungeonStateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID)
public class ServerEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<Level> event){
        if(event.getObject() instanceof Level)
            if(!event.getObject().getCapability(DungeonStateProvider.DUNGEON_STATUS).isPresent())
                event.addCapability(new ResourceLocation(RPGalvaroff.MOD_ID, "properties"), new DungeonStateProvider());

    }

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
