package dev.ashcorp.aetherflight.client.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import dev.ashcorp.aetherflight.AetherFlight;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = AetherFlight.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherKeyBindings {

    private static final String CATEGORY = "key.category.aetherflight.general";
    public static final KeyMapping BOOST_FLIGHT = new KeyMapping("key.aetherflight.boost_flight", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY);

    @SubscribeEvent
    public static void registerKeyBindings(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(BOOST_FLIGHT);
    }

}
