package dev.ashcorp.aetherflight.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TickingBlockEntity extends GenericBlockEntity {

    public TickingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tick() {
        if (level != null) {
            if (level.isClientSide()) {
                tickClient();
            } else {
                tickServer();
            }
        }
    }

    protected void tickServer() {

    }

    protected void tickClient() {

    }
}
