package com.alvaroff.rpgalvaroff.common.entities;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.entities.custom.Scarecraft;
import com.alvaroff.rpgalvaroff.common.entities.custom.Warbler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RPGalvaroff.MOD_ID);

    public static final RegistryObject<EntityType<Scarecraft>> SCARECRAFT = ENTITIES.register("scarecraft", () -> EntityType.Builder.of(Scarecraft::new, MobCategory.MONSTER)
            .sized(1f, 2.6f)
            .build(new ResourceLocation(RPGalvaroff.MOD_ID, "scarecraft").toString()));

    public static final RegistryObject<EntityType<Warbler>> WARBLER = ENTITIES.register("warbler", () -> EntityType.Builder.of(Warbler::new, MobCategory.MONSTER)
            .sized(1f, 1f)
            .build(new ResourceLocation(RPGalvaroff.MOD_ID, "warbler").toString()));

    public static void register(IEventBus eventBus){

        ENTITIES.register(eventBus);
    }

}
