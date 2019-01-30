package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class TailSwipe extends Mechanic {

    private int numOfTargets;

    public TailSwipe(Boss owner) {
        super("Tail Swipe", 20, 5f, owner);
        numOfTargets = 2;
        setAnnounce();
    }

    public TailSwipe(Boss owner, float speed) {
        super("Tail Swipe", 20, speed, owner);
        numOfTargets = 5;
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().finishImpactSFX), false);
        ArrayList<RaidMember> raidMembers = getRaid().getRandomRaidMember(numOfTargets);
        for(int i = 0; i < raidMembers.size(); i++)   {
            raidMembers.get(i).takeDamage(getDamage());
            if(CriticalDice.roll(60,100,0)) {
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
