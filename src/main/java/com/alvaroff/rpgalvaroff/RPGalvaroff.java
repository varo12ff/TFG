package com.alvaroff.rpgalvaroff;

import com.alvaroff.rpgalvaroff.client.KeyBinding;
import com.alvaroff.rpgalvaroff.client.gui.RpgGUI;
import com.alvaroff.rpgalvaroff.common.blocks.BlockInit;
import com.alvaroff.rpgalvaroff.common.entities.EntityInit;
import com.alvaroff.rpgalvaroff.common.items.ItemInit;
import com.alvaroff.rpgalvaroff.common.world.dimension.DimensionInit;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RPGalvaroff.MOD_ID)
public class RPGalvaroff
{

    public static final String MOD_ID = "rpgalvaroff";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public RPGalvaroff()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ItemInit.register(eventBus);
        BlockInit.register(eventBus);
        EntityInit.register(eventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        ModMessages.register();

    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT CLIENT");
        KeyBinding.registerKeyBindings();
    }



}
