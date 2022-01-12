package dev.ashcorp.aetherflight.blocks;

import dev.ashcorp.aetherflight.lib.GenericBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class AethergenBlock extends Block implements EntityBlock {
    public static final String MESSAGE_AETHERGEN = "message.aethergen";
    public static final String SCREEN_AETHERFLIGHT_AETHERGEN = "screen.aetherflight.aethergen";

    private static final VoxelShape RENDER_SHAPE = Shapes.box(0.1,0.1,0.1,0.9,0.9,0.9);

    public AethergenBlock() {
        super(Properties.of(Material.METAL)
        .sound(SoundType.METAL)
        .strength(2.0f)
        .lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14: 0)
        .requiresCorrectToolForDrops()
        );
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return RENDER_SHAPE;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(new TranslatableComponent(MESSAGE_AETHERGEN, Integer.toString(0))
        .withStyle(ChatFormatting.BLUE));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AethergenBE(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if(level.isClientSide()) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if(t instanceof AethergenBE tile) {
                tile.tickServer();
            }
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    @Override
    public void setPlacedBy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);
        if (!world.isClientSide) {
            setOwner(world, pos, placer);
        }

        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof GenericBlockEntity) {
            GenericBlockEntity genericBlockEntity = (GenericBlockEntity) te;
            genericBlockEntity.onBlockPlacedBy(world, pos, state, placer, stack);
        }
    }

    protected void setOwner(Level level, BlockPos pos, LivingEntity entity) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof GenericBlockEntity && entity instanceof Player) {
            GenericBlockEntity genericTileEntity = (GenericBlockEntity) be;
            Player player = (Player) entity;
            genericTileEntity.setOwner(player);
        }
    }

    @Nullable
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if(!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof AethergenBE) {
                MenuProvider containerProvider = new MenuProvider() {

                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent(SCREEN_AETHERFLIGHT_AETHERGEN);
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new AethergenContainer(windowId, pos, playerInventory, playerEntity);
                    }
                };

                NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());

            } else {
                throw new IllegalStateException("Aethergen named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}
