package com.alvaroff.rpgalvaroff.common.items.custom;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.items.custom.keys.Key;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RPGalvaroff.MOD_ID);

    public static final RegistryObject<Item> KEY = ITEMS.register("key", () -> new Key(
            new Item.Properties()
                    .tab(CreativeModeTab.TAB_MISC)
                    .rarity(Rarity.COMMON)
                    .stacksTo(1)
    ));
    public static void register(IEventBus eventBus){
        ITEMS.register((eventBus));
    }
}
