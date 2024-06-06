package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)

public class ThunderSkill extends PlayerSkills{


    private final int id = 0;
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/thunder_skill.png");
    private static final String name = "Thunder";
    private final float manaCost = 5.0f;

    public ThunderSkill() {
        super();
    }
    public int getId() {
        return id;
    }

    @Override
    public String getName(){
        return name;
    }
    @Override
    public ResourceLocation getImage(){
        return icon;
    }

    @Override
    public float getManaCost(){
        return manaCost;
    }

    @Override
    public void launch(ServerPlayer player) {
        HitResult result = rayTrace(player, 5);

        if (result.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockResult = (BlockHitResult) result;

            if (player != null && player.level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel) player.level;
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (lightning != null) {
                    lightning.moveTo(blockResult.getBlockPos().getX(), blockResult.getBlockPos().getY(), blockResult.getBlockPos().getZ());
                    serverLevel.addFreshEntity(lightning);
                }
            }
        }

    }

    private static HitResult rayTrace(Player player, double range) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F).scale(range);
        Vec3 targetPosition = eyePosition.add(lookVec);
        return player.level.clip(new net.minecraft.world.level.ClipContext(eyePosition, targetPosition, net.minecraft.world.level.ClipContext.Block.OUTLINE, net.minecraft.world.level.ClipContext.Fluid.NONE, player));
    }
    @Override
    public void mostrar(){
        System.out.println("soy thunder");
    }

}
