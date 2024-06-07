package com.alvaroff.rpgalvaroff.common.utils;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public class PlayerUtils {
    public static void changeAttributes(Player player){
        player.setHealth(player.getMaxHealth());
    }

    public static boolean hasItem(Player player, ItemStack item){
        boolean hasItem = false;

        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.getItem() == item.getItem()) {
                hasItem = true;
                break;
            }
        }

        return hasItem;
    }

    public static boolean isSkillLearned(ArrayList<Integer> skills, int id){
        boolean isLearned = false;

        for (int skill : skills) {
            if (skill == id) {
                isLearned = true;
                break;
            }
        }

        return isLearned;
    }

    public static double getPlayerTotalAttackDamage(Player player) {
        AttributeInstance attackAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
        double baseAttackDamage = attackAttribute != null ? attackAttribute.getValue() : 0.0;

        // Añadir el efecto de pociones
        if (player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            MobEffectInstance effect = player.getEffect(MobEffects.DAMAGE_BOOST);
            baseAttackDamage += (1.5 * (effect.getAmplifier() + 1));
        }

        if (player.hasEffect(MobEffects.WEAKNESS)) {
            MobEffectInstance effect = player.getEffect(MobEffects.WEAKNESS);
            baseAttackDamage -= (0.5 * (effect.getAmplifier() + 1));
        }

        return baseAttackDamage;
    }
}
