package dev.ashcorp.aetherflight.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static dev.ashcorp.aetherflight.setup.ModSetup.ITEM_GROUP;

public class AetherSiphonItem extends Item {

    private static final Logger LOGGER = LogManager.getLogger();

    private int tier = 0;
    private int storedAether = 0;
    private int maxAether = 5000;
    private UUID owner = null;

    public AetherSiphonItem(Properties pProperties) {
        super(pProperties);
        pProperties.setNoRepair();
        pProperties.tab(ITEM_GROUP);
        pProperties.rarity(Rarity.COMMON);
        pProperties.stacksTo(1);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (this.owner == null) {
            setOwner(pLivingEntity.getUUID());
        } else if (getOwner() != null && pLivingEntity.getUUID() != getOwner()) {
            // Entity trying to use the item isn't the item owner.
            LOGGER.info("User is not the item owner");
        } else {
            // Do the things.
            int stored = getStoredAether();
            setStoredAether(stored + 10);
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent(String.format("Tier: %s", tier)));
        pTooltipComponents.add(new TextComponent(String.format("Stored Aether: %s / %s", storedAether, maxAether)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

    }

    public int getTier() {
        return this.tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getStoredAether() {
        return this.storedAether;
    }

    public void setStoredAether(int storedAether) {
        this.storedAether = storedAether;
    }

    public int getMaxAether() {
        return this.maxAether;
    }

    public void setMaxAether(int maxAether) {
        this.maxAether = maxAether;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
