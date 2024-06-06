package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BerserkModeSkill extends PlayerSkills{
    private final int id = 2;
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/berserk_skill.png");
    private final float manaCost = 10.0f;
    private static final String name = "Berserk Unlock";

    public BerserkModeSkill() {
        super();
    }
    @Override
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
        ServerLevel world = (ServerLevel) player.level;

        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 1, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 2, false, false));

        for (int i = 0; i < 100; i++) {
            double offsetX = (world.random.nextDouble() - 0.5) * 2.0;
            double offsetY = world.random.nextDouble() * 2.0;
            double offsetZ = (world.random.nextDouble() - 0.5) * 2.0;
            world.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                    player.getX() + offsetX,
                    player.getY() + offsetY,
                    player.getZ() + offsetZ,
                    1, 0.0,0.0, 0.0, 0.0);
        }
    }
}
