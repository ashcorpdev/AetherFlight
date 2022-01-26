package dev.ashcorp.aetherflight.events;

import dev.ashcorp.aetherflight.items.AetherSiphonItem;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
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
    private static int checkFlightAllowed = 20;
    private static boolean isFlying = false;

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {

    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {

        if (checkFlightAllowed <= 0) {
            isFlying = event.player.getAbilities().flying;
        }


        Player player = event.player;
        ItemStack siphon = new ItemStack(Registration.AETHER_SIPHON.get().asItem());
        int itemSlot;
        ItemStack stack;
        LOGGER.info(String.format("Is flying? %b", isFlying));
        LOGGER.info(String.format("Flight counter: %s", checkFlightAllowed));

        for (ItemStack item : event.player.getInventory().items) {

            if(item.getItem() instanceof AetherSiphonItem) {

                itemSlot = player.getInventory().findSlotMatchingItem(item);
                stack = player.getInventory().getItem(itemSlot);

                // Player has an aether siphon.
                int amount = player.getInventory().countItem(siphon.getItem());

                if(amount > 1) {
                    // Player has more than one aether siphon.
                    LOGGER.info("Player has more than one aether siphon. Not working.");
                    player.getAbilities().mayfly = false;
                    player.onUpdateAbilities();
                } else {

                    // Allow the player to fly.
                    if (checkFlightAllowed <= 0) {

                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();

                        checkFlightAllowed = 20;

                    } else {
                        checkFlightAllowed--;
                    }

                    if (isFlying) {
                        // If the player is in the air, we don't want them gaining aether.

                        if (stack.getTag() != null) {
                            int storedAether = player.getInventory().getItem(itemSlot).getTag().getInt("storedAether");
                            int tier = player.getInventory().getItem(itemSlot).getTag().getInt("tier");

                            int newAether = storedAether - 10;
                            stack.getTag().putInt("storedAether", newAether);
                            LOGGER.info(String.format("Stored Aether: %s", player.getInventory().getItem(itemSlot).getTag().getInt("storedAether")));

                        }
                    } else {
                        if (stack.getTag() != null) {
                            int storedAether = player.getInventory().getItem(itemSlot).getTag().getInt("storedAether");
                            int tier = player.getInventory().getItem(itemSlot).getTag().getInt("tier");

                            int newAether = storedAether + 20;
                            if(checkFlightAllowed <= 0) {
                                stack.getTag().putInt("storedAether", newAether);
                            }
                            LOGGER.info(String.format("Stored Aether: %s", player.getInventory().getItem(itemSlot).getTag().getInt("storedAether")));
                        }
                    }


                }
            }


        }

    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {

        ItemStack stack = event.getItemStack();

        LOGGER.info("InteractEvent Fired");

            if(stack.getTag() == null) {

                // Check if the item has no tag for some reason - this should resolve this.

                int tier = stack.getOrCreateTag().getInt("tier");
                int storedAether = stack.getOrCreateTag().getInt("storedAether");
                int maxAether = stack.getOrCreateTag().getInt("maxAether");
                String owner = stack.getOrCreateTag().getString("owner");
            }

            String owner = stack.getTag().getString("owner");
            if(!owner.equals(event.getEntityLiving().getUUID().toString()) && !owner.isEmpty()) {
                LOGGER.info("Not the owner!");
                return;
            } else if(!owner.equals(event.getEntityLiving().getUUID().toString())) {
                // Set the owner.
                stack.getTag().putString("owner", event.getEntityLiving().getUUID().toString());
                stack.getTag().putInt("tier", 1);
                stack.getTag().putInt("storedAether", 0);
                stack.getTag().putInt("maxAether", 5000);
                LOGGER.info(String.format("Stack Owner Updated: %s", owner));
            }
            LOGGER.info("Interaction finished");
    }
}
