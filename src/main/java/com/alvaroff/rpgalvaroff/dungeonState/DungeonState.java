package com.alvaroff.rpgalvaroff.dungeonState;

import net.minecraft.nbt.CompoundTag;

public class DungeonState {
    private boolean active;

    public DungeonState(){
        active = false;
    }

    public boolean getStatus(){
        return active;
    }

    public void setStatus(boolean status){
        active = status;
    }

    public void copyFrom(DungeonState source){
        this.active = source.active;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("dungeonActive", active);
    }

    public void loadNBTData(CompoundTag nbt){
        active = nbt.getBoolean("dungeonActive");
    }
}
