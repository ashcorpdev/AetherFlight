package dev.ashcorp.aetherflight.events;

import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {

    }

    @SubscribeEvent
    public static void interactEvent(PlayerInteractEvent event) {

    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        ItemStack siphon = Registration.AETHER_SIPHON.get().getDefaultInstance();

        if(player.getInventory().contains(siphon)) {
            // Player has an aether siphon.
            int amount = player.getInventory().countItem(siphon.getItem());

            if(amount > 1) {
                // Player has more than one aether siphon.
                LOGGER.info("Player has more than one aether siphon. Not working.");
            } else {
                // Do effects on player.
            }
        }

    }

    private static void addTierEffects(LivingEntity player, int tier) {
}
}
