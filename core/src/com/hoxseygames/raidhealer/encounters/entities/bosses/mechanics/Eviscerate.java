package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class Eviscerate extends Mechanic {

    @SuppressWarnings("unused")
    public Eviscerate(Boss owner) {
        super("Eviscerate", 20, 2f, owner);
    }

    public Eviscerate(Boss owner, float speed)   {
        super("Eviscerate", 60, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> random  = getRaid().getRandomRaidMember(1);
        AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
        if(random.get(0) != null) {
            random.get(0).takeDamage(getDamage());
            if(CriticalDice.roll(60,100,1))    {
                random.get(0).addStatusEffect(new BleedEffect(getOwner()));
            }
        }
    }
}
