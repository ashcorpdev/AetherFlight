package dev.ashcorp.aetherflight.common.network;

import dev.ashcorp.aetherflight.AetherFlight;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHelper {

    public static SimpleChannel INSTANCE;

    private static int ID = 0;
    public static int nextPacket(){return ID++;}

    public static void registerMessages() {

        AetherFlight.LOGGER.info("Registering network packets.");

        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(AetherFlight.MODID, "network"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextPacket(),
                PacketHotkeyPressed.class,
                PacketHotkeyPressed::toBytes,
                PacketHotkeyPressed::new,
                PacketHotkeyPressed::handle);
    }
}
