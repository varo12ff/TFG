package com.alvaroff.rpgalvaroff.common.items;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.items.custom.Debugger;
import com.alvaroff.rpgalvaroff.common.items.custom.EndDungeonOrbD;
import com.alvaroff.rpgalvaroff.common.items.custom.Escape_stone;
import com.alvaroff.rpgalvaroff.common.items.custom.keys.Key;
import com.alvaroff.rpgalvaroff.common.items.custom.keys.Key_D;
import com.alvaroff.rpgalvaroff.common.items.custom.scrolls.BerserkScroll;
import com.alvaroff.rpgalvaroff.common.items.custom.scrolls.DashScroll;
import com.alvaroff.rpgalvaroff.common.items.custom.scrolls.HealScroll;
import com.alvaroff.rpgalvaroff.common.items.custom.scrolls.ThunderScroll;
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
                    .tab(ModCreativeTab.MDD_KEY_TAB)
                    .rarity(Rarity.COMMON)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> KEY_D = ITEMS.register("key_d", () -> new Key_D(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_KEY_TAB)
                    .rarity(Rarity.COMMON)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> ESCAPE_STONE = ITEMS.register("escape_stone", () -> new Escape_stone(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_MISC_TAB)
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(16)
    ));

    public static final RegistryObject<Item> END_DUNGEON_ORB_D = ITEMS.register("end_dungeon_orb_d", () -> new EndDungeonOrbD(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_MISC_TAB)
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> DEBUGGER = ITEMS.register("debugger", () -> new Debugger(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_MISC_TAB)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> BERSERK_SCROLL = ITEMS.register("berserk_scroll", () -> new BerserkScroll(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_SCROLL_TAB)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> DASH_SCROLL = ITEMS.register("dash_scroll", () -> new DashScroll(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_SCROLL_TAB)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> THUNDER_SCROLL = ITEMS.register("thunder_scroll", () -> new ThunderScroll(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_SCROLL_TAB)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
    ));

    public static final RegistryObject<Item> HEAL_SCROLL = ITEMS.register("heal_scroll", () -> new HealScroll(
            new Item.Properties()
                    .tab(ModCreativeTab.MDD_SCROLL_TAB)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
    ));
    public static void register(IEventBus eventBus){
        ITEMS.register((eventBus));
    }
}
