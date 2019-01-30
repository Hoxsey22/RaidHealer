package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class BackStab extends Mechanic {

    private int numOfTargets;

    public BackStab(Boss owner) {
        super("Back Stab", owner.getDamage()*3, 17f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> temp  = getOwner().getEnemies().getRandomRaidMember(numOfTargets);
        AudioManager.playSFX(getAssets().getSound(getAssets().stabSFX), false);
        for (int i = 0; i < temp.size(); i++)   {
            BleedEffect bleedEffect = new BleedEffect(getOwner());
            bleedEffect.setModValue(5);

            temp.get(i).takeDamage(getDamage());
            temp.get(i).addStatusEffect(bleedEffect);
        }
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
