package dev.ashcorp.aetherflight.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityAetherPlayer {

    public static Capability<AetherPlayerCapability> AETHER_PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
}