package com.alvaroff.rpgalvaroff.event;

import com.alvaroff.rpgalvaroff.RPGalvaroff;
import com.alvaroff.rpgalvaroff.client.KeyBinding;
import com.alvaroff.rpgalvaroff.networking.ModMessages;
import com.alvaroff.rpgalvaroff.networking.packet.KeyBindingC2SPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGalvaroff.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if(KeyBinding.OPEN_INITIAL_GUI.isDown()) {
            ModMessages.sendToServer(new KeyBindingC2SPacket());

        }
    }


}
