package dev.ashcorp.aetherflight.common.network;

import dev.ashcorp.aetherflight.common.items.AetherSiphonItem;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface AetherHotkeyListener {

    default void onBoostKeyPressed(ItemStack stack, ServerPlayer player){
        ((AetherSiphonItem) stack.getItem()).boost(player);
    }

}
