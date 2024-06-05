package com.alvaroff.rpgalvaroff.networking;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.networking.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RPGalvaroff.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(PlayerStatsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerStatsC2SPacket::new)
                .encoder(PlayerStatsC2SPacket::toBytes)
                .consumer(PlayerStatsC2SPacket::handle)
                .add();

        net.messageBuilder(KeyBindingC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(KeyBindingC2SPacket::new)
                .encoder(KeyBindingC2SPacket::toBytes)
                .consumer(KeyBindingC2SPacket::handle)
                .add();

        net.messageBuilder(ClientGUIS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientGUIS2CPacket::new)
                .encoder(ClientGUIS2CPacket::toBytes)
                .consumer(ClientGUIS2CPacket::handle)
                .add();

        net.messageBuilder(UpdateManaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpdateManaC2SPacket::new)
                .encoder(UpdateManaC2SPacket::toBytes)
                .consumer(UpdateManaC2SPacket::handle)
                .add();

        net.messageBuilder(SummonLightningC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SummonLightningC2SPacket::new)
                .encoder(SummonLightningC2SPacket::toBytes)
                .consumer(SummonLightningC2SPacket::handle)
                .add();

        net.messageBuilder(LaunchSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LaunchSkillC2SPacket::new)
                .encoder(LaunchSkillC2SPacket::toBytes)
                .consumer(LaunchSkillC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
