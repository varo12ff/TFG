package com.alvaroff.rpgalvaroff.capabilities.playerStats;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStatsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public PlayerStatsProvider(){
        createPlayerStats();
    }

    public static Capability<PlayerStats> PLAYER_STATS = CapabilityManager.get(new CapabilityToken<PlayerStats>() {});
    private PlayerStats playerStats = null;
    private final LazyOptional<PlayerStats> optional = LazyOptional.of(this::createPlayerStats);

    private PlayerStats createPlayerStats(){
        if(this.playerStats == null){
            this.playerStats = new PlayerStats();
        }

        return this.playerStats;
    }
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if(cap == PLAYER_STATS)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerStats().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerStats().loadNBTData(nbt);
    }
}
