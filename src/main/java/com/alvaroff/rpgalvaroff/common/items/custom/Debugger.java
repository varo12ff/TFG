package com.alvaroff.rpgalvaroff.common.items.custom;
import com.alvaroff.rpgalvaroff.capabilities.playerSkills.PlayerSkills;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.common.utils.PlayerUtils;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.OverlayUpdateS2CPacket;
import com.alvaroff.rpgalvaroff.networking.packet.PlayerStatsC2SPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Debugger extends Item {
    public Debugger(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        if(!world.isClientSide()){
            PlayerStats newPlayerStats = new PlayerStats();
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).copyFrom(newPlayerStats);
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).syncPlayer(player);
            PlayerUtils.changeAttributes(player);
            ModMessages.sendToPlayer(new OverlayUpdateS2CPacket(newPlayerStats.getNBT()), (ServerPlayer) player);

            player.displayClientMessage(new TranslatableComponent("item.rpgalvaroff.debbuger.reset_notify"), true);
        }

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
