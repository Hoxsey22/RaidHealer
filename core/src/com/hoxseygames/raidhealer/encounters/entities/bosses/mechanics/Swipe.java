package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/28/2017.
 */

public class Swipe extends Mechanic {

    public Swipe(Boss owner) {
        super("Swipe", owner.getDamage(), 1.5f, owner);
    }

    public Swipe(Boss owner, float speed) {
        super("Swipe", owner.getDamage(), speed, owner);
    }

    @Override
    public void action() {
        if(getMainTank().isDead() && getOffTank().isDead())    {
            ArrayList<RaidMember> randoms = getRaid().getRandomRaidMember(2);
            randoms.get(0).takeDamage(getDamage());
            randoms.get(1).takeDamage(getDamage()/2);
            return;
        }

        if(getMainTank().isDead())    {

            getOffTank().takeDamage(getDamage());
            getRaid().getRandomRaidMember(1).get(0).takeDamage(getDamage()/2);
            return;
        }

        if(getOffTank().isDead())    {
            getMainTank().takeDamage(getDamage());
            getRaid().getRandomRaidMember(1).get(0).takeDamage(getDamage()/2);
            return;
        }
        getMainTank().takeDamage(getDamage());
        getOffTank().takeDamage(getDamage()/2);

    }
}
