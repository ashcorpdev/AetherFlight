package dev.ashcorp.aetherflight.lib;

import dev.ashcorp.aetherflight.AetherFlight;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AetherDataProvider implements ICapabilitySerializable<CompoundTag> {

    public static final ResourceLocation KEY = new ResourceLocation(AetherFlight.MODID, "aetherflight_data");
    public static Capability<AetherPlayerData> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {

    });

    final AetherPlayerData data = new AetherPlayerData();

    public AetherDataProvider(Player player) {
        data.setPlayer(player);
    }

    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) data.writeData();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        data.readData(nbt);
    }

    @NotNull
    @Override
    public <T>LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == CAPABILITY) {
            return (LazyOptional<T>) LazyOptional.of(() -> data);
        }
        return LazyOptional.empty();
    }
}
