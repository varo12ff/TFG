package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
import com.alvaroff.rpgalvaroff.common.utils.PlayerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OverlayUpdateS2CPacket {
    private CompoundTag playerStats;
    public OverlayUpdateS2CPacket(CompoundTag playerStats){
        this.playerStats = playerStats;

    }

    public OverlayUpdateS2CPacket(FriendlyByteBuf buf){
        playerStats = buf.readAnySizeNbt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(playerStats);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerStats playerStats1 = new PlayerStats();
            playerStats1.loadNBTData(playerStats);

            float maxMana = playerStats1.getManaCant();
            float currentMana = playerStats1.getCurrentMana();

            ManaBarOverlay.updateMana(maxMana, currentMana);
            ManaBarOverlay.drawBar(playerStats1.getPlayerClass());
            SkillOverlay.drawHud(playerStats1.getPlayerClass());
            SkillOverlay.updateSkills(playerStats1.getActionSkills());
        });

        return true;
    }
}
