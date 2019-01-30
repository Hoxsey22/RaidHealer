package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.PoisonEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class PoisonStab extends Mechanic {

    private int numOfTargets;

    public PoisonStab(Boss owner) {
        super("Poison Stab", owner.getDamage(), 19f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> temp  = getOwner().getEnemies().getRandomRaidMember(numOfTargets);
        AudioManager.playSFX(getAssets().getSound(getAssets().magicStabSFX), false);
        for (int i = 0; i < temp.size(); i++)   {
            temp.get(i).takeDamage(getDamage());
            temp.get(i).addStatusEffect(new PoisonEffect(getOwner(), 15,0.2f));
        }
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
