package dev.ashcorp.aetherflight.lib;

import net.minecraft.core.BlockPos;

public class AetherPlayerCapability implements IAether {

    private int aetherGenTier = 1;
    private Long aetherGenLocation = 0L;
    private boolean firstJoin = true;

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
}
