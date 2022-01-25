package dev.ashcorp.aetherflight.items;

import dev.ashcorp.aetherflight.lib.Helpers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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

    public AetherSiphonItem(Properties pProperties) {
        super(pProperties);
        pProperties.setNoRepair();
        pProperties.rarity(Rarity.COMMON);
        pProperties.stacksTo(1);
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

        if(pStack.getTag() != null) {
            if(!pStack.getTag().getString("owner").isEmpty()) {

                UUID uuid = UUID.fromString(pStack.getTag().getString("owner"));
                Player player = Helpers.getPlayerFromUUID(uuid);

                pTooltipComponents.add(new TextComponent(String.format("Tier: %s", pStack.getTag().getInt("tier"))));
                pTooltipComponents.add(new TextComponent(String.format("Stored Aether: %s / %s", pStack.getTag().getInt("storedAether"), pStack.getTag().getInt("maxAether"))));
                pTooltipComponents.add(new TextComponent(String.format("Owner: %s", player.getDisplayName().getString())));
            } else {
                pTooltipComponents.add(new TextComponent("Right-click to bind!"));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

    }


    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);

        // Filter out the existing item in creative menu and replace with the custom one?

        int i = 0;
        for (ItemStack item : pItems
             ) {
            i++;
            if (item.getItem() instanceof AetherSiphonItem && i < pItems.size()) {
                pItems.remove(i);
                int tier = stack.getOrCreateTag().getInt("tier");
                int storedAether = stack.getOrCreateTag().getInt("storedAether");
                int maxAether = stack.getOrCreateTag().getInt("maxAether");
                String owner = stack.getOrCreateTag().getString("owner");
                pItems.add(stack);
            }
        }

        super.fillItemCategory(pCategory, pItems);

    }

}
