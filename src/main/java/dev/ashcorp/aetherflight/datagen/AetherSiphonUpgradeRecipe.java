package dev.ashcorp.aetherflight.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.ashcorp.aetherflight.items.AetherSiphonItem;
import dev.ashcorp.aetherflight.lib.RecipeHelper;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class AetherSiphonUpgradeRecipe extends ShapedRecipe {

    public AetherSiphonUpgradeRecipe(ResourceLocation pId, String pGroup, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
        super(pId, pGroup, pWidth, pHeight, pRecipeItems, pResult);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.AETHER_SIPHON_UPGRADE_RECIPE;
    }

    @Override
    public ItemStack assemble(CraftingContainer pInv) {

        final ItemStack output = super.assemble(pInv);

        if(!output.isEmpty()) {
            for(int i = 0; i < pInv.getContainerSize(); i++) {
                final ItemStack ingredient = pInv.getItem(i);
                if (!ingredient.isEmpty() && ingredient.getItem() instanceof AetherSiphonItem) {
                        output.setTag(ingredient.getOrCreateTag().copy());
                    switch (ingredient.getOrCreateTag().getInt("tier")) {
                        case 1 -> output.getOrCreateTag().putInt("tier", 2);
                        case 2 -> output.getOrCreateTag().putInt("tier", 3);
                        default -> {
                        }
                    }
                }
            }
        }

        return output;
    }

    // This is where we define how the recipe is structured and recognised by the game.
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AetherSiphonUpgradeRecipe> {

        @Override
        public AetherSiphonUpgradeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

            final String group = GsonHelper.getAsString(pSerializedRecipe, "group", "");
            final NonNullList<Ingredient> ingredients = RecipeHelper.parseShaped(pSerializedRecipe).getIngredients();
            final ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"), true);

            return new AetherSiphonUpgradeRecipe(pRecipeId, group, 3, 3, ingredients, result);

        }

        @Nullable
        @Override
        public AetherSiphonUpgradeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {

            final String group = pBuffer.readUtf(Short.MAX_VALUE);
            final int numberOfIngredients = pBuffer.readInt();
            final NonNullList<Ingredient> ingredients = NonNullList.withSize(numberOfIngredients, Ingredient.EMPTY);

            for (int j = 0; j < ingredients.size(); j++) {
                ingredients.set(j, Ingredient.fromNetwork(pBuffer));
            }

            final ItemStack result = pBuffer.readItem();

            return new AetherSiphonUpgradeRecipe(pRecipeId, group, 3, 3, ingredients, result);

        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AetherSiphonUpgradeRecipe pRecipe) {

            pBuffer.writeUtf(pRecipe.getGroup());
            pBuffer.writeVarInt(pRecipe.getIngredients().size());

            for (final Ingredient ingredient: pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.getResultItem());

        }
    }
}
