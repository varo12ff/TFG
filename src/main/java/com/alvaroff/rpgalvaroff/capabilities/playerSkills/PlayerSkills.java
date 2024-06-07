package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerSkills {
    private static final List<PlayerSkills> SKILLS = new ArrayList<>();
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/thunder_skill.png");
    private static final String name = "";
    private int id = -1;
    public PlayerSkills(){

    }

    public String getName(){
        return name;
    }
    public void mostrar(){
        System.out.println("soy skill");
    }

    public void initSkillsVector(){
        ThunderSkill thunder = new ThunderSkill();
        HealSelfSkill heal = new HealSelfSkill();
        BerserkModeSkill berserk = new BerserkModeSkill();
        DashSkill dash = new DashSkill();

        SKILLS.clear();
        SKILLS.add(thunder);
        SKILLS.add(heal);
        SKILLS.add(berserk);
        SKILLS.add(dash);
    }

    public PlayerSkills getSkill(int id){
        if(id > SKILLS.size())
            return null;
        else
            return SKILLS.get(id);
    }

    public int getId(){
        return id;
    }
    public ResourceLocation getImage(){
        return icon;
    }

    public float getManaCost(){
        return -1.0f;
    }

    public void launch(ServerPlayer player) {
        Minecraft.getInstance().player.displayClientMessage(new TextComponent("No Skill"), true);
    }

}
