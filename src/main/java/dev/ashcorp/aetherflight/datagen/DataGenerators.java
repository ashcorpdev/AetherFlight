package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AetherFlight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    // Data generator helps to create models/json files etc easily.

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if(event.includeServer()) {
            generator.addProvider(new AetherFlightRecipes(generator));
            generator.addProvider(new AetherFlightLootTables(generator));
            AetherFlightBlockTags blockTags = new AetherFlightBlockTags(generator, event.getExistingFileHelper());
            generator.addProvider(blockTags);
            generator.addProvider(new AetherFlightItemTags(generator, blockTags, event.getExistingFileHelper()));
        }
        if(event.includeClient()) {
            generator.addProvider(new AetherFlightBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new AetherFlightItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new AetherFlightLanguageProvider(generator, "en_us"));
        }
    }
}
