package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 11/29/2017.
 */

public class CorruptionEffect extends Debuff {

    /**
     */
    public CorruptionEffect(Boss owner) {
        super(owner,
                4,
                "Corruption Effect",
                "Corruption will cause the target to take a moderate amount of damage over time.",
                owner.getAssets().getTexture(owner.getAssets().corruptionIcon),
                400f,
                3f,
                10,
                true);
    }

    /**
     */
    public CorruptionEffect(Boss owner, boolean dispellable) {
        super(owner,
                4,
                "Corruption Effect",
                "Corruption will cause the target to take a moderate amount of damage over time.",
                owner.getAssets().getTexture(owner.getAssets().corruptionIcon),
                400f,
                3f,
                10,
                dispellable);
    }

    @Override
    public void startConditions() {

    }

    @Override
    public void additionalConditions() {
    }

    @Override
    public void applyEffect() {
        getTarget().takeDamage(getModValue());
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
