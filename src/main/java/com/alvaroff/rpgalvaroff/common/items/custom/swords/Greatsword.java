package com.alvaroff.rpgalvaroff.common.items.custom.swords;

import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStatsProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Greatsword extends SwordItem {
    public Greatsword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(stats -> {

                int level = stats.getStrength();
                int baseDamage = 12;
                int extraDamage = (level >= 5) ? (level / 5) * 2 : 0;
                int totalDamage = (level < 5) ? 0 : baseDamage + extraDamage;

                target.hurt(DamageSource.playerAttack(player), totalDamage);
                stack.hurtAndBreak(1, attacker, (p_43296_) -> {
                    p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            });
        }

        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TextComponent("Nivel mínimo de fuerza: 5").withStyle(ChatFormatting.GRAY));
        tooltip.add(new TextComponent("Escalado por fuerza S").withStyle(ChatFormatting.DARK_PURPLE));
    }
}


