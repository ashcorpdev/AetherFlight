package dev.ashcorp.aetherflight.lib;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityAetherPlayer {

    public static Capability<IAether> AETHER_PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
}