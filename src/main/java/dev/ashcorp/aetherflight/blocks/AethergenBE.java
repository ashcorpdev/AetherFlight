package dev.ashcorp.aetherflight.blocks;

import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class AethergenBE extends BlockEntity {

    private ItemStackHandler itemHandler = createHandler();

    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private int counter;

    public AethergenBE(BlockPos pos, BlockState state) {
        super(Registration.AETHERGEN_BE.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }



    public void tickServer() {

        if (counter > 0) {
            counter--;

            if(counter <= 0) {
                // Do energy generation part.
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

        super.load(tag);
    }
    
    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inv", itemHandler.serializeNBT());
        tag.putInt("counter", counter);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == Registration.REFINED_AETHER_CRYSTAL.get();
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(stack.getItem() == Registration.REFINED_AETHER_CRYSTAL.get()) {
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
}
