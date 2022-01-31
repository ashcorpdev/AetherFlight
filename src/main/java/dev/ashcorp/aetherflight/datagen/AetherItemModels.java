package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AetherItemModels extends ItemModelProvider {

    public AetherItemModels(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, AetherFlight.MODID, helper);
    }

    @Override
    protected void registerModels() {

        withExistingParent(Registration.AETHER_ORE_OVERWORLD_ITEM.get().getRegistryName().getPath(), modLoc("block/aether_ore_overworld"));

        singleTexture(Registration.RAW_AETHER_CRYSTAL.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/raw_aether_crystal"));

        singleTexture(Registration.REFINED_AETHER_CRYSTAL.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/refined_aether_crystal"));

        singleTexture(Registration.BASIC_AETHER_SIPHON.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/basic_aether_siphon"));

        singleTexture(Registration.NETHER_AETHER_SIPHON.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/nether_aether_siphon"));

        singleTexture(Registration.END_AETHER_SIPHON.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/end_aether_siphon"));

        singleTexture(Registration.OVERWORLD_CORE.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/overworld_core"));

        singleTexture(Registration.NETHER_CORE.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/nether_core"));

        singleTexture(Registration.END_CORE.get().getRegistryName().getPath(), mcLoc("item/generated"),
                "layer0", modLoc("item/end_core"));

    }
}
