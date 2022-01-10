package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AetherFlightItemTags extends ItemTagsProvider {

    public AetherFlightItemTags(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(gen, blockTags, AetherFlight.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.ORES)
                .add(Registration.AETHER_ORE_OVERWORLD_ITEM.get());
        tag(Registration.AETHER_ORE_ITEM)
                .add(Registration.AETHER_ORE_OVERWORLD_ITEM.get());
    }

    @Override
    public String getName() {
        return "Aether Flight Tags";
    }

}
