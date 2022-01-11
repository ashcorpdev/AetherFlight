package dev.ashcorp.aetherflight.events;

import dev.ashcorp.aetherflight.capabilities.CapabilityAetherPlayer;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    @SubscribeEvent
    public static void interactEvent(PlayerInteractEvent event) {

        BlockPos pos = event.getPos();
        Player player = event.getPlayer();

        player.getCapability(CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY).ifPresent(capability -> {
            BlockPos aetherGenLocation = capability.getAethergenLocation();
            if (pos.equals(aetherGenLocation)) {
                if (player.isCrouching() && player.getMainHandItem().getItem() == Registration.REFINED_AETHER_CRYSTAL.get()) {
                    // If player is crouched and interacts with the aethergen, do something!
                    LOGGER.info("Aethergen interacted with using a crystal!");
                    event.setCanceled(true);
                }
            }
        });

    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        event.player.getCapability(CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {

            BlockPos aetherGenLocation = h.getAethergenLocation();
            BlockPos playerLocation = event.player.getOnPos();
            LivingEntity entity = event.player;
            int aetherGenTier = h.getAethergenTier();

            if(aetherGenLocation.asLong() == BlockPos.ZERO.asLong()) {
                // If there is no aetherGenLocation placed, just return.
                return;
            }
            double maxDistance = 32;

            boolean isWithinDistance = aetherGenLocation.closerThan(playerLocation, maxDistance);

            if(isWithinDistance) {
                if ((entity instanceof ServerPlayer) && !((ServerPlayer) entity).isCreative()) {
                    addTierEffects(entity, aetherGenTier);
                }
            } else {

                if ((entity instanceof ServerPlayer) && !((ServerPlayer) entity).isCreative()) {

                    // If the player isn't a server player, or they are in the creative mode.
                    ((ServerPlayer) entity).getAbilities().mayfly = false;
                    ((ServerPlayer) entity).getAbilities().flying = false;
                    ((ServerPlayer) entity).onUpdateAbilities();

                    // Adds slowfall in case they are high up and likely to die.
                    entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 10));
                }
            }
        });
    }

    private static void addTierEffects(LivingEntity player, int tier) {


        Level currentDimension = player.getLevel();
        Level overworld = player.getLevel().getServer().getLevel(Level.OVERWORLD);
        Level nether = player.getLevel().getServer().getLevel(Level.NETHER);
        Level end = player.getLevel().getServer().getLevel(Level.END);


        player.getCapability(CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY).ifPresent(capability -> {
            BlockEntity aetherGen = player.getLevel().getBlockEntity(capability.getAethergenLocation());
            int storedAether = capability.getStoredAether();
            int cost = 5;

            switch(tier) {
                case 1:


                    // Overworld only - limited ability to fly.

                    if(currentDimension.equals(overworld) && storedAether > 0) {

                        // If the player isn't a server player, or they are in the creative mode.
                        ((ServerPlayer) player).getAbilities().mayfly = true;
                        ((ServerPlayer) player).onUpdateAbilities();

                        // Subtract aether per tick to fly!
                        capability.setStoredAether(storedAether - cost);
                    }

                    break;
                case 2:

                    //Overworld and Nether - Overworld free within maxDistance, then costs Aether.

                    if(currentDimension.equals(overworld)) {

                        // If the player isn't a server player, or they are in the creative mode.
                        ((ServerPlayer) player).getAbilities().mayfly = true;
                        ((ServerPlayer) player).onUpdateAbilities();
                    } else if (currentDimension.equals(nether)) {

                        //Check aether amounts in aethergen - if aether > 0, then allow flight.

                    }

                    break;
                case 3:

                    // Overworld/Nether/End - Overworld completely free, Nether costs aether, End costs Aether at higher rate.



                    break;
                case 4:

                    // ALL DIMENSIONS - Completely free, aether instead used to provide speedboosts.

                    break;
                default:
                    break;

            }
        });

    }
}
