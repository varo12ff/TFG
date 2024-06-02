package com.alvaroff.rpgalvaroff.common.items.custom;
import com.alvaroff.rpgalvaroff.capabilities.playerSkills.PlayerSkills;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.PlayerStatsC2SPacket;
import net.minecraft.network.chat.TranslatableComponent;
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
            ModMessages.sendToServer(new PlayerStatsC2SPacket(newPlayerStats.getNBT()));


            player.displayClientMessage(new TranslatableComponent("item.rpgalvaroff.debbuger.reset_notify"), true);
        }

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
