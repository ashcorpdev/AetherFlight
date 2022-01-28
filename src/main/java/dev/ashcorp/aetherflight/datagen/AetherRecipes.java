package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class AetherRecipes extends RecipeProvider {

    public AetherRecipes(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.AETHER_ORE_ITEM), Registration.REFINED_AETHER_CRYSTAL.get(), 1.0f, 100)
                .unlockedBy("has_ore", has(Registration.AETHER_ORE_ITEM))
                .save(consumer, "aether_crystal1");

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Registration.RAW_AETHER_CRYSTAL.get()),
                        Registration.REFINED_AETHER_CRYSTAL.get(), 0.0f, 100)
                .unlockedBy("has_chunk", has(Registration.RAW_AETHER_CRYSTAL.get()))
                .save(consumer, "aether_crystal2");

        ShapedRecipeBuilder.shaped(Registration.AETHER_SIPHON.get())
                .pattern("mcm")
                .pattern("x x")
                .pattern(" x ")
                .define('c', Registration.OVERWORLD_CORE.get())
                .define('x', Tags.Items.INGOTS_GOLD)
                .define('m', Registration.REFINED_AETHER_CRYSTAL.get())
                .group("aetherflight")
                .unlockedBy("refinedAetherCrystal", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.REFINED_AETHER_CRYSTAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.OVERWORLD_CORE.get())
                .pattern("sss")
                .pattern("sls")
                .pattern("wgw")
                .define('s', ItemTags.LEAVES)
                .define('g', Blocks.GRASS_BLOCK)
                .define('l', ItemTags.LOGS)
                .define('w', Items.WATER_BUCKET)
                .group("aetherflight")
                .unlockedBy("refinedAetherCrystal", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.REFINED_AETHER_CRYSTAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.NETHER_CORE.get())
                .pattern("wtb")
                .pattern("ngs")
                .pattern("lml")
                .define('w', Items.WITHER_SKELETON_SKULL)
                .define('t', Items.GHAST_TEAR)
                .define('b', Items.BLAZE_ROD)
                .define('n', Blocks.NETHERRACK)
                .define('g', Tags.Items.INGOTS_GOLD)
                .define('s', Blocks.GLOWSTONE)
                .define('l', Items.LAVA_BUCKET)
                .define('m', Items.MAGMA_BLOCK)
                .group("aetherflight")
                .unlockedBy("overworldCore", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.OVERWORLD_CORE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.END_CORE.get())
                .pattern("pep")
                .pattern("bob")
                .pattern("csc")
                .define('p', Items.ENDER_EYE)
                .define('e', Items.ELYTRA)
                .define('b', Blocks.PURPUR_BLOCK)
                .define('o', Blocks.OBSIDIAN)
                .define('c', Items.POPPED_CHORUS_FRUIT)
                .define('s', Blocks.END_STONE)
                .group("aetherflight")
                .unlockedBy("netherCore", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.END_CORE.get()))
                .save(consumer);
    }
}
