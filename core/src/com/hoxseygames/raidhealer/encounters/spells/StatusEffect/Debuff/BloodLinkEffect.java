package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 11/29/2017.
 */

@SuppressWarnings("unused")
public class BloodLinkEffect extends Debuff {

    /**
     */
    public BloodLinkEffect(Boss owner) {
        super(owner,
                1,
                "Blood Link Effect",
                "A bleed will cause the target to take damage over time and all damage done to the target will be increased.",
                owner.getAssets().getTexture(owner.getAssets().bleedIcon),
                300f,
                2f,
                owner.getDamage(),
                false);
    }

    @Override
    public void startConditions() {

    }

    @Override
    public void additionalConditions() {
    }

    @Override
    public void applyEffect() {
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
