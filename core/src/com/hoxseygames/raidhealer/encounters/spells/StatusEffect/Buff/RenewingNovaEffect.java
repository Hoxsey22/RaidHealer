package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class RenewingNovaEffect extends Buff {
    /**
     * @param owner       : The player that owns this status effect.
     */
    public RenewingNovaEffect(Player owner) {
        super(owner,
                1,
                "Renewing Nova",
                "A periodic heal.",
                owner.getAssets().getTexture(owner.getAssets().renewingNovaIcon),
                8.75f,
                1.25f,
                8
        );
    }

    /**
     * @param owner       : The player that owns this status effect.
     * @param duration    : The time of which the status effect will last.
     * @param speed       : The time of which the status effect will apply effect.
     * @param modValue    : The mod value that will change a specific stat.
     */
    public RenewingNovaEffect(Player owner, float duration, float speed, int modValue) {
        super(owner,
                1,
                "Renewing Nova",
                "A periodic heal.",
                owner.getAssets().getTexture(owner.getAssets().renewIcon),
                duration,
                speed,
                modValue
        );
    }

    @Override
    public void startConditions() {
        if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected())  {
            setDuration(7f);
            setSpeed(1f);
        }

    }

    @Override
    public void additionalConditions() {

    }

    @Override
    public void applyEffect() {
        getTarget().receiveHealing(getModValue(), CriticalDice.roll(getOwner().getCriticalChance()));
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
