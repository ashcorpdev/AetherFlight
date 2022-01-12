package dev.ashcorp.aetherflight.blocks;

import dev.ashcorp.aetherflight.capabilities.CapabilityManager;
import dev.ashcorp.aetherflight.lib.Helpers;
import dev.ashcorp.aetherflight.lib.TickingBlockEntity;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;

public class AethergenBE extends TickingBlockEntity {

    private static final Logger LOGGER = LogManager.getLogger();

    private ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private int counter;

    // Weak reference used here to help with caching data and to prevent memory leaks!
    private final WeakReference<Player> player;

    public AethergenBE(BlockPos pos, BlockState state) {
        super(Registration.AETHERGEN_BE.get(), pos, state);
        this.player = new WeakReference<>(Helpers.getPlayerFromUUID(getOwnerUUID()));
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }



    public void tickServer() {

        Player player_ = this.player.get();

        if (counter > 0) {
            counter--;

            if(counter <= 0) {
                // Do energy generation part.


                if(player_ != null) {
                    LOGGER.info(String.format("Processing aether fuel - current player: %s", player_.getDisplayName()));
                    player_.getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h ->{
                        LOGGER.info(String.format("Loaded player data. Current aether: %s", h.getStoredAether()));
                        int oldAether = h.getStoredAether();
                        int newAether = oldAether + 5;
                        h.setStoredAether(newAether);
                        LOGGER.info(String.format("Updated player aether from %s to %s", oldAether, newAether));
                    });
                } else {
                    LOGGER.info("Player object is invalid!");
                    LOGGER.info(String.format("Owner is set to: %s", Helpers.getPlayerFromUUID(getOwnerUUID())));
                }
            }
            setChanged();
        }

        // Burn the item up if the counter has reset.
        if (counter <= 0) {
            ItemStack stack = itemHandler.getStackInSlot(0);

            if(stack.getItem() == Registration.REFINED_AETHER_CRYSTAL.get()) {
                itemHandler.extractItem(0, 1, false);
                counter = 20;
                setChanged();
            }
        }

        BlockState blockState = level.getBlockState(worldPosition);
        if(blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0), Block.UPDATE_ALL);
        }
    }
    
    @Override
    public void load(CompoundTag tag) {

        if(tag.contains("inv")) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }

        counter = tag.getInt("counter");
        super.load(tag);
    }

    @NotNull
    @Override
    public CompoundTag save(@NotNull CompoundTag tagCompound) {
        return super.save(tagCompound);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.putInt("counter", counter);
        super.saveAdditional(tag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return super.getUpdateTag();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (stack.getItem() == Registration.REFINED_AETHER_CRYSTAL.get()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(stack.getItem() != Registration.REFINED_AETHER_CRYSTAL.get()) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }
}
