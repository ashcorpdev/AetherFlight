package dev.ashcorp.aetherflight.capabilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AetherItemCapability implements IAether {

    private static final Logger LOGGER = LogManager.getLogger();

    private int aetherSiphonTier = 1;
    private int storedAether = 0;
    private int maxAether = 5000;

    @Override
    public int getAetherSiphonTier() {
        return aetherSiphonTier;
    }

    @Override
    public void setAetherSiphonTier(int tier) {
        this.aetherSiphonTier = tier;
    }

    @Override
    public int getStoredAether() {
        return this.storedAether;
    }

    @Override
    public void setStoredAether(int aether) {
        this.storedAether = aether;
    }

    @Override
    public int getMaxAether() {
        return this.maxAether;
    }

    @Override
    public void setMaxAether(int maxAether) {
        this.maxAether = maxAether;
    }

    public void copyFrom(AetherItemCapability source) {
        LOGGER.info("Copying item data...");
        aetherSiphonTier = source.aetherSiphonTier;
        storedAether = source.storedAether;
        maxAether = source.maxAether;
    }
}
