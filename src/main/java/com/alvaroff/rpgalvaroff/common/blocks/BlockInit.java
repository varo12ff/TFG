package com.alvaroff.rpgalvaroff.common.blocks;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.items.ItemInit;
import com.alvaroff.rpgalvaroff.common.items.ModCreativeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RPGalvaroff.MOD_ID);

    public static final RegistryObject<Block> LOCK = registerBlock("lock",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f)
            ), ModCreativeTab.MDD_BLOCK_TAB);

    public static final RegistryObject<Block> UNLOCK_NEW_ROOM_BLOCK = registerBlock("unlock_new_room",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(9f)
            ), ModCreativeTab.MDD_BLOCK_TAB);
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn,tab);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab){
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()
                        .tab(tab)
        ));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register((eventBus));
    }
}
