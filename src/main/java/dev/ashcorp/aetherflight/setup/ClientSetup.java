package dev.ashcorp.aetherflight.setup;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.menus.AethergenScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AetherFlight.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Registration.AETHERGEN_CONTAINER.get(), AethergenScreen::new);

            ItemBlockRenderTypes.setRenderLayer(Registration.AETHERGEN.get(), RenderType.translucent());
        });
    }
}