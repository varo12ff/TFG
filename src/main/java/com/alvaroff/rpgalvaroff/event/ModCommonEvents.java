package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.entities.EntityInit;
import com.alvaroff.rpgalvaroff.common.entities.custom.Scarecraft;
import com.alvaroff.rpgalvaroff.common.entities.custom.Warbler;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommonEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.SCARECRAFT.get(), Scarecraft.setAttributes());
        event.put(EntityInit.WARBLER.get(), Warbler.setAttributes());
    }

}
