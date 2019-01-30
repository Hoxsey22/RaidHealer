package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BurnEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/4/2017.
 */

public class Pyroblast extends Mechanic {

    public Pyroblast(Boss owner) {
        super("Pyroblast", 50, 6f, owner);
        setAnnounce();
    }

    public Pyroblast(Boss owner, float speed) {
        super("Pyroblast", 50, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getOwner().getAssets().getSound(getOwner().getAssets().bigFireballSFX),false);
        ArrayList<RaidMember> selected = getRaid().getRandomRaidMember(1);
        if(selected != null)    {
            selected.get(0).takeDamage(getDamage());
            if(CriticalDice.roll(35,100,0))    {
                selected.get(0).addStatusEffect(new BurnEffect(getOwner()));
            }
        }
    }
}
