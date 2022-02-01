package dev.ashcorp.aetherflight.client.keybindings;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.common.network.NetworkHelper;
import dev.ashcorp.aetherflight.common.network.PacketHotkeyPressed;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AetherFlight.MODID)
public class KeyHelper {


    private static final Minecraft MINECRAFT = Minecraft.getInstance();

    public static void checkPressedKey(int key) {
        if(key == AetherKeyBindings.BOOST_FLIGHT.getKey().getValue()) {
                sendHotkeyPacket(PacketHotkeyPressed.Key.BOOST_FLIGHT);
        }
    }


    @SubscribeEvent
    public static void keyEvent(final InputEvent.KeyInputEvent event) {
        if(MINECRAFT.player == null || MINECRAFT.screen != null || event.getAction() != 1) {
            return;
        }
        checkPressedKey(event.getKey());

    }

    public static void sendUpdatePacket(){
    }

    public static void sendHotkeyPacket(PacketHotkeyPressed.Key key){
        NetworkHelper.INSTANCE.sendToServer(new PacketHotkeyPressed(key));
    }

}
