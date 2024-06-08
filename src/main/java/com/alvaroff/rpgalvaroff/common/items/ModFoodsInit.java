package com.alvaroff.rpgalvaroff.common.items;

import net.minecraft.world.food.FoodProperties;

public class ModFoodsInit {
    public static final FoodProperties MAGIC_BERRIES = (new FoodProperties.Builder())
            .nutrition(4)
            .saturationMod(0.5F)
            .alwaysEat()
            .build();
}
