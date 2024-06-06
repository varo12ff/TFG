package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.lwjgl.system.CallbackI;

import java.util.function.Supplier;

public class KeyBindingC2SPacket {

    public KeyBindingC2SPacket(){


    }

    public KeyBindingC2SPacket(FriendlyByteBuf buf){

    }
    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ModMessages.sendToPlayer(new ClientGUIS2CPacket(player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getNBT()), player);
        });

        context.setPacketHandled(true);
        return true;
    }
}
