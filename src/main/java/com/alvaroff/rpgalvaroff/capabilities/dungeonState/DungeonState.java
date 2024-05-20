package com.alvaroff.rpgalvaroff.capabilities.dungeonState;

import net.minecraft.nbt.CompoundTag;

public class DungeonState {
    private boolean active;
    private int activeSpawners;
    private float bossRoom;

    public DungeonState(){

        active = false;
        activeSpawners = 0;
        bossRoom = 0;
    }

    public boolean getStatus(){
        return active;
    }

    public void setStatus(boolean status){
        active = status;
    }

    public int getActiveSpawners() {
        return activeSpawners;
    }

    public void setActiveSpawners(int activeSpawners) {
        this.activeSpawners = activeSpawners;
    }

    public void subtractSpawner(){
        if (activeSpawners > 0)
            activeSpawners--;
    }

    public float getBossRoom() {
        return bossRoom;
    }

    public void setBossRoom(float bossRoom) {
        this.bossRoom = bossRoom;
    }

    public void addPercentageBossRoom() {
        if(bossRoom + 0.1f > 1)
            this.bossRoom = 1;
        else
            this.bossRoom += 0.1f;
    }

    public void copyFrom(DungeonState source){
        this.active = source.active;
    }

    public void saveNBTData(CompoundTag nbt){

        nbt.putBoolean("dungeonActive", active);
        nbt.putInt("activeSpawners", activeSpawners);
        nbt.putFloat("boss", bossRoom);
    }

    public void loadNBTData(CompoundTag nbt){

        active = nbt.getBoolean("dungeonActive");
        activeSpawners = nbt.getInt("activeSpawners");
        bossRoom = nbt.getFloat("boss");
    }
}
