package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AetherBlockTags extends BlockTagsProvider {

    public AetherBlockTags(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, AetherFlight.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.AETHER_ORE_OVERWORLD.get())
                .add(Registration.AETHERSIPHON.get());
        tag(net.minecraft.tags.BlockTags.NEEDS_IRON_TOOL)
                .add(Registration.AETHER_ORE_OVERWORLD.get())
                .add(Registration.AETHERSIPHON.get());
        tag(Tags.Blocks.ORES)
                .add(Registration.AETHER_ORE_OVERWORLD.get());
        tag(Registration.AETHER_ORE)
                .add(Registration.AETHER_ORE_OVERWORLD.get());
    }

    @Override
    public String getName() {
        return "Aether Flight Tags";
    }
}
