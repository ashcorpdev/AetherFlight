package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;

public class AetherFlightLootTables extends AetherFlightBaseLootTableProvider {

    public AetherFlightLootTables(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void addTables() {

        lootTables.put(Registration.AETHER_ORE_OVERWORLD.get(), createSilkTouchTable("aether_ore_overworld",
                Registration.AETHER_ORE_OVERWORLD.get(), Registration.RAW_AETHER_CRYSTAL.get(), 1, 3));

        lootTables.put(Registration.AETHERGEN.get(), createStandardTable("aethergen", Registration.AETHERGEN.get(), Registration.AETHERGEN_BE.get()));
    }
}
