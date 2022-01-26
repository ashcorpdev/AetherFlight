package dev.ashcorp.aetherflight.events;

import dev.ashcorp.aetherflight.items.AetherSiphonItem;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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
    private static int flightCheckTimer = 20;
    private static boolean isFlying = false;
    private static boolean canFly = false;
    //INFO: 1000 max aether = 25s of flight;

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {

    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {

        Player player = event.player;

        if (flightCheckTimer <= 0) {
            isFlying = event.player.getAbilities().flying;
        } else {
            flightCheckTimer--;
        }

        if (event.player instanceof ServerPlayer && !event.player.isCreative()) {

            ItemStack siphon = new ItemStack(Registration.AETHER_SIPHON.get().asItem());
            int itemSlot;
            ItemStack stack;

            for (ItemStack item : event.player.getInventory().items) {

                if (item.getItem() instanceof AetherSiphonItem) {

                    // If the player has the item in their inventory, find it.

                    itemSlot = player.getInventory().findSlotMatchingItem(item);
                    stack = player.getInventory().getItem(itemSlot);

                    // Player has an aether siphon.
                    int amount = player.getInventory().countItem(siphon.getItem());

                    if (amount > 1) {
                        // Player has more than one aether siphon.
                        LOGGER.info("Player has more than one aether siphon. Not working.");
                        player.getAbilities().mayfly = false;
                        player.onUpdateAbilities();
                        canFly = false;

                    } else if (stack.getTag() != null) {

                        // Has aether, can fly.
                        if(stack.getTag().getInt("storedAether") > 0) {
                            canFly = true;
                            stack.getTag().putInt("storedAether", stack.getTag().getInt("storedAether") - 1);

                        } else if (stack.getTag().getInt("storedAether") <= 0){
                            // Has negative aether.
                            //stack.getTag().putInt("storedAether", 0);
                            canFly = false;
                        }
                    }

                    if(stack.getTag() != null) {
                        if (!isFlying && stack.getTag().getInt("storedAether") <= stack.getTag().getInt("maxAether")) {


                                stack.getTag().putInt("storedAether", stack.getTag().getInt("storedAether") + 2);

                        } else if(!isFlying && stack.getTag().getInt("storedAether") >= stack.getTag().getInt("maxAether")) {
                            stack.getTag().putInt("storedAether", stack.getTag().getInt("maxAether"));
                        }


                        if(stack.getTag().getInt("storedAether") <= 0 && isFlying) {
                            canFly = false;
                            player.getAbilities().flying = false;
                            player.onUpdateAbilities();
                        }

                        player.getAbilities().mayfly = canFly;
                        player.onUpdateAbilities();
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
                stack.getTag().putInt("maxAether", 1000);
                LOGGER.info(String.format("Stack Owner Updated: %s", owner));
            }
            LOGGER.info("Interaction finished");
    }
}
