package dev.ashcorp.aetherflight.events;

import dev.ashcorp.aetherflight.lib.CapabilityAetherPlayer;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RuntimeEvents {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void breakEvent(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            BlockPos pos = event.getPos();
            BlockState state = event.getState();

            if(state.getBlock() == Registration.AETHERGEN.get()) {
                player.getCapability(CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY).ifPresent(capability -> {

                        BlockPos storedPos = capability.getAethergenLocation();
                        if(pos.equals(storedPos)) {
                            // This means the block is the users' aethergen.
                            capability.setAethergenLocation(BlockPos.of(0L));
                        } else {
                            LOGGER.error("Detected Aethergen break, but didn't match position!");
                            LOGGER.error(String.format("Expected:  %s | Actual: %s", pos, storedPos));
                        }
                });
            }

        }
    }

    @SubscribeEvent
    public static void placeEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            BlockPos pos = event.getPos();
            BlockState state = event.getPlacedBlock();

            if(state.getBlock() == Registration.AETHERGEN.get()) {
                player.getCapability(CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY).ifPresent(capability -> {
                    if(capability.getAethergenLocation().asLong() == 0L) {
                        capability.setAethergenLocation(pos);
                    } else {
                        LOGGER.info("Detected Aethergen place when Aethergen already exists!");
                        event.setCanceled(true);
                    }
                });
            }

        }
    }
}
