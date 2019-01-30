package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 12/10/2017.
 */

public class HealingAbsorbEffect extends Debuff {

    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner: The owner of the debuff.
     * @param modValue: The value of the debuff.
     */
    HealingAbsorbEffect(Boss owner, int modValue) {
        super(owner,
                3,
                "Healing Absorb Effect",
                "A healing absorb shield that diminishes when healed. A member cannot be healed " +
                        "until the healing absorb shield is removed.",
                owner.getAssets().getTexture(owner.getAssets().healingAbsorbIcon),
                300f,
                0.1f,
                modValue,
                false);
    }

    @Override
    public void startConditions() {
        getTarget().applyHealingAbsorb(getModValue());
    }

    @Override
    public void additionalConditions() {
        if(getTarget().getHealingAbsorb() < 1)   {
            remove();
        }
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }

}
