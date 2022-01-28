package dev.ashcorp.aetherflight.lib;

import dev.ashcorp.aetherflight.AetherFlight;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import static dev.ashcorp.aetherflight.AetherFlight.LOGGER;

@Mod.EventBusSubscriber
public class ConfigManager {

    public static class ClientConfig {}

    public static class CommonConfig {}

    public static class ServerConfig {

        public static ForgeConfigSpec.IntValue flightCost; // How much aether is drained (per tick)
        public static ForgeConfigSpec.IntValue costTimer; // How often is aether drained (per tick)

        public static ForgeConfigSpec.IntValue flightGain; // How much aether is gained (per tick)
        public static ForgeConfigSpec.IntValue gainTimer; // How often is aether gained (per tick)


        ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Aether Flight configuration options")
                    .push("General");

            flightCost = builder.comment("Defines how much aether is drained per tick. Use 0 to disable cost.")
                    .defineInRange("flightCost", 20, 0, 2147483647);

            costTimer = builder.comment("Defines how often aether is drained from the player per tick. Recommended to keep at minimum of 100.")
                    .defineInRange("costTimer", 100, 1, 2147483647);

            flightGain = builder.comment("Defines how much aether is gained per tick. Use 0 to disable cost.")
                    .defineInRange("flightCost", 20, 0, 2147483647);

            gainTimer = builder.comment("Defines how often aether is gained by the player per tick. Recommended to keep at minimum of 100.")
                    .defineInRange("gainTimer", 100, 1, 2147483647);

            builder.pop();

        }
    }

    public static final ForgeConfigSpec serverForgeSpec;
    public static final ServerConfig SERVER_CONFIG;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> serverForgeSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        serverForgeSpec = serverForgeSpecPair.getRight();
        SERVER_CONFIG = serverForgeSpecPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        LOGGER.debug("Loaded the aether flight configuration file! {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
        LOGGER.fatal("Aether flight configuration changed!");
    }
}
