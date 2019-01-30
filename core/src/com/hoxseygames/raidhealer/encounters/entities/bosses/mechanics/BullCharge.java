package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/4/2017.
 */

public class BullCharge extends Mechanic {


    public BullCharge(Boss owner) {
        super("Bull Charge", 30, 6f, owner);
        setAnnounce();
    }

    public BullCharge(Boss owner, float speed) {
        super("Bull Charge", 30 , speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().chargeSFX), false);
        ArrayList<RaidMember> selected = getRaid().getRandomRaidMember(4);
        if(selected != null)    {
            selected.get(0).takeDamage(getDamage());
        }
    }
}
