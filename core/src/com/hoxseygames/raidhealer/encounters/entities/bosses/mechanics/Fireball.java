package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BurnEffect;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hoxsey on 8/28/2017.
 */

public class Fireball extends Mechanic {

    private final Random dice;

    @SuppressWarnings("unused")
    public Fireball(Boss owner) {
        super("Fireball", 20, 2f, owner);
        dice = new Random();
        setBgMech();
    }

    public Fireball(Boss owner, float speed) {
        super("Fireball", 20, speed, owner);
        dice = new Random();
        setBgMech();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().fireballSFX), false);
        ArrayList<RaidMember> random  = getRaid().getRandomRaidMember(1);
        random.get(0).takeDamage(getDamage());
        if(dice.nextInt(100) > 95)    {
            random.get(0).addStatusEffect(new BurnEffect(getOwner()));
        }
    }
}
