package com.alvaroff.rpgalvaroff.common.utils;

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
}
