package com.alvaroff.rpgalvaroff.capabilities.playerSkills;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayerSkills {
    private static final List<PlayerSkills> SKILLS = new ArrayList<>();
    private static final ResourceLocation icon = new ResourceLocation(RPGalvaroff.MOD_ID,"textures/gui/skill_hud/thunder_skill.png");

    public PlayerSkills(){

    }
    public void mostrar(){
        System.out.println("soy skill");
    }

    public void initSkillsVector(){
        ThunderSkill thunder = new ThunderSkill();
        SKILLS.clear();
        SKILLS.add(thunder);
    }

    public PlayerSkills getSkill(int id){
        if(id > SKILLS.size())
            return null;
        else
            return SKILLS.get(id);
    }

    public ResourceLocation getImage(){
        return icon;
    }

    public void launch(TickEvent.ClientTickEvent event) {
        Minecraft.getInstance().player.displayClientMessage(new TextComponent("No Skill"), true);
    }

}
