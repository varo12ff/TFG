package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class DashSkill extends PlayerSkills{
    private final int id = 3;
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/dash_skill.png");
    private final float manaCost = 2.0f;
    private static final String name = "Dash";

    public DashSkill() {
        super();
    }
    @Override
    public String getName(){
        return name;
    }
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
        Vec3 lookVec = player.getLookAngle();
        Vec3 motionVec = new Vec3(lookVec.x, 0, lookVec.z).normalize().scale(3);
        player.setDeltaMovement(motionVec);
        player.hurtMarked = true;
    }
}
