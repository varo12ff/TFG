package com.alvaroff.rpgalvaroff.networking.packet;

import com.alvaroff.rpgalvaroff.capabilities.playerSkills.PlayerSkills;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.SkillOverlay;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LaunchSkillC2SPacket {
    private final int id;
    public LaunchSkillC2SPacket(int id){
        this.id = id;

    }

    public LaunchSkillC2SPacket(FriendlyByteBuf buf){
        this.id = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(id);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        supplier.get().enqueueWork(() -> {
            ServerPlayer player = supplier.get().getSender();
            PlayerSkills actionSkill = new PlayerSkills();
            actionSkill.initSkillsVector();
            if(!player.getLevel().isClientSide()) {
                if (player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getCurrentMana() < actionSkill.getSkill(id).getManaCost()) {
                    player.displayClientMessage(new TextComponent("Maná insuficiente."), true);
                } else {
                    float maxMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getManaCant();
                    float currentMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getCurrentMana();

                    actionSkill.getSkill(id).launch(player);
                    player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).subtractMana(actionSkill.getSkill(id).getManaCost());
                    currentMana -= actionSkill.getSkill(id).getManaCost();

                    ModMessages.sendToPlayer(new UpdateManaOverlayS2CPacket(maxMana, currentMana), player);
                }
            }


        });
        supplier.get().setPacketHandled(true);

        return true;
    }

    private static HitResult rayTrace(Player player, double range) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F).scale(range);
        Vec3 targetPosition = eyePosition.add(lookVec);
        return player.level.clip(new net.minecraft.world.level.ClipContext(eyePosition, targetPosition, net.minecraft.world.level.ClipContext.Block.OUTLINE, net.minecraft.world.level.ClipContext.Fluid.NONE, player));
    }
}
