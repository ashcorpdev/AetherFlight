package dev.ashcorp.aetherflight.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static dev.ashcorp.aetherflight.setup.ModSetup.ITEM_GROUP;

public class AetherSiphonItem extends Item {

    private int tier = 0;
    private double storedAether = 0f;
    private double maxAether = 10f;

    public AetherSiphonItem(Properties pProperties) {
        super(pProperties);
        pProperties.setNoRepair();
        pProperties.tab(ITEM_GROUP);
        pProperties.rarity(Rarity.COMMON);
        pProperties.stacksTo(1);
    }

    public int getTier() {
        return this.tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public double getStoredAether() {
        return this.storedAether;
    }

    public void setStoredAether(double storedAether) {
        this.storedAether = storedAether;
    }

    public double getMaxAether() {
        return this.maxAether;
    }

    public void setMaxAether(double maxAether) {
        this.maxAether = maxAether;
    }
}
