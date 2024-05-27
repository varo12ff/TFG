package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.client.renderer.ScarecraftEntityRenderer;
import com.alvaroff.rpgalvaroff.client.renderer.WarblerEntityRenderer;
import com.alvaroff.rpgalvaroff.client.renderer.model.ScarecraftEntityModel;
import com.alvaroff.rpgalvaroff.client.renderer.model.WarblerEntityModel;
import com.alvaroff.rpgalvaroff.common.entities.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ScarecraftEntityModel.LAYER_LOCATION, ScarecraftEntityModel::createBodyLayer);
        event.registerLayerDefinition(WarblerEntityModel.LAYER_LOCATION, WarblerEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.SCARECRAFT.get(), ScarecraftEntityRenderer::new);
        event.registerEntityRenderer(EntityInit.WARBLER.get(), WarblerEntityRenderer::new);
    }


}
