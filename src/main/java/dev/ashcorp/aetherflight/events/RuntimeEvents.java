package dev.ashcorp.aetherflight.events;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RuntimeEvents {

    private static final Logger LOGGER = LogManager.getLogger();
    //INFO: 1000 max aether = 25s of flight;

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {

    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {


    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {

        ItemStack stack = event.getItemStack();

        if (stack.getTag() == null) {

            // Check if the item has no tag for some reason - this should resolve this.

            int storedAether = stack.getOrCreateTag().getInt("storedAether");
            int maxAether = stack.getOrCreateTag().getInt("maxAether");
            String owner = stack.getOrCreateTag().getString("owner");
        }

        String owner = stack.getTag().getString("owner");
        if (!owner.equals(event.getEntityLiving().getUUID().toString()) && !owner.isEmpty()) {
            LOGGER.info("Player attempted to use another player's aether siphon!");
            return;
        } else if (!owner.equals(event.getEntityLiving().getUUID().toString())) {
            // Set the owner.
            stack.getTag().putString("owner", event.getEntityLiving().getUUID().toString());
            stack.getTag().putInt("storedAether", 0);
            stack.getTag().putInt("maxAether", 1000);
        }
    }
}
