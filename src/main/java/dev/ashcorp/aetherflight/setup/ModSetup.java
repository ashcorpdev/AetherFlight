package dev.ashcorp.aetherflight.setup;

import dev.ashcorp.aetherflight.blocks.AetherOres;
import dev.ashcorp.aetherflight.lib.Helpers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

    public static final String TAB_NAME = "aether_flight";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.AETHER_ORE_OVERWORLD_ITEM.get());
        }
    };

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(AetherOres::onBiomeLoadingEvent);
        bus.addListener(Helpers::onServerStartedEvent);
    }

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AetherOres.registerConfiguredFeatures();
        });
    }
}
