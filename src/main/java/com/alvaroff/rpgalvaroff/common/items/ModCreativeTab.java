package com.alvaroff.rpgalvaroff.common.items;

import com.alvaroff.rpgalvaroff.common.blocks.BlockInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab {
    public static final CreativeModeTab MDD_MISC_TAB = new CreativeModeTab("misc") {
        @Override
        public ItemStack makeIcon(){
            return new ItemStack(ItemInit.ESCAPE_STONE.get());
        }
    };

    public static final CreativeModeTab MDD_KEY_TAB = new CreativeModeTab("keys") {
        @Override
        public ItemStack makeIcon(){
            return new ItemStack(ItemInit.KEY.get());
        }
    };

    public static final CreativeModeTab MDD_BLOCK_TAB = new CreativeModeTab("block") {
        @Override
        public ItemStack makeIcon(){
            return new ItemStack(BlockInit.LOCK.get());
        }
    };

    public static final CreativeModeTab MDD_SCROLL_TAB = new CreativeModeTab("scroll") {
        @Override
        public ItemStack makeIcon(){
            return new ItemStack(ItemInit.HEAL_SCROLL.get());
        }
    };

    public static final CreativeModeTab MDD_FOOD_TAB = new CreativeModeTab("food") {
        @Override
        public ItemStack makeIcon(){
            return new ItemStack(ItemInit.MAGIC_BERRIES.get());
        }
    };
}
