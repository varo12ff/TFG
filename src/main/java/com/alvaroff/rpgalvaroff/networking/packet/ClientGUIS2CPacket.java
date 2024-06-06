package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerClass;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.RpgGUI;
import com.alvaroff.rpgalvaroff.client.gui.SkillGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientGUIS2CPacket {
    private CompoundTag playerStats;
    private int guiId;
    public ClientGUIS2CPacket(CompoundTag playerStats, int id){
        this.playerStats = playerStats;
        guiId = id;

    }

    public ClientGUIS2CPacket(FriendlyByteBuf buf){

        playerStats = buf.readAnySizeNbt();
        guiId = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf){

        buf.writeNbt(playerStats);
        buf.writeInt(guiId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            PlayerStats playerStats1 = new PlayerStats();
            playerStats1.loadNBTData(playerStats);

            if(guiId == 0) {
                TranslatableComponent translationKey = new TranslatableComponent("gui.statsName");
                RpgGUI gui = new RpgGUI(new TextComponent(translationKey.getString()), playerStats1);
                gui.openScreen();
            }
            else if (guiId == 1) {
                TranslatableComponent translationKey = new TranslatableComponent("gui.skillName");
                SkillGUI gui = new SkillGUI(new TextComponent(translationKey.getString()), playerStats1);
                gui.openScreen();
            }

        });
        context.setPacketHandled(true);
        return true;
    }
}
