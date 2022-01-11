package dev.ashcorp.aetherflight.lib;

import net.minecraft.core.BlockPos;

public interface IAether {

    int getAethergenTier();
    void setAethergenTier(int tier);

    BlockPos getAethergenLocation();
    void setAethergenLocation(BlockPos pos);

    boolean getFirstJoin();
    void setFirstJoin(boolean bool);

}
