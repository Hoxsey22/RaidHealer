package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 11/29/2017.
 */

public class AgonyEffect extends Debuff {

    /**
     */
    public AgonyEffect(Boss owner) {
        super(owner,
                5,
                "Agony Effect",
                "Agony will cause the target to take a huge amount of damage over time.",
                owner.getAssets().getTexture(owner.getAssets().agonyIcon),
                15f,
                2f,
                30,
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
        getTarget().takeDamage(getModValue());
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
