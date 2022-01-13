package dev.ashcorp.aetherflight.events;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.capabilities.CapabilityManager;
import dev.ashcorp.aetherflight.capabilities.AetherCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {

    @SubscribeEvent
    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            AetherCapabilityProvider provider = new AetherCapabilityProvider();
            event.addCapability(new ResourceLocation(AetherFlight.MODID, "aetherflight_data"), provider);
        }
    }

    @SubscribeEvent
    public static void playerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player) {
            player.getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
                boolean firstJoin = h.getFirstJoin();

                if(firstJoin) {
                    // If the player is joining for the first time set up the capability.
                    h.setAethergenLocation(BlockPos.of(0L));
                    h.setAethergenTier(1);
                    h.setFirstJoin(false);
                    h.setStoredAether(0);
                    h.setMaxAether(5000);
                    player.serializeNBT();
                }

            });
        }
    }
}
