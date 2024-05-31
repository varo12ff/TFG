package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.common.utils.PlayerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerStatsC2SPacket {
    private CompoundTag playerStats;
    public PlayerStatsC2SPacket(CompoundTag playerStats){
        this.playerStats = playerStats;

    }

    public PlayerStatsC2SPacket(FriendlyByteBuf buf){
        playerStats = buf.readAnySizeNbt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(playerStats);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).loadNBTData(playerStats);
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).syncPlayer(player);
            int maxMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getMana();
            float currentMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getCurrentMana();
            ManaBarOverlay.updateMana(maxMana, (int)currentMana);
            ManaBarOverlay.drawBar(player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getPlayerClass());
            PlayerUtils.changeAttributes(player);
        });

        return true;
    }

}
