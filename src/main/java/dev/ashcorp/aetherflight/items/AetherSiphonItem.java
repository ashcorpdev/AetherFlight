package dev.ashcorp.aetherflight.items;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.config.ConfigManager;
import dev.ashcorp.aetherflight.lib.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AetherSiphonItem extends Item {
    private int i;
    private int j;
    public int tier;
    private int multiplier = 1;

    public AetherSiphonItem(Properties pProperties) {
        super(pProperties);
        pProperties.setNoRepair();
        pProperties.rarity(Rarity.COMMON);
        pProperties.stacksTo(1);
        this.tier = 1;
    }

    public AetherSiphonItem(Properties pProperties, int tier) {
        super(pProperties);
        pProperties.setNoRepair();
        pProperties.rarity(Rarity.COMMON);
        pProperties.stacksTo(1);
        this.tier = tier;
    }

    private static void startFlying(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    private static void stopFlying(Player player) {
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

        if (pEntity instanceof Player player) {

            ResourceKey<Level> currentDimension = player.getLevel().dimension();
            int siphonCount = 0;


            for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
                if(player.getInventory().getItem(i).getItem() instanceof AetherSiphonItem) {
                    siphonCount++;
                }
            }

            // Player has multiple siphons, so shouldn't be allowed to fly.
            if (siphonCount > 1) {
                stopFlying(player);
                if (player instanceof LocalPlayer)
                    player.displayClientMessage(new TranslatableComponent("item.aetherflight.aether_siphon.conflict").withStyle(ChatFormatting.RED), true);
                return;
            }

            // This is garbage code, but I'm too tired to fix this right now.
            // TODO - Fix garbage code.
            switch (this.getTier()) {
                case 1:
                    if (player.getLevel().dimension() != Level.OVERWORLD) {
                        stopFlying(player);
                    }
                    break;
                case 2:
                    if (player.getLevel().dimension() != Level.OVERWORLD && player.getLevel().dimension() != Level.NETHER) {
                        stopFlying(player);
                    }
                    if (player.getLevel().dimension() == Level.OVERWORLD) {
                        multiplier = 0;
                    } else {
                        multiplier = 1;
                    }
                    break;
                case 3:
                    // Do tier 3 stuff.
                    multiplier = 0;
                    break;
                default:
                    break;

            }

            // Double checks that the item has the relevant tag.
            if (pStack.getTag() != null) {

                // If the player is allowed to fly and has enough aether, start flying.
                if ((!player.getAbilities().mayfly && pStack.getTag().getInt("storedAether") > ConfigManager.ServerConfig.flightCost.get())) {
                    startFlying(player);
                }


                // If the player is not allowed to fly, stop them from flying.
                if ((pStack.getTag().getInt("storedAether") <= ConfigManager.ServerConfig.flightCost.get() && ConfigManager.ServerConfig.flightCost.get() != 0)) {
                    stopFlying(player);

                    // We give the player extra aether to kickstart the recharging. Otherwise player cannot fly anymore.
                    j += ConfigManager.ServerConfig.flightGain.get();
                }

                // If the player is currently flying, start draining aether.
                if (player.getAbilities().mayfly && player.getAbilities().flying && pStack.getTag().getInt("storedAether") > ConfigManager.ServerConfig.flightCost.get()) {
                    i++;
                    j = 0;
                    if (i >= ConfigManager.ServerConfig.costTimer.get()) {
                        int oldAether = pStack.getTag().getInt("storedAether");
                        int newAether = oldAether - (ConfigManager.ServerConfig.flightCost.get() * multiplier);
                        pStack.getTag().putInt("storedAether", newAether);
                        i = 0;
                    }
                }

                // If the player isn't flying, start gaining aether.
                if (!player.getAbilities().flying) {
                    i = 0;
                    j++;
                    if (j >= ConfigManager.ServerConfig.gainTimer.get()) {

                        int oldAether = pStack.getTag().getInt("storedAether");
                        // Gain rate is multiplied by the item tier.
                        int newAether = oldAether + (ConfigManager.ServerConfig.flightGain.get() * this.getTier());

                        if (newAether > pStack.getTag().getInt("maxAether")) {
                            newAether = pStack.getTag().getInt("maxAether");
                        }
                        pStack.getTag().putInt("storedAether", newAether);
                        j = 0;
                    }
                }
            }
        }


        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {

        int storedAether = pStack.getOrCreateTag().getInt("storedAether");
        int maxAether = pStack.getOrCreateTag().getInt("maxAether");
        String owner = pStack.getOrCreateTag().getString("owner");
        super.onCraftedBy(pStack, pLevel, pPlayer);

    }

    // TODO: Display this on the GUI somehow.

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (pStack.getTag() != null) {
            if (!pStack.getTag().getString("owner").isEmpty()) {

                UUID uuid = UUID.fromString(pStack.getTag().getString("owner"));
                Player player = Helpers.getPlayerFromUUID(uuid);

                pTooltipComponents.add(new TranslatableComponent("item.aetherflight.aether_siphon.tier", this.tier).withStyle(ChatFormatting.AQUA));
                pTooltipComponents.add(new TranslatableComponent("item.aetherflight.aether_siphon.storedAether", pStack.getTag().getInt("storedAether"), pStack.getTag().getInt("maxAether")).withStyle(ChatFormatting.GREEN));

                pTooltipComponents.add(new TranslatableComponent("item.aetherflight.aether_siphon.owner", pLevel.getPlayerByUUID(UUID.fromString(pStack.getTag().getString("owner"))).getDisplayName().getString()).withStyle(ChatFormatting.GOLD));
            } else {
                pTooltipComponents.add(new TextComponent("Right-click to bind!"));
            }
        } else {
            pTooltipComponents.add(new TextComponent("Right-click to bind!"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {

        if (this.allowdedIn(pCategory)) {
            pItems.add(new ItemStack(this));
        }

    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        stopFlying(player);
        return super.onDroppedByPlayer(item, player);
    }
}

