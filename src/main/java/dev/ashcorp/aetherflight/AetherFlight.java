package dev.ashcorp.aetherflight;

import dev.ashcorp.aetherflight.setup.ClientSetup;
import dev.ashcorp.aetherflight.setup.ModSetup;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AetherFlight.MODID)
public class AetherFlight
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "aetherflight";

    public AetherFlight() {

        // Register the deferred registry
        Registration.init();

        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        ModSetup.setup();
        modbus.addListener(ModSetup::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                modbus.addListener(ClientSetup::init));

    }
}
