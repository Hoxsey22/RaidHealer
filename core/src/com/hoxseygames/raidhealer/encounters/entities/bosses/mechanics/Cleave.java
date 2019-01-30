package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class Cleave extends Mechanic {

    private int numOfTargets;

    @SuppressWarnings("unused")
    public Cleave(Boss owner) {
        super("Cleave", 15, 5f, owner);
        numOfTargets = 2;
        setBgMech();
    }

    public Cleave(Boss owner, float speed) {
        super("Cleave", 15, speed, owner);
        numOfTargets = 2;
        setBgMech();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> raidMembers = getRaid().getRandomRaidMember(numOfTargets);
        for(int i = 0; i < raidMembers.size(); i++)   {
            raidMembers.get(i).takeDamage(getDamage());
        }
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
