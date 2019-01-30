package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 1/23/2018.
 */

public class VenomEffect extends Debuff {
    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner       : The owner of the buff.
     */
    public VenomEffect(Boss owner) {
        super(owner,
                11,
                "Venom Effect",
                "Envenoms the target causing one massive surge of damage once.",
                owner.getAssets().getTexture(owner.getAssets().venomIcon),
                9f,
                9f,
                80,
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
        return 0;
    }
}
