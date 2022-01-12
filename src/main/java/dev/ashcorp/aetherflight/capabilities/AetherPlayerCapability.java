package dev.ashcorp.aetherflight.capabilities;

import net.minecraft.core.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AetherPlayerCapability implements IAether {

    private static final Logger LOGGER = LogManager.getLogger();

    private int aetherGenTier = 1;
    private Long aetherGenLocation = 0L;
    private boolean firstJoin = true;
    private int storedAether = 0;
    private int maxAether = 5000;

    @Override
    public int getAethergenTier() {
        return aetherGenTier;
    }

    @Override
    public void setAethergenTier(int tier) {
        this.aetherGenTier = tier;
    }

    @Override
    public BlockPos getAethergenLocation() {
        return BlockPos.of(aetherGenLocation);
    }

    @Override
    public void setAethergenLocation(BlockPos pos) {
        this.aetherGenLocation = pos.asLong();
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
        aetherGenLocation = source.aetherGenLocation;
        aetherGenTier = source.aetherGenTier;
        storedAether = source.storedAether;
        maxAether = source.maxAether;
        firstJoin = source.firstJoin;
    }
}
