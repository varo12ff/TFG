package com.alvaroff.rpgalvaroff.capabilities.playerStats;
import net.minecraft.nbt.CompoundTag;

public class PlayerStats {
    private int lvl;
    private float health;
    private int strength;
    private int ability;
    private int resistance;
    private int mana;
    private final float MAX_MANA = 100.0f;
    private float currentMana;
    private int magicPower;
    private int agility;
    private int sanation;
    private PlayerClass playerClass;

    public PlayerStats(){

        this.lvl = 0;
        this.health = 6.0f;
        this.strength = 0;
        this.ability = 0;
        this.resistance = 0;
        this.mana = 0;
        this.currentMana = mana;
        this.magicPower = 0;
        this.agility = 0;
        this.sanation = 0;
        this.playerClass = PlayerClass.NONE;
    }

    public PlayerStats(int lvl, float health, int strength, int ability, int resistance, int mana, int magicPower, int agility, int sanation, PlayerClass playerClass) {
        this.lvl = lvl;
        this.health = health;
        this.strength = strength;
        this.ability = ability;
        this.resistance = resistance;
        this.mana = mana;
        this.currentMana = mana;
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

    public int getStrength() {
        return strength;
    }

    public int getAbility() {
        return ability;
    }

    public int getResistance() {
        return resistance;
    }

    public int getMana() {
        return mana;
    }
    public float getCurrentMana() {
        return currentMana;
    }
    public float getMaxMana() {
        return MAX_MANA;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public int getAgility() {
        return agility;
    }

    public int getSanation() {
        return sanation;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setLevel(int lvl){ this.lvl = lvl; }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setAbility(int ability) {
        this.ability = ability;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
    public void setCurrentMana(float mana) {
        this.currentMana = currentMana;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setSanation(int sanation) {
        this.sanation = sanation;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public void copyFrom(PlayerStats source){

        this.lvl = source.lvl;
        this.health = source.health;
        this.strength = source.strength;
        this.ability = source.ability;
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
        nbt.putInt("strength", strength);
        nbt.putInt("ability", ability);
        nbt.putInt("resistance", resistance);
        nbt.putInt("mana", mana);
        nbt.putFloat("currentMana", currentMana);
        nbt.putInt("magicPower", magicPower);
        nbt.putInt("agility", agility);
        nbt.putInt("sanation", sanation);
        nbt.putString("playerClass", playerClass.toString());
    }

    public void loadNBTData(CompoundTag nbt){

        lvl = nbt.getInt("level");
        health = nbt.getFloat("health");
        strength = nbt.getInt("strength");
        ability = nbt.getInt("ability");
        resistance = nbt.getInt("resistance");
        mana = nbt.getInt("mana");
        currentMana = nbt.getFloat("currentMana");
        magicPower = nbt.getInt("magicPower");
        agility = nbt.getInt("agility");
        sanation = nbt.getInt("sanation");
        playerClass = PlayerClass.valueOf(nbt.getString("playerClass"));
    }

    public CompoundTag getNBT(){

        CompoundTag nbt = new CompoundTag();

        nbt.putInt("level", lvl);
        nbt.putFloat("health", health);
        nbt.putInt("strength", strength);
        nbt.putInt("ability", ability);
        nbt.putInt("resistance", resistance);
        nbt.putInt("mana", mana);
        nbt.putFloat("currentMana", currentMana);
        nbt.putInt("magicPower", magicPower);
        nbt.putInt("agility", agility);
        nbt.putInt("sanation", sanation);
        nbt.putString("playerClass", playerClass.toString());

        return nbt;
    }
}
