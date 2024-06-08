package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSkillOverlayS2CPacket {
    private final int[] actionSkills;
    public UpdateSkillOverlayS2CPacket(int[] actionSkills){
        this.actionSkills = actionSkills;

    }

    public UpdateSkillOverlayS2CPacket(FriendlyByteBuf buf){

        this.actionSkills = buf.readVarIntArray();
    }
    public void toBytes(FriendlyByteBuf buf){

        buf.writeVarIntArray(actionSkills);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SkillOverlay.updateSkills(actionSkills);
        });

        return true;
    }
}
