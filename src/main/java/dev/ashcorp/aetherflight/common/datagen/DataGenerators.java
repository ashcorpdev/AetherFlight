package dev.ashcorp.aetherflight.common.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.common.blocks.AetherBlockStates;
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
        if (event.includeServer()) {
            generator.addProvider(new AetherRecipes(generator));
            generator.addProvider(new AetherLootTables(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new AetherBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(new AetherItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new AetherLanguageProvider(generator, "en_us"));
        }
    }
}
