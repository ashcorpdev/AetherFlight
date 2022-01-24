package dev.ashcorp.aetherflight.capabilities;

public interface IAether {

    int getAetherSiphonTier();
    void setAetherSiphonTier(int tier);

    int getStoredAether();
    void setStoredAether(int aether);

    int getMaxAether();
    void setMaxAether(int maxAether);

}
