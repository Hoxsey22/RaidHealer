package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.PoisonEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/21/2017.
 */

public class Sting extends Mechanic {

    private int numOfTargets;

    public Sting(Boss owner) {
        super("Sting", 15, 15f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> temp  = getOwner().getEnemies().getRandomRaidMember(numOfTargets);
        AudioManager.playSFX(getAssets().getSound(getAssets().finishImpactSFX), false);
        for (int i = 0; i < temp.size(); i++)   {
            if(temp.get(i) != null) {
                AudioManager.playSFX(getAssets().getSound(getAssets().stabSFX), false);
                temp.get(i).takeDamage(getDamage());
                temp.get(i).addStatusEffect(new PoisonEffect(getOwner()));
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
