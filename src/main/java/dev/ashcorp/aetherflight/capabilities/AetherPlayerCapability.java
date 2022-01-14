package dev.ashcorp.aetherflight.capabilities;

import net.minecraft.core.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AetherPlayerCapability implements IAether {

    private static final Logger LOGGER = LogManager.getLogger();

    private int aetherSiphonTier = 1;
    private Long aetherSiphonLocation = 0L;
    private boolean firstJoin = true;
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
    public BlockPos getAetherSiphonLocation() {
        return BlockPos.of(aetherSiphonLocation);
    }

    @Override
    public void setAetherSiphonLocation(BlockPos pos) {
        this.aetherSiphonLocation = pos.asLong();
    }

    @Override
    public boolean getFirstJoin() {
        return firstJoin;
    }

    @Override
    public void setFirstJoin(boolean bool) {
        this.firstJoin = bool;
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

    public void copyFrom(AetherPlayerCapability source) {
        LOGGER.info("Copying player data...");
        aetherSiphonLocation = source.aetherSiphonLocation;
        aetherSiphonTier = source.aetherSiphonTier;
        storedAether = source.storedAether;
        maxAether = source.maxAether;
        firstJoin = source.firstJoin;
    }
}
