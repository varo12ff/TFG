package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class HealSelfSkill extends PlayerSkills{
    private final int id = 1;
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/heal_self_skill.png");
    private final float manaCost = 3.0f;
    private static final String name = "Heal";

    public HealSelfSkill() {
        super();
    }

    @Override
    public String getName(){
        return name;
    }
    @Override
    public int getId() {
        return id;
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
        player.heal(3.0f);
        for (int i = 0; i < 100; i++) {
            double offsetX = (world.random.nextDouble() - 0.5) * 2.0;
            double offsetY = world.random.nextDouble() * 2.0;
            double offsetZ = (world.random.nextDouble() - 0.5) * 2.0;
            world.sendParticles(ParticleTypes.COMPOSTER,
                    player.getX() + offsetX,
                    player.getY() + offsetY,
                    player.getZ() + offsetZ,
                    1, 0.0,0.0, 0.0, 0.0);
        }
    }
}
