package dev.ashcorp.aetherflight.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static final String TAB_NAME = "aether_flight";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.AETHER_ORE_OVERWORLD_ITEM.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {

    }
}
