package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;

import static dev.ashcorp.aetherflight.blocks.AetherSiphonBlock.MESSAGE_AETHERSIPHON;
import static dev.ashcorp.aetherflight.blocks.AetherSiphonBlock.SCREEN_AETHERFLIGHT_AETHERSIPHON;
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
        add(MESSAGE_AETHERSIPHON, "Generates %s aether per tick.");
        add(SCREEN_AETHERFLIGHT_AETHERSIPHON, "Aether Siphon");
        add(Registration.AETHERSIPHON.get(), "Aether Siphon");
    }
}
