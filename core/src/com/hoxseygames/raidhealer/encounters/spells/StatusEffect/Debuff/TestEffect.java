package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 11/29/2017.
 */

public class TestEffect extends Debuff {

    /**
     */
    public TestEffect(Boss owner) {
        super(owner,
                5,
                "Test Effect",
                "Test.",
                owner.getAssets().getTexture(owner.getAssets().poisonIcon),
                900f,
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
    public void applyEffect(){
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
