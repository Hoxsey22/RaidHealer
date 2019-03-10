package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/28/2017.
 */

public class Swipe extends Mechanic {

    private int numOfTargets;

    public Swipe(Boss owner) {
        super("Swipe", 25, 4f, owner);
        numOfTargets = 4;
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().finishImpactSFX), false);
        ArrayList<RaidMember> raidMembers = getRaid().getRandomRaidMember(numOfTargets);
        for(int i = 0; i < raidMembers.size(); i++)   {
            raidMembers.get(i).takeDamage(getDamage());
            if(CriticalDice.roll(20,100,0)) {
                raidMembers.get(i).addStatusEffect(new BleedEffect(getOwner(), 3f, 5));
            }
        }
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
