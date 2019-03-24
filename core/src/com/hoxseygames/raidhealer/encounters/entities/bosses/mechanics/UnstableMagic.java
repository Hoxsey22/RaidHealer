package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.UnstableMagicEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class UnstableMagic extends Mechanic {

    private int numOfTargets;
    private int totalCounter;
    private int dpsCounter;
    private int healerCounter;

    public UnstableMagic(Boss owner) {
        super("Unstable Magic", 0, 8f, owner);
        numOfTargets = 1;
        setAnnounce();
        totalCounter = 0;
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().bigDebuffSFX), false);

        if(totalCounter < 5)    {
            totalCounter++;
            ArrayList<RaidMember> dps = getRaid().getDebuffLessRaidMembers("Unstable Magic Effect", getRaid().dpsAlive());
            if(dps != null)    {
                dps.get(0).addStatusEffect(new UnstableMagicEffect(getOwner()));
            }
            else {
                ArrayList<RaidMember> temp = getRaid().getDebuffLessRaidMembers("Unstable Magic Effect");
                if(temp != null) {
                    temp.get(0).addStatusEffect(new UnstableMagicEffect(getOwner()));
                }
            }
        } else {
            ArrayList<RaidMember> healers = getRaid().getDebuffLessRaidMembers("Unstable Magic Effect", getRaid().healersAlive());
            if(healers != null)    {
                healers.get(0).addStatusEffect(new UnstableMagicEffect(getOwner()));
            }
            else {
                ArrayList<RaidMember> temp = getRaid().getDebuffLessRaidMembers("Unstable Magic Effect");
                if(temp != null) {
                    temp.get(0).addStatusEffect(new UnstableMagicEffect(getOwner()));
                }
            }
        }
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
