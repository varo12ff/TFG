package com.alvaroff.rpgalvaroff.capabilities.playerStats;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class PlayerStats {
    private int lvl;
    private int xp;
    private int nextLvl;
    private int abilityPoints;
    private float health;
    private int strength;
    private int ability;
    private int resistance;
    private int mana;
    private float manaCant;
    private final float MAX_MANA = 100.0f;
    private float currentMana;
    private int magicPower;
    private int agility;
    private int sanation;
    private PlayerClass playerClass;

    public PlayerStats(){

        this.lvl = 0;
        this.xp = 0;
        this.nextLvl = 0;
        this.abilityPoints = 0;
        this.health = 6.0f;
        this.strength = 0;
        this.ability = 0;
        this.resistance = 0;
        this.mana = 0;
        this.manaCant = 0;
        this.currentMana = 0;
        this.magicPower = 0;
        this.agility = 0;
        this.sanation = 0;
        this.playerClass = PlayerClass.NONE;
    }

    public PlayerStats(int lvl, float health, int strength, int ability, int resistance, int mana, int magicPower, int agility, int sanation, PlayerClass playerClass) {
        this.lvl = lvl;
        this.xp = 0;
        this.nextLvl = lvl * 10;
        this.abilityPoints = 0;
        this.health = health;
        this.strength = strength;
        this.ability = ability;
        this.resistance = resistance;
        this.mana = mana;
        this.manaCant = mana * 5;
        this.currentMana = manaCant;
        this.magicPower = magicPower;
        this.agility = agility;
        this.sanation = sanation;
        this.playerClass = playerClass;
    }

    public int getLevel(){
        return lvl;
    }
    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
    public void addXp(int xp) {
        if(playerClass != PlayerClass.NONE) {
            this.xp += xp;
            if (this.xp >= this.nextLvl) {
                this.lvl++;
                this.xp = this.xp % this.nextLvl;
                this.nextLvl *= 2;
                this.abilityPoints++;
            }
        }
    }

    public int getNextLvl() {
        return nextLvl;
    }

    public void setNextLvl(int nextLvl) {
        this.nextLvl = nextLvl;
    }

    public int getAbilityPoints() {
        return abilityPoints;
    }

    public void setAbilityPoints(int abilityPoints) {
        this.abilityPoints = abilityPoints;
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

    public void addHealth(){
        this.health++;
        this.abilityPoints--;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void addStrength(){
        this.strength++;
        this.abilityPoints--;
    }
    public void setAbility(int ability) {
        this.ability = ability;
    }

    public void addAbility(){
        this.ability++;
        this.abilityPoints--;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void addResistance() {
        this.resistance++;
        this.abilityPoints--;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void addMana() {
        this.mana++;
        this.abilityPoints--;
    }
    public void setCurrentMana(float mana) {
        if(mana > manaCant)
            this.currentMana = this.manaCant;
        else
            this.currentMana = mana;

    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    public float getManaCant() {
        return manaCant;
    }

    public void setManaCant(float manaCant) {
        this.manaCant = manaCant;
    }

    public void addMagicPower() {
        this.magicPower++;
        this.abilityPoints--;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void addAgility() {
        this.agility++;
        this.abilityPoints--;
    }

    public void setSanation(int sanation) {
        this.sanation = sanation;
    }

    public void addSanation() {
        this.sanation++;
        this.abilityPoints--;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public void copyFrom(PlayerStats source){

        this.lvl = source.lvl;
        this.xp = source.xp;
        this.nextLvl = source.nextLvl;
        this.abilityPoints = source.abilityPoints;
        this.health = source.health;
        this.strength = source.strength;
        this.ability = source.ability;
        this.resistance = source.resistance;
        this.mana = source.mana;
        this.manaCant = source.manaCant;
        this.currentMana = source.currentMana;
        this.magicPower = source.magicPower;
        this.agility = source.agility;
        this.sanation = source.sanation;
        this.playerClass = source.playerClass;
    }

    public void syncPlayer(Player player){
        if(playerClass == PlayerClass.GUERRERO) {
            float agilityBase = 0.075f;
            float cooldownBase = 4.0f;
            float strengthBase = 1.0f;

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health + 1);
            //base = 1
            player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(strengthBase + ((float) strength) / 2.5f);
            //base = 0.1
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(agilityBase + ((float) agility) / 250);
            //base = 4
            player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(cooldownBase + ((float) ability) / 100);
            //base = 0
            player.getAttribute(Attributes.ARMOR).setBaseValue(resistance + 0.5);

            this.manaCant = mana * 2;
            setCurrentMana(currentMana);
        }
        else if(playerClass == PlayerClass.NINJA) {
            float agilityBase = 0.125f;
            float cooldownBase = 5.0f;
            float strengthBase = 1.0f;

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(strengthBase + ((float) strength) / 3);
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(agilityBase + ((float) agility) / 150);
            player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(cooldownBase + ((float) ability) / 80);
            player.getAttribute(Attributes.ARMOR).setBaseValue(resistance * 0.5);


            this.manaCant = mana * 3;
            setCurrentMana(currentMana);
        }
        else if(playerClass == PlayerClass.CLERIGO) {
            float agilityBase = 0.075f;
            float cooldownBase = 4.0f;
            float strengthBase = 0.8f;

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health + 0.5);
            player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(strengthBase + ((float) strength) / 5);
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(agilityBase + ((float) agility) / 250);
            player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(cooldownBase + ((float) ability) / 100);
            player.getAttribute(Attributes.ARMOR).setBaseValue(resistance * 0.5);

            this.manaCant = mana * 4;
            setCurrentMana(currentMana);
        }
        else if(playerClass == PlayerClass.MAGO) {
            float agilityBase = 0.1f;
            float cooldownBase = 4.0f;
            float strengthBase = 0.8f;

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health - 1);
            player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(strengthBase + ((float) strength) / 5);
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(agilityBase + ((float) agility) / 200);
            player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(cooldownBase + ((float) ability) / 100);
            player.getAttribute(Attributes.ARMOR).setBaseValue(resistance * 0.5);

            this.manaCant = mana * 5;
            setCurrentMana(currentMana);
        }
        else{
            float agilityBase = 0.1f;
            float cooldownBase = 4.0f;
            float strengthBase = 1.0f;

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(strengthBase);
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(agilityBase);
            player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(cooldownBase);
            player.getAttribute(Attributes.ARMOR).setBaseValue(0);
            this.manaCant = 0;
            setCurrentMana(currentMana);
        }
    }

    public void saveNBTData(CompoundTag nbt){

        nbt.putInt("level", lvl);
        nbt.putInt("xp", xp);
        nbt.putInt("nextLvl", nextLvl);
        nbt.putInt("abilityPoints", abilityPoints);
        nbt.putFloat("health", health);
        nbt.putInt("strength", strength);
        nbt.putInt("ability", ability);
        nbt.putInt("resistance", resistance);
        nbt.putInt("mana", mana);
        nbt.putFloat("manaCant", manaCant);
        nbt.putFloat("currentMana", currentMana);
        nbt.putInt("magicPower", magicPower);
        nbt.putInt("agility", agility);
        nbt.putInt("sanation", sanation);
        nbt.putString("playerClass", playerClass.toString());
    }

    public void loadNBTData(CompoundTag nbt){

        lvl = nbt.getInt("level");
        xp = nbt.getInt("xp");
        nextLvl = nbt.getInt("nextLvl");
        abilityPoints = nbt.getInt("abilityPoints");
        health = nbt.getFloat("health");
        strength = nbt.getInt("strength");
        ability = nbt.getInt("ability");
        resistance = nbt.getInt("resistance");
        mana = nbt.getInt("mana");
        manaCant = nbt.getFloat("manaCant");
        currentMana = nbt.getFloat("currentMana");
        magicPower = nbt.getInt("magicPower");
        agility = nbt.getInt("agility");
        sanation = nbt.getInt("sanation");
        playerClass = PlayerClass.valueOf(nbt.getString("playerClass"));
    }

    public CompoundTag getNBT(){

        CompoundTag nbt = new CompoundTag();

        nbt.putInt("level", lvl);
        nbt.putInt("xp", xp);
        nbt.putInt("nextLvl", nextLvl);
        nbt.putInt("abilityPoints", abilityPoints);
        nbt.putFloat("health", health);
        nbt.putInt("strength", strength);
        nbt.putInt("ability", ability);
        nbt.putInt("resistance", resistance);
        nbt.putInt("mana", mana);
        nbt.putFloat("manaCant", manaCant);
        nbt.putFloat("currentMana", currentMana);
        nbt.putInt("magicPower", magicPower);
        nbt.putInt("agility", agility);
        nbt.putInt("sanation", sanation);
        nbt.putString("playerClass", playerClass.toString());

        return nbt;
    }
}
