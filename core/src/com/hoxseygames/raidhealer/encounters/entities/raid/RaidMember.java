package com.hoxseygames.raidhealer.encounters.entities.raid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.encounters.entities.Entity;
import com.hoxseygames.raidhealer.encounters.floatingtext.FloatingText;
import com.hoxseygames.raidhealer.encounters.floatingtext.FloatingTextManager;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.StatusEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.StatusEffectList;

import java.util.Comparator;

/**
 * Created by Hoxsey on 6/16/2017.
 */
public class RaidMember extends Entity implements Comparable<RaidMember>, Comparator<RaidMember> {

    private Texture frame;
    private HealthBar healthBar;
    private FloatingTextManager floatingTextManager;
    private StatusEffectList statusEffects;


    public RaidMember(int id, String role, Assets assets)  {
        super(id,role,assets);
        healthBar = new HealthBar(this);
        frame = getAssets().getTexture(getAssets().raidFrameIdle);


    }

    private void create()    {
        setRoleImage();
        floatingTextManager = new FloatingTextManager(this, getAssets());
        statusEffects = new StatusEffectList(this);
        setHp(getMaxHp());
        setShield(0);
        setHealingAbsorb(0);
        setDead(false);
    }

    @Override
    public void takeDamage(int output) {
        int newOutput = statusEffects.getStatusEffectModification(output,false);
        super.takeDamage(newOutput);
        floatingTextManager.add(newOutput, FloatingText.DAMAGE);
        if(statusEffects.contains("Prayer of Mending"))    {
            statusEffects.getStatusEffect("Prayer of Mending").applyEffect();
        }
        if(isDead())    {
            statusEffects.clear();
        }

    }

    @Override
    public int receiveHealing(int output, boolean isCritical) {
        int newOutput = statusEffects.getStatusEffectModification(output,true);
        newOutput = super.receiveHealing(newOutput, isCritical);
        floatingTextManager.add(newOutput,FloatingText.HEAL, isCritical);
        return newOutput;
    }

    @Override
    public void applyShield(int output) {
        super.applyShield(output);
        floatingTextManager.add(output,FloatingText.SHIELD);
    }

    private void setRoleImage()  {
        switch (getRole())   {
            case "Tank":
                setRoleImage(getAssets().getTexture(getAssets().tankIcon));
                break;
            case "Healer":
                setRoleImage(getAssets().getTexture(getAssets().healerIcon));
                break;
            case "Dps":
                setRoleImage(getAssets().getTexture(getAssets().dpsIcon));
                break;
        }
    }

    public void unselected()  {
        setSelected(false);
        frame = getAssets().getTexture(getAssets().raidFrameIdle);
    }

    public void selected()  {
        setSelected(true);
        frame = getAssets().getTexture(getAssets().raidFrameSelected);
    }

    @Override
    public int compareTo(RaidMember rm) {
        return (int)(getFullHealthPercent()*100) - (int)(rm.getFullHealthPercent()*100);
    }

    @Override
    public int compare(RaidMember rm1, RaidMember rm2) {
        return (int)(rm1.getFullHealthPercent()*100) - (int)(rm2.getFullHealthPercent()*100);
    }

    public float getHealthPercent() {
        return (float)getHp()/(float)getMaxHp();
    }

    public float getFullHealthPercent() {

       if(getHp()-getHealingAbsorb() <= 0)
           return 0;
       else {
           return (float)(getHp()-getHealingAbsorb())/(float)getMaxHp();
       }
    }

    @Override
    public String toString() {
        return getId()+":"+getRole()+" hp:"+getHp();
    }

    public void addStatusEffect(StatusEffect newStatusEffect)   {
        newStatusEffect.setTarget(this);
        statusEffects.add(newStatusEffect);
    }

    public StatusEffectList getStatusEffectList() {
        return statusEffects;
    }

    public void setStatusEffects(StatusEffectList statusEffects) {
        this.statusEffects = statusEffects;
    }

    @Override
    public void reset() {
        super.reset();
        switch (getRole())   {
            case "Tank":
                setDamage(5);
                break;
            case "Healer":
                setDamage(2);
                break;
            case "Dps":
                setDamage(10);
                break;
        }
        create();
    }

    public void stop()  {
        if(statusEffects != null) {
            for (int i = 0; i < statusEffects.size(); i++) {
                statusEffects.clear();
            }
        }
    }

    public Texture getFrame() {
        return frame;
    }

    public void setFrame(Texture frame) {
        this.frame = frame;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public FloatingTextManager getFloatingTextManager() {
        return floatingTextManager;
    }

    public void setFloatingTextManager(FloatingTextManager floatingTextManager) {
        this.floatingTextManager = floatingTextManager;
    }

    public StatusEffectList getStatusEffects() {
        return statusEffects;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(frame, getX(),getY(),getWidth(),getHeight());
        if(!isDead()) {
            batch.draw(getRoleImage(), getX()+8, getY() + getHeight()- 39, 34,34);
            /*
            for (int i = 0; i < effects.size(); i++) {
                batch.draw(effects.get(i), healthBar.x + healthBar.width - 20 * (i) - 20, healthBar.y + healthBar.height + 5, 20, 20);
            }*/
            statusEffects.draw(batch, alpha);
            healthBar.draw(batch, alpha);
            floatingTextManager.draw(batch, alpha);
        }
        else {
            floatingTextManager.clear();
            batch.draw(getAssets().getTexture(getAssets().deathIcon), getX()+5, getY() + getHeight()- 39, 34,34);
        }
    }

}
