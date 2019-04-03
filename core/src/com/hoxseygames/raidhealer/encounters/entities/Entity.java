package com.hoxseygames.raidhealer.encounters.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.HealingTracker;

/**
 * Created by Hoxsey on 6/16/2017.
 */
public class Entity extends Button{

    private int id;
    private String name;
    private int maxHp;
    private int hp;
    private float hpPercent;
    private int shield;
    private int healingAbsorb;
    private String role;
    private int damage;
    private boolean isDead;
    private Texture roleImage;
    private boolean selected;
    private Assets assets;
    private int amountAbsorbed;
    private HealingTracker healingTracker;

    /**
     * RaidMember param
     * @param id
     * @param role
     */
    protected Entity(int id, String role, Assets assets) {
        this.assets = assets;
        setBounds(assets.raidPositions.get(id).x,
                assets.raidPositions.get(id).y,146,70);

        this.id = id;
        this.role = role;
        setRoleStats(role);
        hp = maxHp;
        hpPercent = getHpPercent();
        shield = 0;
        isDead = false;
        selected = false;
        healingTracker = new HealingTracker();
    }

    /**
     * BOSS param
     * @param name
     * @param maxHp
     */
    protected Entity(String name, int maxHp, Assets assets) {
        this.assets = assets;
        this.name = name;
        this.maxHp = maxHp;
        hp = maxHp;
        shield = 0;
        isDead = false;
        healingTracker = new HealingTracker();
    }

    private void setRoleStats(String role)    {
        switch (role)   {
            case "Tank":
                maxHp = 200;
                damage = 5;
                break;
            case "Healer":
                maxHp = 100;
                damage = 2;
                break;
            case "Dps":
                maxHp = 100;
                damage = 10;
                break;
        }
    }

    public void takeDamage(int damage)    {
        if(!isDead) {
            if(shield > 0)
                takeShieldDamage(damage);
            else
                hp = hp - damage;

            if(hp <= 0) {
                isDead = true;
                hp = 0;
                shield = 0;
            }
            getHpPercent();
        }
    }

    public int takeDamage(int output, boolean isCritical)    {
        int newOutput = output;
        if(isCritical)
            newOutput = newOutput + (newOutput/2);
        takeDamage(newOutput);
        return newOutput;
    }

    private void takeShieldDamage(int damage)  {
        int oldShield = shield;
        shield = shield - (int)(damage*0.8f);
        if(shield <= 0) {
            hp = hp - Math.abs(shield);
            shield = 0;
            getHpPercent();
            amountAbsorbed = oldShield;
        }
        else {
            amountAbsorbed = (int)(damage*0.8f);
        }
        hp = hp - (int)(damage*(0.2f));
    }

    private void reduceHealingAbsorb(int output)   {
        int oldHealingAbsorb = healingAbsorb;
        healingAbsorb = healingAbsorb - output;

        if(healingAbsorb <= 0) {
            healingTracker.addHealingDone(oldHealingAbsorb);
            receiveHealing(Math.abs(healingAbsorb));
            healingAbsorb = 0;
            getHpPercent();
            return;
        }
        healingTracker.addHealingDone(output);
    }

    private void receiveDamage(int output) {
        if(!isDead()) {
            hp = hp - output;
            if(hp <= 0) {
                isDead = true;
                hp = 0;
            }
        }
    }

    public void receiveHealing(int output) {
        if (!isDead)   {
            if (hp < maxHp) {
                hp = hp + output;
                if (hp > maxHp) {
                    healingTracker.addHealingDone(output - (hp - maxHp));
                    hp = maxHp;
                    return;
                }
                healingTracker.addHealingDone(output);
            }

        }

    }

    public int receiveHealing(int output, boolean isCritical)    {
        int newOutput = output;



        if(isCritical) {
            newOutput = newOutput + (newOutput / 2);
            healingTracker.addTotalHealingDone(newOutput);
        }
        else    {
            healingTracker.addTotalHealingDone(newOutput);
        }

        if(healingAbsorb > 0) {
            reduceHealingAbsorb(newOutput);
        }
        else {
            receiveHealing(newOutput);
        }

        return newOutput;
    }

    public void applyShield(int output)   {

        if(output + shield > (int)(maxHp*0.8f)) {
            shield = (int)(maxHp*0.8f);
            return;
        }
        shield = shield + output;
    }

    public void applyHealingAbsorb(int output) {

        if(output + healingAbsorb > maxHp) {
            healingAbsorb = maxHp;
        }
        else {
            healingAbsorb = healingAbsorb + output;
        }
    }
    /*Getters and Setters*/

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    @SuppressWarnings("unused")
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }

    public int getShield() {
        return shield;
    }

    @SuppressWarnings("unused")
    public void setShield(int shield) {
        this.shield = shield;
    }

    public String getRole() {
        return role;
    }

    @SuppressWarnings("unused")
    public void setRole(String role) {
        this.role = role;
    }

    public int getDamage() {
        if(!isDead())
            return damage;
        else
            return 0;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isDead() {
        return isDead;
    }

    protected void setDead(boolean dead) {
        isDead = dead;
    }

    public float getHpPercent() {
        hpPercent = (float)hp/(float)maxHp;
        return hpPercent;
    }

    public boolean isFullHealth()   {
        if(getHp() == getMaxHp())
            return true;
        return false;
    }

    public float getShieldPercent() {
        return (float)shield/(float)maxHp;
    }

    public void setHpPercent(float hpPercent) {
        this.hpPercent = hpPercent;
    }

    public float getHealingAbsorbPercent()  {
        return (float)healingAbsorb/(float)maxHp;
    }

    protected Texture getRoleImage() {
        return roleImage;
    }

    protected void setRoleImage(Texture roleImage) {
        this.roleImage = roleImage;
    }


    public boolean isSelected() {
        return selected;
    }

    public void selected() {
        selected = true;
    }

    public void setAssets(Assets assets)    {
        this.assets = assets;
    }

    public void reset() {
        hp = maxHp;
    }

    public boolean equals(Entity entity) {
        return (this.getId() == entity.getId());
    }

    public HealingTracker getHealingTracker() {
        return healingTracker;
    }

    public int getHealingAbsorb() {
        return healingAbsorb;
    }

    @SuppressWarnings("unused")
    public void setHealingAbsorb(int healingAbsorb) {
        this.healingAbsorb = healingAbsorb;
    }

    protected void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Assets getAssets() {
        return assets;
    }

    @SuppressWarnings("unused")
    public int getAmountAbsorbed() {
        return amountAbsorbed;
    }

    public void setAmountAbsorbed(int amountAbsorbed) {
        this.amountAbsorbed = amountAbsorbed;
    }

    public void setHealingTracker(HealingTracker healingTracker) {
        this.healingTracker = healingTracker;
    }
}
