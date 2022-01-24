package dev.ashcorp.aetherflight.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityManager {

    public static Capability<AetherItemCapability> AETHER_ITEM_CAPABILITY = net.minecraftforge.common.capabilities.CapabilityManager.get(new CapabilityToken<>() {
    });
}