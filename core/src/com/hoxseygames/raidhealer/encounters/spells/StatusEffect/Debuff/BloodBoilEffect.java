package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 11/29/2017.
 */

@SuppressWarnings("unused")
public class BloodBoilEffect extends Debuff {

    /**
     */
    @SuppressWarnings("unused")
    public BloodBoilEffect(Boss owner) {
        super(owner,
                5,
                "Blood Boil Effect",
                "Blood Boil was do little raid damage when the target is healed to full.",
                owner.getAssets().getTexture(owner.getAssets().boilIcon),
                20f,
                0.1f,
                10,
                false);
    }

    @Override
    protected void startConditions() {
    }

    @Override
    public void additionalConditions() {
    }

    @Override
    public void applyEffect() {
        if(getTarget().getHealthPercent() > 0.99)    {
            getOwner().getEnemies().takeDamage(getModValue());
        }
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
