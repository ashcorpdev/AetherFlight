package dev.ashcorp.aetherflight.blocks;

import dev.ashcorp.aetherflight.capabilities.CapabilityManager;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class AethergenContainer extends AbstractContainerMenu {

    private static final Logger LOGGER = LogManager.getLogger();
    private BlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;

    public AethergenContainer(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
        super(Registration.AETHERGEN_CONTAINER.get(), windowId);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 64, 24));

            });
            layoutPlayerInventorySlots(10, 70);
            trackAether(player);
        }

    }

    private void trackAether(Player player) {

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy(player);
            }

            @Override
            public void set(int pValue) {
                player.getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
                    h.setStoredAether(pValue);
                });
            }
        });

    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(),
                blockEntity.getBlockPos()), playerEntity, Registration.AETHERGEN.get());
    }

    public int getEnergy(Player player) {
        AtomicInteger storedAether = new AtomicInteger();
        player.getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
              storedAether.set(h.getStoredAether());
        });
        return storedAether.get();
    }

    public int getEnergy() {
        AtomicInteger storedAether = new AtomicInteger();
        playerEntity.getCapability(CapabilityManager.AETHER_PLAYER_CAPABILITY).ifPresent(h -> {
            storedAether.set(h.getStoredAether());
        });
        return storedAether.intValue();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if(index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if(stack.getItem() == Registration.REFINED_AETHER_CRYSTAL.get()) {
                    if(!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if(stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemstack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {

        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

}
