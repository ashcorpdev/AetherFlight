package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
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
                .pattern(" x ")
                .pattern("x x")
                .pattern(" x ")
                .define('x', Blocks.DIRT)
                .group("aetherflight")
                .unlockedBy("refinedAetherCrystal", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.REFINED_AETHER_CRYSTAL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.NETHER_CORE.get())
                .pattern(" x ")
                .pattern("x x")
                .pattern(" x ")
                .define('x', Blocks.NETHERRACK)
                .group("aetherflight")
                .unlockedBy("overworldCore", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.OVERWORLD_CORE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.END_CORE.get())
                .pattern(" x ")
                .pattern("x x")
                .pattern(" x ")
                .define('x', Blocks.END_STONE)
                .group("aetherflight")
                .unlockedBy("netherCore", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.END_CORE.get()))
                .save(consumer);

    }
}
