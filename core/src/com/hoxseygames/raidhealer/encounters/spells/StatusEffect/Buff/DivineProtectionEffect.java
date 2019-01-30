package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff;

import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class DivineProtectionEffect extends Buff {
    /**
     * @param owner       : The player that owns this status effect.
     */
    public DivineProtectionEffect(Player owner) {
        super(owner,
                1,
                "Divine Protection Effect",
                "Reduces all damage by 40% for 15 seconds.",
                owner.getAssets().getTexture(owner.getAssets().divineProtectionIcon),
                15f,
                0.1f,
                0
        );
        setType(DAMAGE_TAKEN_MODIFIER);
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
        return output - (int)((float)output*0.4f);
    }
}
