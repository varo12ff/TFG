package com.alvaroff.rpgalvaroff.client.renderer.model;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.common.entities.custom.Scarecraft;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ScarecraftEntityModel<Type extends Scarecraft> extends EntityModel<Type> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RPGalvaroff.MOD_ID, "scarecraft"), "main");
    private final ModelPart left_arm_up;
    private final ModelPart right_arm_up;
    private final ModelPart body_down;
    private final ModelPart body_mid;
    private final ModelPart head;
    private final ModelPart right_arm_down;
    private final ModelPart left_arm_down;
    private final ModelPart body_up;

    public ScarecraftEntityModel(ModelPart root) {
        this.left_arm_up = root.getChild("left_arm_up");
        this.right_arm_up = root.getChild("right_arm_up");
        this.body_down = root.getChild("body_down");
        this.body_mid = root.getChild("body_mid");
        this.head = root.getChild("head");
        this.right_arm_down = root.getChild("right_arm_down");
        this.left_arm_down = root.getChild("left_arm_down");
        this.body_up = root.getChild("body_up");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition left_arm_up = partdefinition.addOrReplaceChild("left_arm_up", CubeListBuilder.create().texOffs(26, 32).addBox(3.0F, -34.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 50).addBox(4.0F, -28.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_arm_up = partdefinition.addOrReplaceChild("right_arm_up", CubeListBuilder.create().texOffs(24, 50).addBox(0.0F, 4.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(26, 32).addBox(-1.0F, -2.0F, -5.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -8.0F, 2.0F));

        PartDefinition body_down = partdefinition.addOrReplaceChild("body_down", CubeListBuilder.create().texOffs(15, 31).addBox(0.0F, -5.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 35).addBox(-1.0F, -5.0F, -1.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 18.0F, 0.0F));

        PartDefinition body_mid = partdefinition.addOrReplaceChild("body_mid", CubeListBuilder.create().texOffs(36, 46).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(15, 46).addBox(-2.0F, -6.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 47).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 29).addBox(-2.0F, -7.0F, -1.0F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -7.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 54).addBox(-4.0F, -42.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(15, 58).addBox(-1.0F, -40.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(-4.0F, -40.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_arm_down = partdefinition.addOrReplaceChild("right_arm_down", CubeListBuilder.create().texOffs(0, 48).addBox(-7.0F, -24.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_arm_down = partdefinition.addOrReplaceChild("left_arm_down", CubeListBuilder.create().texOffs(0, 48).addBox(5.0F, -24.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_up = partdefinition.addOrReplaceChild("body_up", CubeListBuilder.create().texOffs(8, 38).addBox(-3.0F, -34.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 26).addBox(-3.0F, -28.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -32.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -34.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        left_arm_up.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_arm_up.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body_down.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body_mid.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_arm_down.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_arm_down.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body_up.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Type type, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float idleAnimationProgress = (ageInTicks / 20.0F) % 3.0F;  // Ajuste para ralentizar la animación

        // Animación para left_arm_up
        float leftArmUpPos = Mth.sin(idleAnimationProgress / 3.0F * (float)Math.PI * 2.0F) * 0.5F + 0.5F;
        this.left_arm_up.y = 24.0F + leftArmUpPos * 1.0F;

        // Animación para right_arm_up
        float rightArmUpPos = Mth.sin(idleAnimationProgress / 3.0F * (float)Math.PI * 2.0F) * 0.5F + 0.5F;
        this.right_arm_up.y = -8.0F + rightArmUpPos * 1.0F;

        // Animación para left_arm_down
        float leftArmDownPos = Mth.sin(idleAnimationProgress / 3.0F * (float)Math.PI * 2.0F) * 0.5F + 0.5F;
        this.left_arm_down.y = 24.0F + leftArmDownPos * 1.0F;

        // Animación para right_arm_down
        float rightArmDownPos = Mth.sin(idleAnimationProgress / 3.0F * (float)Math.PI * 2.0F) * 0.5F + 0.5F;
        this.right_arm_down.y = 24.0F + rightArmDownPos * 1.0F;

    }

    private void bobArms(ModelPart rightArm, ModelPart leftArm, float ageInTicks) {
        rightArm.zRot += (Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
        rightArm.xRot += (Mth.sin(ageInTicks * 0.067F) * 0.05F);
        leftArm.zRot -= (Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
        leftArm.xRot -= (Mth.sin(ageInTicks * 0.067F) * 0.05F);
    }
}
