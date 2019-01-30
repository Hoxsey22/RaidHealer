package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class Bite extends Mechanic {

    @SuppressWarnings("unused")
    public Bite(Boss owner) {
        super("Bite", 20, 2f, owner);
    }

    public Bite(Boss owner, float speed)   {
        super("Bite", 20, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> random  = getRaid().getRandomRaidMember(1);
        AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
        if(random.get(0) != null)
            random.get(0).takeDamage(getDamage());
    }
}
