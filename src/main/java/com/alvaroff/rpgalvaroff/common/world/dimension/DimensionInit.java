package com.alvaroff.rpgalvaroff.common.world.dimension;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class DimensionInit {
    public static ResourceKey<Level> RPGDIM_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(RPGalvaroff.MOD_ID, "rpgdim"));

    public static final ResourceKey<DimensionType> RPG_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, RPGDIM_KEY.getRegistryName());

    public static void register(){
        System.out.println("Registering ModDimensionsfor " + RPGalvaroff.MOD_ID);
    }
}
