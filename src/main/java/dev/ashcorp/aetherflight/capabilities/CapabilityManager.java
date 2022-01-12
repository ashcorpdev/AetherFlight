package dev.ashcorp.aetherflight.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityManager {

    public static Capability<AetherPlayerCapability> AETHER_PLAYER_CAPABILITY = net.minecraftforge.common.capabilities.CapabilityManager.get(new CapabilityToken<>() {
    });
}