package dev.ashcorp.aetherflight.setup;

import dev.ashcorp.aetherflight.blocks.AethergenBlock;
import dev.ashcorp.aetherflight.blocks.AethergenBE;
import dev.ashcorp.aetherflight.blocks.AethergenContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.ashcorp.aetherflight.AetherFlight.MODID;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);


    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // This allows the blocks and items we've created to be registered when forge starts loading.
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops();

    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);

    public static final RegistryObject<Block> AETHER_ORE_OVERWORLD = BLOCKS.register("aether_ore_overworld", () -> new Block(BLOCK_PROPERTIES));
    public static final RegistryObject<Item> AETHER_ORE_OVERWORLD_ITEM = fromBlock(AETHER_ORE_OVERWORLD);

    public static final RegistryObject<Item> RAW_AETHER_CRYSTAL = ITEMS.register("raw_aether_crystal", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> REFINED_AETHER_CRYSTAL = ITEMS.register("refined_aether_crystal", () -> new Item(ITEM_PROPERTIES));

    public static final Tags.IOptionalNamedTag<Block> AETHER_ORE = BlockTags.createOptional(new ResourceLocation(MODID, "aether_ore"));
    public static final Tags.IOptionalNamedTag<Item> AETHER_ORE_ITEM = ItemTags.createOptional(new ResourceLocation(MODID, "aether_ore"));

    public static final RegistryObject<AethergenBlock> AETHERGEN = BLOCKS.register("aethergen", AethergenBlock::new);
    public static final RegistryObject<Item> AETHERGEN_ITEM = fromBlock(AETHERGEN);
    public static final RegistryObject<BlockEntityType<AethergenBE>> AETHERGEN_BE = BLOCK_ENTITIES.register("aethergen", () -> BlockEntityType.Builder.of(AethergenBE::new, AETHERGEN.get()).build(null));
    public static final RegistryObject<MenuType<AethergenContainer>> AETHERGEN_CONTAINER = CONTAINERS.register("aethergen", () -> IForgeMenuType.create((windowId, inv, data) -> new AethergenContainer(windowId, data.readBlockPos(), inv, inv.player)));


    // Convenience function to get the corresponding RegistryObject<Item> from a RegistryObject<Block>.
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }

}
