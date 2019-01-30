package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.ZombieBiteEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/26/2017.
 */

public class ZombieBite extends Mechanic {

    private int numOfTargets;

    public ZombieBite(Boss owner) {
        super("Zombie Bite", 20, 2f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    public ZombieBite(Boss owner, float speed) {
        super("Zombie Bite", 20, speed, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> temp = getRaid().getRandomRaidMember(numOfTargets);
        AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
        for(int i = 0; i < temp.size(); i++) {
            temp.get(i).takeDamage(getDamage());
            temp.get(i).addStatusEffect(new ZombieBiteEffect(getOwner()));
        }
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
