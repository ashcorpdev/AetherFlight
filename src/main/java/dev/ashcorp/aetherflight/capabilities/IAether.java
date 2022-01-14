package dev.ashcorp.aetherflight.capabilities;

import net.minecraft.core.BlockPos;

public interface IAether {

    int getAetherSiphonTier();
    void setAetherSiphonTier(int tier);

    BlockPos getAetherSiphonLocation();
    void setAetherSiphonLocation(BlockPos pos);

    boolean getFirstJoin();
    void setFirstJoin(boolean bool);

    int getStoredAether();
    void setStoredAether(int aether);

    int getMaxAether();
    void setMaxAether(int maxAether);

}
