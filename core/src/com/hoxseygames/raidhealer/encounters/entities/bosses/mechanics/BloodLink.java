package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 8/4/2017.
 */

@SuppressWarnings("unused")
public class BloodLink extends Mechanic {

    public BloodLink(Boss owner) {
        super("Blood Link",owner.getDamage(),2f,owner);
    }

    public BloodLink(Boss owner, float speed) {
        super("Blood Link",owner.getDamage(),speed,owner);
        setBgMech();
    }

    @Override
    public void action() {
        if(getOwner().getTarget().isDead()){
            getOwner().setTarget(getOwner().getNextThreat());
        }
        getOwner().getTarget().takeDamage(getDamage());
        getOwner().getNextThreat().takeDamage(getDamage());
    }
}
