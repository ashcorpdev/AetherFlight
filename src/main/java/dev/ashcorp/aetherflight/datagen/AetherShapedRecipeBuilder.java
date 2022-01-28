package dev.ashcorp.aetherflight.datagen;

import dev.ashcorp.aetherflight.items.AetherSiphonItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class AetherShapedRecipeBuilder extends ShapedRecipeBuilder {

    public AetherShapedRecipeBuilder(ItemLike pResult, int pCount) {
        super(pResult, pCount);
    }


}
