package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.spells.CriticalDice;

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
                owner.getAssets().getTexture(owner.getAssets().renewIcon),
                10f,
                2f,
                5
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
