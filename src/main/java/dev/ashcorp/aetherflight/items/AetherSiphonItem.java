package dev.ashcorp.aetherflight.items;

import dev.ashcorp.aetherflight.lib.Helpers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class AetherSiphonItem extends Item {

    private static final Logger LOGGER = LogManager.getLogger();

    //TODO: Replace these static variables with an actual configuration file to adjust.
    private static int costTimer = 20;
    private int i;
    private int j;
    private static int gainTimer = 20;
    private static int flightCost = 5;
    private static int flightGain = 5;
    //INFO: 1000 max aether = 25s of flight;

    public AetherSiphonItem(Properties pProperties) {
        super(pProperties);
        pProperties.setNoRepair();
        pProperties.rarity(Rarity.COMMON);
        pProperties.stacksTo(1);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {


        if (pEntity instanceof Player player) {
            LOGGER.info(String.format("TICK! Can fly: %s, Is flying: %s, costTimer: %s, gainTimer: %s", player.getAbilities().mayfly, player.getAbilities().flying, i, j));

            if(pStack.getTag() != null) {

                if(!player.getAbilities().mayfly && pStack.getTag().getInt("storedAether") > flightCost) {
                    startFlying(player);
                }
                if(pStack.getTag().getInt("storedAether") <= flightCost && flightCost != 0) {
                    stopFlying(player);
                    // We give the player 5 aether to kickstart the recharging. Otherwise player cannot fly anymore.
                    j += flightGain;
                }

                if(player.getAbilities().mayfly && player.getAbilities().flying && pStack.getTag().getInt("storedAether") > flightCost) {
                    i++;
                    j = 0;
                    if(i >= costTimer) {
                        int oldAether = pStack.getTag().getInt("storedAether");
                        int newAether = oldAether - flightCost;
                        pStack.getTag().putInt("storedAether", newAether);
                        i = 0;
                    }
                }

                if(!player.getAbilities().flying) {
                    i = 0;
                    j++;
                    if(j >= gainTimer) {

                        int oldAether = pStack.getTag().getInt("storedAether");
                        int newAether = oldAether + flightGain;

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

        int tier = pStack.getOrCreateTag().getInt("tier");
        int storedAether = pStack.getOrCreateTag().getInt("storedAether");
        int maxAether = pStack.getOrCreateTag().getInt("maxAether");
        String owner = pStack.getOrCreateTag().getString("owner");
        super.onCraftedBy(pStack, pLevel, pPlayer);

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        //TODO: Convert all text to TranslatableComponent instead of TextComponent and use lang files.

        if (pStack.getTag() != null) {
            if (!pStack.getTag().getString("owner").isEmpty()) {

                UUID uuid = UUID.fromString(pStack.getTag().getString("owner"));
                Player player = Helpers.getPlayerFromUUID(uuid);

                pTooltipComponents.add(new TranslatableComponent("item.aetherflight.aether_siphon.tier", pStack.getTag().getInt("tier")).withStyle(ChatFormatting.AQUA));
                pTooltipComponents.add(new TranslatableComponent("item.aetherflight.aether_siphon.storedAether", pStack.getTag().getInt("storedAether"), pStack.getTag().getInt("maxAether")).withStyle(ChatFormatting.GREEN));
                //pTooltipComponents.add(new TextComponent(String.format("Stored Aether: %s / %s", pStack.getTag().getInt("storedAether"), pStack.getTag().getInt("maxAether"))));

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

    private static void startFlying(Player player) {
        if(!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    private static void stopFlying(Player player) {
        if(!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }
}

