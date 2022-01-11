package dev.ashcorp.aetherflight.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AetherCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private final AetherPlayerCapability capability = new AetherPlayerCapability();
    private final LazyOptional<IAether> capabilityOptional = LazyOptional.of(() -> capability);

    public void invalidate() {
        capabilityOptional.invalidate();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return capabilityOptional.cast();
    }

    @Override
    public CompoundTag serializeNBT() {
        if (CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY == null) {
            return new CompoundTag();
        } else {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("firstJoin", capability.getFirstJoin());
            tag.putLong("aetherGenLocation", capability.getAethergenLocation().asLong());
            tag.putInt("aetherGenTier", capability.getAethergenTier());
            return tag;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        if (CapabilityAetherPlayer.AETHER_PLAYER_CAPABILITY != null) {
            capability.setAethergenTier(nbt.getInt("aetherGenTier"));
            capability.setAethergenLocation(BlockPos.of(nbt.getLong("aetherGenLocation")));
            capability.setFirstJoin(nbt.getBoolean("firstJoin"));
        }

    }
}
