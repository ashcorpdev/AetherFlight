package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static dev.ashcorp.aetherflight.blocks.AethergenBlock.MESSAGE_AETHERGEN;
import static dev.ashcorp.aetherflight.blocks.AethergenBlock.SCREEN_AETHERFLIGHT_AETHERGEN;
import static dev.ashcorp.aetherflight.setup.ModSetup.TAB_NAME;

public class AetherLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public AetherLanguageProvider(DataGenerator gen, String locale) {
        super(gen, AetherFlight.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + TAB_NAME, "Aether Flight");
        add(Registration.AETHER_ORE_OVERWORLD.get(), "Aether Ore");
        add(Registration.RAW_AETHER_CRYSTAL.get(), "Raw Aether Crystal");
        add(Registration.REFINED_AETHER_CRYSTAL.get(), "Refined Aether Crystal");
        add(MESSAGE_AETHERGEN, "Generates %s aether per tick.");
        add(SCREEN_AETHERFLIGHT_AETHERGEN, "Aether Siphon");
        add(Registration.AETHERGEN.get(), "Aether Siphon");
    }
}
