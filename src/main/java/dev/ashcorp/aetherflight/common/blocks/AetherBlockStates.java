package dev.ashcorp.aetherflight.common.blocks;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AetherBlockStates extends BlockStateProvider {

    public AetherBlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, AetherFlight.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.AETHER_ORE_OVERWORLD.get());
    }
}