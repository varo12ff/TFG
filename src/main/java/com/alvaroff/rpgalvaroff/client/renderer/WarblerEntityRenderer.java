package com.alvaroff.rpgalvaroff.client.renderer;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.client.renderer.model.ScarecraftEntityModel;
import com.alvaroff.rpgalvaroff.client.renderer.model.WarblerEntityModel;
import com.alvaroff.rpgalvaroff.common.entities.custom.Scarecraft;
import com.alvaroff.rpgalvaroff.common.entities.custom.Warbler;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WarblerEntityRenderer<Type extends Warbler> extends MobRenderer<Type, WarblerEntityModel<Type>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(RPGalvaroff.MOD_ID, "textures/entities/warbler.png");

    public WarblerEntityRenderer(Context context){
        super(context, new WarblerEntityModel<>(context.bakeLayer(WarblerEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Type type) {
        return TEXTURE;
    }


}
