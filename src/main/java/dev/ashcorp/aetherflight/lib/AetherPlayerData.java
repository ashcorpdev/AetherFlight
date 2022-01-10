package dev.ashcorp.aetherflight.lib;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class AetherPlayerData {

    private int tier = 1;
    private BlockPos pos;
    private Player player;

    public static LazyOptional<AetherPlayerData> get(Player player) {
        return player.getCapability(AetherDataProvider.CAPABILITY, null);
    }

    public void setPlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    public Tag writeData() {
        CompoundTag tag = new CompoundTag();

        tag.putInt("aethergenTier", tier);
        tag.putLong("aethergenLocation", pos.asLong());

        return tag;
    }

    public void readData(Tag nbt) {

        CompoundTag tag = (CompoundTag) nbt;

        this.tier = tag.getInt("aethergenTier");
        this.pos = BlockPos.of(tag.getLong("aethergenLocation"));
    }

    public int getAethergenTier() {
        return this.tier;
    }

    public void setAethergenTier(int tier) {
        this.tier = tier;
    }

    public BlockPos getAethergenLocation() {
        return this.pos;
    }

    public void setAethergenLocation(BlockPos pos) {
        this.pos = pos;
    }

}
