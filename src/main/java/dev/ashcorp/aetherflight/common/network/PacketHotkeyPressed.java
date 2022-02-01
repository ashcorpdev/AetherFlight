package dev.ashcorp.aetherflight.common.network;

import dev.ashcorp.aetherflight.AetherFlight;
import dev.ashcorp.aetherflight.common.items.AetherSiphonItem;
import dev.ashcorp.aetherflight.setup.Registration;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHotkeyPressed {

    public enum Key{
        BOOST_FLIGHT
    }
    Key key;

    public PacketHotkeyPressed(Key key){
        this.key = key;
    }

    //Decoder
    public PacketHotkeyPressed(FriendlyByteBuf buf){
        this.key = Key.valueOf(buf.readUtf());
    }

    //Encoder
    public void toBytes(FriendlyByteBuf buf){
        buf.writeUtf(key.name());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer player = ctx.get().getSender();
            if(player != null){
                ItemStack stack;
                AetherFlight.LOGGER.info("Checking for aether siphon in player inventory (Packets)...");
                for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    if(player.getInventory().getItem(i).getItem() instanceof AetherSiphonItem) {
                        stack = player.getInventory().getItem(i);
                    } else {
                        return;
                    }
                    if(!(stack.getItem() instanceof AetherHotkeyListener hotkeyListener)){
                        return;
                    }
                    if(key == Key.BOOST_FLIGHT){
                        hotkeyListener.onBoostKeyPressed(stack, player);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

}