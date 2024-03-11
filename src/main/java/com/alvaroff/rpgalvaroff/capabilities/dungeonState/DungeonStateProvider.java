package com.alvaroff.rpgalvaroff.capabilities.dungeonState;

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

public class DungeonStateProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public DungeonStateProvider(){
        createDungeonState();
    }
    public static Capability<DungeonState> DUNGEON_STATUS = CapabilityManager.get(new CapabilityToken<DungeonState>() {});
    private DungeonState active = null;
    private final LazyOptional<DungeonState> optional = LazyOptional.of(this::createDungeonState);

    private DungeonState createDungeonState(){
        if(this.active == null){
            this.active = new DungeonState();
        }

        return this.active;
    }
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if(cap == DUNGEON_STATUS)
            return optional.cast();

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createDungeonState().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createDungeonState().loadNBTData(nbt);
    }
}
