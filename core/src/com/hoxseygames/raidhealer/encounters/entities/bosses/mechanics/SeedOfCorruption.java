package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.SeedOfCorruptionEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class SeedOfCorruption extends Mechanic {

    private int numOfTargets;

    public SeedOfCorruption(Boss owner) {
        super("Seed of Corruption", owner.getDamage(), 0.1f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {

        if(getOwner().getEnemies().getDebuffLessRaidMembers("Seed of Corruption Effect").size() == getOwner().getEnemies().getNumOfAlive()) {
            ArrayList<RaidMember> temp = getOwner().getEnemies().getRandomRaidMember(
                    numOfTargets,getOwner().getEnemies().getDebuffLessRaidMembers("Seed of Corruption Effect"));
            AudioManager.playSFX(getAssets().getSound(getAssets().debuffSFX), false);
            for (int i = 0; i < temp.size(); i++) {
                SeedOfCorruptionEffect seedOfCorruptionEffect = new SeedOfCorruptionEffect(getOwner());
                temp.get(i).addStatusEffect(seedOfCorruptionEffect);
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
