package com.alvaroff.rpgalvaroff.common.utils;

import net.minecraft.world.entity.player.Player;

public class PlayerUtils {
    public static void changeAttributes(Player player){
        player.setHealth(player.getMaxHealth());
    }
}
