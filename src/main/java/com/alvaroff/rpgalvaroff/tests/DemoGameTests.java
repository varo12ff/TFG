package com.alvaroff.rpgalvaroff.tests;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerClass;
import com.alvaroff.rpgalvaroff.capabilities.playerStats.PlayerStats;
import com.alvaroff.rpgalvaroff.client.KeyBinding;
import com.alvaroff.rpgalvaroff.client.gui.ManaBarOverlay;
import com.alvaroff.rpgalvaroff.client.gui.RpgGUI;
import com.alvaroff.rpgalvaroff.common.utils.DimensionUtils;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.KeyBindingC2SPacket;
import com.alvaroff.rpgalvaroff.networking.packet.PlayerStatsC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.event.KeyEvent;

@GameTestHolder(RPGalvaroff.MOD_ID)
public class DemoGameTests {
    @OnlyIn(Dist.CLIENT)
    @GameTest
    public static void openGUI(GameTestHelper helper) {
        helper.succeedWhen(() ->{
            ModMessages.sendToServer(new KeyBindingC2SPacket(0));
            Screen currentScreen = Minecraft.getInstance().screen;
            if(!(currentScreen instanceof RpgGUI))
                throw new GameTestAssertException("La interfaz no se ha abierto correctamente");
        });
    }

    @OnlyIn(Dist.CLIENT)
    @GameTest
    public static void syncAttributes(GameTestHelper helper) {
        helper.succeedWhen(() ->{
            PlayerStats warrior = new PlayerStats(1, 13, 5, 3, 3, 3, 1, 1, 3, PlayerClass.GUERRERO);
            PlayerStats magician = new PlayerStats(1, 7, 1, 4, 3, 3, 5, 5, 3, PlayerClass.MAGO);
            PlayerStats ninja = new PlayerStats(1, 10, 3, 5,3, 3, 2, 5, 1, PlayerClass.NINJA);
            PlayerStats priest = new PlayerStats(1, 11.5f, 1, 3,3, 3, 4, 1, 5, PlayerClass.CLERIGO);

            if(!(warrior.getPlayerClass().equals(PlayerClass.GUERRERO)) || !(magician.getPlayerClass().equals(PlayerClass.MAGO)) ||
                    !(ninja.getPlayerClass().equals(PlayerClass.NINJA)) || !(priest.getPlayerClass().equals(PlayerClass.CLERIGO)))
                throw new GameTestAssertException("No se han ajustado bien las clases.");

            warrior.addStrength();
            magician.addStrength();
            ninja.addStrength();
            priest.addStrength();

            if((warrior.getStrength() != 6) || (magician.getStrength() != 2) || (ninja.getStrength() != 4) || (priest.getStrength() != 2))
                throw new GameTestAssertException("No se han ajustado bien las estadísticas.");

        });
    }

    @OnlyIn(Dist.CLIENT)
    @GameTest
    public static void changeMana(GameTestHelper helper) {
        helper.succeedWhen(() ->{
            PlayerStats magician = new PlayerStats(1, 7, 1, 4, 3, 3, 5, 5, 3, PlayerClass.MAGO);
            magician.subtractMana(5.0f);
            ManaBarOverlay.updateMana(magician.getManaCant(), magician.getCurrentMana());
            ModMessages.sendToServer(new PlayerStatsC2SPacket(magician.getNBT()));

            if(ManaBarOverlay.getMaxMana() <= ManaBarOverlay.getCurrentMana() || !ManaBarOverlay.isDraw())
                throw new GameTestAssertException("El Overlay de Maná no se ha actualizado");
        });
    }
}
