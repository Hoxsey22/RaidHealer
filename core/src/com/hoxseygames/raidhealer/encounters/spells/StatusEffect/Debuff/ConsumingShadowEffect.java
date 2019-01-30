package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 12/10/2017.
 */

public class ConsumingShadowEffect extends Debuff {

    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner       : The owner of the buff.
     */
    public ConsumingShadowEffect(Boss owner) {
        super(owner,
                3,
                "Consuming Shadow Effect",
                "Dispellable: Consuming Shadow will increase all damage taken by the target.",
                owner.getAssets().getTexture(owner.getAssets().swarmingShadowIcon),
                300f,
                0.1f,
                0,
                true);
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
        return output + (int)((float)output*0.9f);
    }

}
