package dev.ashcorp.aetherflight.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AetherCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private final AetherItemCapability capability = new AetherItemCapability();
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
        if (CapabilityManager.AETHER_ITEM_CAPABILITY == null) {
            return new CompoundTag();
        } else {
            CompoundTag tag = new CompoundTag();
            tag.putInt("aetherSiphonTier", capability.getAetherSiphonTier());
            tag.putInt("storedAether", capability.getStoredAether());
            return tag;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        if (CapabilityManager.AETHER_ITEM_CAPABILITY != null) {
            capability.setAetherSiphonTier(nbt.getInt("aetherSiphonTier"));
            capability.setStoredAether(nbt.getInt("storedAether"));
        }

    }
}
