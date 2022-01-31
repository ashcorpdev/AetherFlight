package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;

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
        add(Registration.OVERWORLD_CORE.get(), "Overworld Core");
        add(Registration.NETHER_CORE.get(), "Nether Core");
        add(Registration.END_CORE.get(), "End Core");
        add(Registration.BASIC_AETHER_SIPHON.get(), "Basic Aether Siphon");
        add(Registration.NETHER_AETHER_SIPHON.get(), "Nether Aether Siphon");
        add(Registration.END_AETHER_SIPHON.get(), "End Aether Siphon");
        add("item.aetherflight.aether_siphon.tier", "Tier: %1$s");
        add("item.aetherflight.aether_siphon.owner", "Owner: %1$s");
        add("item.aetherflight.aether_siphon.storedAether", "Stored Aether: %1$s / %2$s");
        add("item.aetherflight.aether_siphon.conflict", "Another aether siphon is preventing you from flying!");
    }
}
