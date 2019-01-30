package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 12/10/2017.
 */

public class StoneSkinEffect extends Debuff {

    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner       : The owner of the buff.
     */
    public StoneSkinEffect(Boss owner) {
        super(owner,
                3,
                "Stone Skin Effect",
                "Stone Skin prevents the target from dying if their health is above 10%.",
                owner.getAssets().getTexture(owner.getAssets().stoneSkinIcon),
                600f,
                0.1f,
                0,
                false);
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
        if(output >= getTarget().getHp())    {
            if(getTarget().getHealthPercent() > 0.1f)    {
                return getTarget().getHp() - 1;
            }
        }
        return output;
    }

}
