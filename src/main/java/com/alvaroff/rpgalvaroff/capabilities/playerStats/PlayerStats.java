package com.alvaroff.rpgalvaroff.capabilities.playerStats;
import net.minecraft.nbt.CompoundTag;

public class PlayerStats {
    private int lvl;
    private float health;
    private float strength;
    private float resistance;
    private float mana;
    private float magicPower;
    private float agility;
    private float sanation;
    private PlayerClass playerClass;

    public PlayerStats(){

        this.lvl = 0;
        this.health = 0;
        this.strength = 0;
        this.resistance = 0;
        this.mana = 0;
        this.magicPower = 0;
        this.agility = 0;
        this.sanation = 0;
        this.playerClass = PlayerClass.NONE;
    }

    public PlayerStats(int lvl, float health, float strength, float resistance, float mana, float magicPower, float agility, float sanation, PlayerClass playerClass) {
        this.lvl = lvl;
        this.health = health;
        this.strength = strength;
        this.resistance = resistance;
        this.mana = mana;
        this.magicPower = magicPower;
        this.agility = agility;
        this.sanation = sanation;
        this.playerClass = playerClass;
    }

    public int getLevel(){
        return lvl;
    }

    public float getHealth() {
        return health;
    }

    public float getStrength() {
        return strength;
    }

    public float getResistance() {
        return resistance;
    }

    public float getMana() {
        return mana;
    }

    public float getMagicPower() {
        return magicPower;
    }

    public float getAgility() {
        return agility;
    }

    public float getSanation() {
        return sanation;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setLevel(int level){

        lvl = level;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public void setMagicPower(float magicPower) {
        this.magicPower = magicPower;
    }

    public void setAgility(float agility) {
        this.agility = agility;
    }

    public void setSanation(float sanation) {
        this.sanation = sanation;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public void copyFrom(PlayerStats source){

        this.lvl = source.lvl;
        this.health = source.health;
        this.strength = source.strength;
        this.resistance = source.resistance;
        this.mana = source.mana;
        this.magicPower = source.magicPower;
        this.agility = source.agility;
        this.sanation = source.sanation;
        this.playerClass = source.playerClass;
    }

    public void saveNBTData(CompoundTag nbt){

        nbt.putInt("level", lvl);
        nbt.putFloat("health", health);
        nbt.putFloat("strength", strength);
        nbt.putFloat("resistance", resistance);
        nbt.putFloat("mana", mana);
        nbt.putFloat("magicPower", magicPower);
        nbt.putFloat("agility", agility);
        nbt.putFloat("sanation", sanation);
        nbt.putString("playerClass", playerClass.toString());
    }

    public void loadNBTData(CompoundTag nbt){

        lvl = nbt.getInt("level");
        health = nbt.getFloat("health");
        strength = nbt.getFloat("strength");
        resistance = nbt.getFloat("resistance");
        mana = nbt.getFloat("mana");
        magicPower = nbt.getFloat("magicPower");
        agility = nbt.getFloat("agility");
        sanation = nbt.getFloat("sanation");
        playerClass = PlayerClass.valueOf(nbt.getString("playerClass"));
    }
}
