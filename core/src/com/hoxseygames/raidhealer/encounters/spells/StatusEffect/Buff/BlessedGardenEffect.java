package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class BlessedGardenEffect extends Buff {
    /**
     * @param owner       : The player that owns this status effect.
     */
    public BlessedGardenEffect(Player owner) {
        super(owner,
                1,
                "Blessed Garden Effect",
                "Renew is a periodic heal.",
                owner.getAssets().getTexture(owner.getAssets().blessedGardenIcon),
                15f,
                0.1f,
                0
        );
        setType(HEALING_TAKEN_MODIFIER);
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
        return output + (int)((float)output*0.4f);
    }
}
