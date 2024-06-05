package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
import com.alvaroff.rpgalvaroff.common.utils.PlayerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SummonLightningC2SPacket {
    private final double x, y, z;
    public SummonLightningC2SPacket(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public SummonLightningC2SPacket(FriendlyByteBuf buf){
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            if (player != null && player.level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel) player.level;
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (lightning != null) {
                    lightning.moveTo(x, y, z);
                    serverLevel.addFreshEntity(lightning);
                }
            }
        });
        supplier.get().setPacketHandled(true);

        return true;
    }
}
