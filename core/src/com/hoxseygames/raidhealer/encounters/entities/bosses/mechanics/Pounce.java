package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 7/26/2017.
 */

public class Pounce extends Mechanic {

    private int numOfTargets;

    public Pounce(Boss owner) {
        super("Pounce",30,4f,owner);
        numOfTargets = 3;
        setAnnounce();
    }

    public Pounce(Boss owner, float speed) {
        super("Pounce",30,speed,owner);
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> temp  = getRaid().getRandomRaidMember(numOfTargets);

        for (int i = 0; i < temp.size(); i++)   {
            if(temp.get(i) != null) {
                AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
                temp.get(i).takeDamage(getDamage());
                temp.get(i).addStatusEffect(new BleedEffect(getOwner()));
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
