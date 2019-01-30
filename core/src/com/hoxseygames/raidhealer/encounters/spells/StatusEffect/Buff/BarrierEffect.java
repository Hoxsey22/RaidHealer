package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class BarrierEffect extends Buff {
    /**
     * @param owner       : The player that owns this status effect.
     */
    public BarrierEffect(Player owner) {
        super(owner,
                1,
                "Barrier",
                "An absorb shield.",
                owner.getAssets().getTexture(owner.getAssets().barrierIcon),
                555f,
                0.1f,
                0
        );
    }

    @Override
    public void startConditions() {
    }

    @Override
    public void additionalConditions() {
        if(getTarget().getShield() < 1)   {
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
