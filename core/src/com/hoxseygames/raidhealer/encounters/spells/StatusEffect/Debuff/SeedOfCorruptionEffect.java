package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 11/29/2017.
 */

public class SeedOfCorruptionEffect extends Debuff {

    private int numOfTargets;
    /**
     */
    public SeedOfCorruptionEffect(Boss owner) {
        super(owner,
                6,
                "Seed of Corruption Effect",
                "Dispellable: Seed of Corruption's damage will progressively increase until dispelled. Once dispelled, 2 other targets will receive " +
                        "corruption effects.",
                owner.getAssets().getTexture(owner.getAssets().seedOfCorruptionIcon),
                300f,
                1.5f,
                2,
                true);

        numOfTargets = 2;
    }

    @Override
    public void startConditions() {

    }

    @Override
    public void remove() {

        ArrayList<RaidMember> group = getOwner().getEnemies().getRandomRaidMember(
                2, getOwner().getEnemies().getBuffLessRaidMembers("Corruption Effect"));

        AudioManager.playSFX(getAssets().getSound(getAssets().bigDebuffSFX), false);
        getOwner().getEnemies().takeDamage(getModValue());

        for(int i = 0; i < group.size(); i++)   {
            CorruptionEffect corruptionEffect = new CorruptionEffect(getOwner());
            corruptionEffect.setModValue(15);
            corruptionEffect.setDispellable(false);

            group.get(i).addStatusEffect(corruptionEffect);
        }
        super.remove();
    }

    @Override
    public void additionalConditions() {
    }

    @Override
    public void applyEffect() {
        if(!getTarget().isDead()) {
            getTarget().takeDamage(getModValue());
            setModValue(getModValue() + 1);
        }
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }

    private ArrayList<RaidMember> getRandomGroup()   {
        return getOwner().getEnemies().getRandomRaidMember(numOfTargets,getOwner().getEnemies().getDebuffLessRaidMembers(this.getName()));
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }
}
