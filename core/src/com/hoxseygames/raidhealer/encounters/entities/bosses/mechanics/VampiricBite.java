package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.VampiricBiteEffect;

/**
 * Created by Hoxsey on 8/26/2017.
 */

public class VampiricBite extends Mechanic {

    private int numOfTargets;

    public VampiricBite(Boss owner) {
        super("Vampiric Bite", 20, 5f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    public VampiricBite(Boss owner, float speed) {
        super("Vampiric Bite", 20, speed, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {
        RaidMember t = getOwner().getEnemies().getRandomRaidMember(1).get(0);
        if(t != null)    {
            setTarget(t);
            AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
            getTarget().takeDamage(getDamage());
            getTarget().addStatusEffect(new VampiricBiteEffect(getOwner()));
        }
        stop();
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
