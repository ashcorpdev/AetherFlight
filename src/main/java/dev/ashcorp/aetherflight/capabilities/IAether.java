package dev.ashcorp.aetherflight.capabilities;

import net.minecraft.core.BlockPos;

public interface IAether {

    int getAethergenTier();
    void setAethergenTier(int tier);

    BlockPos getAethergenLocation();
    void setAethergenLocation(BlockPos pos);

    boolean getFirstJoin();
    void setFirstJoin(boolean bool);

    int getStoredAether();
    void setStoredAether(int aether);

    int getMaxAether();
    void setMaxAether(int maxAether);

}
