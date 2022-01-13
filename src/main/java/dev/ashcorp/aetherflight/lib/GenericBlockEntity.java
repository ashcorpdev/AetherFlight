package dev.ashcorp.aetherflight.lib;

import dev.ashcorp.aetherflight.capabilities.CapabilityManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class GenericBlockEntity extends BlockEntity {

    private String ownerName = "";
    private UUID ownerUUID = null;
    private WeakReference<Player> ownerRef;

    public GenericBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public void onBlockPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    }

    protected void loadInfo(CompoundTag tag) {
        if (tag.contains("Info")) {
            CompoundTag infoTag = tag.getCompound("Info");

            if (infoTag.contains("owner")) {
                ownerName = infoTag.getString("owner");
            }
            if (infoTag.hasUUID("ownerId")) {
                ownerUUID = infoTag.getUUID("ownerId");
            }
        }
    }

    protected CompoundTag getOrCreateInfo(CompoundTag tagCompound) {
        if (tagCompound.contains("Info")) {
            return tagCompound.getCompound("Info");
        }
        CompoundTag data = new CompoundTag();
        tagCompound.put("Info", data);
        return data;
    }

    public void load(@NotNull CompoundTag tagCompound) {
        loadCaps(tagCompound);
        loadInfo(tagCompound);
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag tagCompound) {
        super.save(tagCompound);
        saveAdditional(tagCompound);
        return tagCompound;
    }

    public void saveAdditional(@Nonnull CompoundTag tagCompound) {
        saveCaps(tagCompound);
        saveInfo(tagCompound);
    }



    protected void saveInfo(CompoundTag tagCompound) {
        CompoundTag infoTag = getOrCreateInfo(tagCompound);
        infoTag.putString("owner", ownerName);
        if (ownerUUID != null) {
            infoTag.putUUID("ownerId", ownerUUID);
        }
    }


    protected void saveCaps(CompoundTag tagCompound) {
        CompoundTag infoTag = getOrCreateInfo(tagCompound);
        getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
            infoTag.putInt("storedAether", h.getStoredAether());
            infoTag.putBoolean("firstJoin", h.getFirstJoin());
            infoTag.putLong("aetherGenLocation", h.getAethergenLocation().asLong());
            infoTag.putInt("aetherGenTier", h.getAethergenTier());
            infoTag.putInt("storedAether", h.getStoredAether());
        });
    }

    protected void loadCaps(CompoundTag tagCompound) {
        if (tagCompound.contains("Info")) {
            CompoundTag infoTag = tagCompound.getCompound("Info");
            getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
                h.setStoredAether(infoTag.getInt("storedAether"));
                h.setAethergenTier(infoTag.getInt("aetherGenTier"));
                h.setAethergenLocation(BlockPos.of(infoTag.getLong("aetherGenLocation")));
                h.setFirstJoin(infoTag.getBoolean("firstJoin"));
            });
        }
    }

    public void setOwner(Player player_) {

        if (ownerUUID != null) {
            // Already has an owner.
            return;
        }
        ownerUUID = player_.getGameProfile().getId();
        ownerName = player_.getName().getString() /* was getFormattedText() */;
        setChanged();

    }

    @Nullable
    public Player getCachedOwner() {
        Player owner = ownerRef == null ? null : ownerRef.get();
        if (owner == null) {
            owner = Helpers.getPlayerFromUUID(ownerUUID);
            if (owner != null) {
                ownerRef = new WeakReference<>(owner);
            }
        }
        return owner;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void clearOwner() {
        ownerUUID = null;
        ownerName = "";
        setChanged();
    }


}
