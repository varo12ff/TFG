package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateManaOverlayS2CPacket {
    private final float maxMana, currentMana;
    public UpdateManaOverlayS2CPacket(float maxMana, float currentMana){
        this.maxMana = maxMana;
        this.currentMana = currentMana;

    }

    public UpdateManaOverlayS2CPacket(FriendlyByteBuf buf){

        this.maxMana = buf.readFloat();
        this.currentMana = buf.readFloat();
    }
    public void toBytes(FriendlyByteBuf buf){

        buf.writeFloat(maxMana);
        buf.writeFloat(currentMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ManaBarOverlay.updateMana(maxMana, currentMana);
        });

        return true;
    }
}
