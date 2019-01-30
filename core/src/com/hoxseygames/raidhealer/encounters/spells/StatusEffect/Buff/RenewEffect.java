package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class RenewEffect extends Buff {
    /**
     * @param owner       : The player that owns this status effect.
     * @param duration    : The time of which the status effect will last.
     * @param speed       : The time of which the status effect will apply effect.
     * @param modValue    : The mod value that will change a specific stat.
     */
    public RenewEffect(Player owner, float duration, float speed, int modValue) {
        super(owner,
                1,
                "Renew Effect",
                "Renew is a periodic heal.",
                owner.getAssets().getTexture(owner.getAssets().renewIcon),
                duration,
                speed,
                modValue
        );
    }

    @Override
    public void startConditions() {

    }

    @Override
    public void additionalConditions() {

    }

    @Override
    public void applyEffect() {
        if (getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected()) {
            getOwner().getSpellBar().getSpell(0).applyMasteringHealing(getTarget(), getModValue());
        } else {
            getTarget().receiveHealing(getModValue(), CriticalDice.roll(getOwner().getCriticalChance()));
        }
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
