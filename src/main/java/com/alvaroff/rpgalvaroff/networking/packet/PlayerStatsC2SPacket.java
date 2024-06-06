package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
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
            PlayerUtils.changeAttributes(player);
            /*float maxMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getManaCant();
            float currentMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getCurrentMana();

            ManaBarOverlay.updateMana(maxMana, currentMana);
            ManaBarOverlay.drawBar(player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getPlayerClass());
            SkillOverlay.drawHud(player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getPlayerClass());

            SkillOverlay.updateSkills(player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getActionSkills());*/
        });

        return true;
    }

}
