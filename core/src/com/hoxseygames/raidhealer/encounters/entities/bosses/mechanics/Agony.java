package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.AgonyEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class Agony extends Mechanic {

    private int numOfTargets;

    public Agony(Boss owner) {
        super("Agony", 0, 18f, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    public Agony(Boss owner, float speed) {
        super("Agony", 0, speed, owner);
        numOfTargets = 1;
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().debuffSFX), false);
        ArrayList<RaidMember> temp  = getOwner().getEnemies().getRandomRaidMember(numOfTargets);

        for (int i = 0; i < temp.size(); i++)   {
            AgonyEffect agonyEffect = new AgonyEffect(getOwner());

            temp.get(i).addStatusEffect(agonyEffect);
        }
    }

// --Commented out by Inspection START (5/29/2018 8:12 PM):
//    public int getNumOfTargets() {
//        return numOfTargets;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:12 PM)

// --Commented out by Inspection START (5/29/2018 8:12 PM):
//    public void setNumOfTargets(int numOfTargets) {
//        this.numOfTargets = numOfTargets;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:12 PM)
}
