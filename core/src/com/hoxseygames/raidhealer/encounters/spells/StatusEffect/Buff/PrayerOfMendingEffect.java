package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class PrayerOfMendingEffect extends Buff{

    private int jumpCount;
    /**
     * */
    public PrayerOfMendingEffect(Player owner) {
        super(owner, 4, "Prayer of Mending", "When the target takes damage, the target will be healed and Prayer of Mending" +
                "will jump to a new target.", owner.getAssets().getTexture(owner.getAssets().prayerOfMendingIcon), -1, -1, 20);
        jumpCount = 5;
    }

    public PrayerOfMendingEffect(Player owner, int modValue, int numOfTargets) {
        super(owner, 4, "Prayer of Mending", "When the target takes damage, the target will be healed and Prayer of Mending" +
                "will jump to a new target.", owner.getAssets().getTexture(owner.getAssets().prayerOfMendingIcon), 25f, 26f, modValue);
        jumpCount = numOfTargets;
    }

    @Override
    public void startConditions() {

    }

    @Override
    public void additionalConditions() {

    }

    @Override
    public void applyEffect() {
        AudioManager.playSFX(getAssets().getSound(getAssets().pomHitSFX), false);
        if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())    {
            getOwner().getSpellBar().getSpell(0).applyMasteringHealing(getTarget(), getModValue());
        }
        else {
            getTarget().receiveHealing(getModValue(), CriticalDice.roll(getOwner().getCriticalChance()));
        }

        jumpCount--;
        if(jumpCount > 0) {
            ArrayList<RaidMember> temp = getOwner().getRaid().getRandomRaidMember(1, getOwner().getRaid().getBuffLessRaidMembers(this.getName()));
            if(temp.size() < 1)    {
                return;
            }
            RaidMember newTarget = temp.get(0);

            removeFromParent();

            setTarget(newTarget);
            setParent(getTarget().getStatusEffectList());
            getTarget().addStatusEffect(this);
        }
        else {
            remove();
        }
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
