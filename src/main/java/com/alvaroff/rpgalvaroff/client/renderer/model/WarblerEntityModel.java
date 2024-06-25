package com.alvaroff.rpgalvaroff.client.renderer.model;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.entities.custom.Warbler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class WarblerEntityModel<Type extends Warbler> extends EntityModel<Type> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RPGalvaroff.MOD_ID, "warbler"), "main");
    private final ModelPart head;
    private final ModelPart wings;
    private final ModelPart body;

    public WarblerEntityModel(ModelPart root) {
        this.head = root.getChild("head");
        this.wings = root.getChild("wings");
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 44).addBox(-5.0F, -24.0F, -4.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition wings = partdefinition.addOrReplaceChild("wings", CubeListBuilder.create().texOffs(32, 56).addBox(5.0F, -19.0F, -4.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 46).addBox(-17.0F, -19.0F, -4.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = wings.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 46).addBox(-1.0F, 0.0F, -4.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = wings.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 56).addBox(-1.0F, 0.0F, -4.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -25.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(10, 28).addBox(-4.0F, -23.0F, 3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(34, 24).addBox(-3.0F, -22.0F, 10.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 36).addBox(-2.0F, -21.0F, 16.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 39).addBox(-1.0F, -20.0F, 20.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 38).addBox(-1.0F, -19.0F, 22.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(29, 38).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -19.0F, 23.0F, 0.0F, 0.0F, 1.5533F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        wings.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Type type, float v, float v1, float v2, float v3, float v4) {

    }
}
