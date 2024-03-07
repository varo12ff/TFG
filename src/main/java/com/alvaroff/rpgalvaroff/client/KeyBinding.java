package com.alvaroff.rpgalvaroff.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.Input;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_RPGALVAROFF = "key.category.rpgalvaroff.rpgkey";
    public static final String KEY_OPEN_INITIAL_GUI = "key.rpgalvaroff.open_initial_gui";
    public static final KeyMapping OPEN_INITIAL_GUI = new KeyMapping(KEY_OPEN_INITIAL_GUI, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_RPGALVAROFF);

    public static void registerKeyBindings(){
        ClientRegistry.registerKeyBinding(OPEN_INITIAL_GUI);
    }
}
