package com.alvaroff.rpgalvaroff.client.renderer;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.client.renderer.model.ScarecraftEntityModel;
import com.alvaroff.rpgalvaroff.common.entities.custom.Scarecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScarecraftEntityRenderer<Type extends Scarecraft> extends MobRenderer<Type, ScarecraftEntityModel<Type>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/entities/scarecraft.png");

    public ScarecraftEntityRenderer(Context context){
        super(context, new ScarecraftEntityModel<>(context.bakeLayer(ScarecraftEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Type type) {
        return TEXTURE;
    }


}
