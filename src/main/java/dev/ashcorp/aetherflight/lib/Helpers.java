package dev.ashcorp.aetherflight.lib;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class Helpers {

    private static final Logger LOGGER = LogManager.getLogger();

    private static MinecraftServer SERVER;

    public static MinecraftServer getServer() {
        return SERVER;
    }


    public static ServerPlayer getPlayerFromUUID(UUID uuid) {

        return SERVER.getPlayerList().getPlayer(uuid);
    }

    @SubscribeEvent
    public static void onServerStartingEvent(ServerStartingEvent event) {
        SERVER = event.getServer();
    }

}
