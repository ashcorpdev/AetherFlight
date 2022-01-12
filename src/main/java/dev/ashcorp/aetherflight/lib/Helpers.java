package dev.ashcorp.aetherflight.lib;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.server.ServerStartedEvent;
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


    public static Player getPlayerFromUUID(UUID uuid) {

        return SERVER.getPlayerList().getPlayer(uuid);
    }

    public static void onServerStartedEvent(ServerStartedEvent event) {
        LOGGER.info("Server started - setting server instance...");
       SERVER = event.getServer();
       LOGGER.info("Server instance set!");
    }

}
