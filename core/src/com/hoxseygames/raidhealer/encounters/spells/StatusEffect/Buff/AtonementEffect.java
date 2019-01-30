package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class AtonementEffect extends Buff {
    /**
     * @param owner       : The player that owns this status effect.
     */
    public AtonementEffect(Player owner) {
        super(owner,
                1,
                "Atonement Effect",
                "Atonement effect will allow smite heals to heal all targets to be healed by smite.",
                owner.getAssets().getTexture(owner.getAssets().smiteIcon),
                13f,
                0.1f,
                0
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

    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }
}
