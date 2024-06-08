package com.alvaroff.rpgalvaroff.common.items.custom.food;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.UpdateManaOverlayS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicBerries extends Item {
    public MagicBerries(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity player) {
        ItemStack itemstack = super.finishUsingItem(stack, world, player);
        if(player instanceof Player) {

            if (!world.isClientSide) {
                player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).addCurrentMana(2);
                float maxMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getManaCant();
                float currentMana = player.getCapability(PlayerStatsProvider.PLAYER_STATS).orElse(new PlayerStats()).getCurrentMana();

                ModMessages.sendToPlayer(new UpdateManaOverlayS2CPacket(maxMana, currentMana), (ServerPlayer) player);
            }

        }

        return itemstack;
    }
}
