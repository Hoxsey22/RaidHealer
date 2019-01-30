package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class LifeboomEffect extends Buff {

    private int totalBoom;
    /**
     * @param owner       : The player that owns this status effect.
     * @param duration    : The time of which the status effect will last.
     * @param speed       : The time of which the status effect will apply effect.
     * @param modValue    : The mod value that will change a specific stat.
     */
    public LifeboomEffect(Player owner, float duration, float speed, int modValue) {
        super(owner,
                1,
                "Lifeboom Effect",
                "Renew is a periodic heal.",
                owner.getAssets().getTexture(owner.getAssets().lifeboomIcon),
                duration,
                speed,
                modValue
        );
        totalBoom = 0;
    }

    @Override
    public void additionalConditions() {

    }

    @Override
    public void applyEffect() {
        totalBoom = totalBoom + getTarget().receiveHealing(getModValue(), CriticalDice.roll(getOwner().getCriticalChance()));
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }

    @Override
    public void startConditions() {

    }

    @Override
    public void remove() {
        if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())    {
            getOwner().getSpellBar().getSpell(0).applyMasteringHealing(getTarget(), getModValue());
        }
        else
            getTarget().receiveHealing((int)((float)totalBoom/2f), CriticalDice.roll(getOwner().getCriticalChance()));
        super.remove();
    }
}
