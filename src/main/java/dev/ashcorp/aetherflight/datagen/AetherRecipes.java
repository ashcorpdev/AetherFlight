package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
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

        ShapedRecipeBuilder.shaped(Registration.AETHERSIPHON.get())
                .pattern("mmm")
                .pattern("x#x")
                .pattern("#x#")
                .define('x', Tags.Items.DUSTS_REDSTONE)
                .define('#', Tags.Items.INGOTS_IRON)
                .define('m', Registration.REFINED_AETHER_CRYSTAL.get())
                .group("assets/aetherflight")
                .unlockedBy("aether", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.REFINED_AETHER_CRYSTAL.get()))
                .save(consumer);
    }
}
