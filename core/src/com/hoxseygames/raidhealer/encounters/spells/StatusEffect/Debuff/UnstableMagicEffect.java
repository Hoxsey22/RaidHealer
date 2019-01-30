package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

import java.util.Random;

/**
 * Created by Hoxsey on 11/29/2017.
 */

public class UnstableMagicEffect extends Debuff {

    /**
     */
    public UnstableMagicEffect(Boss owner) {
        super(owner,
                5,
                "Unstable Magic Effect",
                "A surge of uncontrollable magic increases the unit's role, but the unit is damage by this uncontrollable magic.",
                owner.getAssets().getTexture(owner.getAssets().unstableMagicIcon),
                600f,
                2.5f,
                10,
                false);
    }

    @Override
    public void additionalConditions() {

    }

    @Override
    public void applyEffect() {
        getTarget().takeDamage(new Random().nextInt(18)+2);
    }

    @Override
    public int modifyOutput(int output) {
        switch (getTarget().getRole())  {
            case "dps":
                return output;
            case "healer":
                return output + (int)((float)output*0.2f);
            case "tank":
                return output - (int)((float)output*0.2f);
        }
        return output;
    }

    @Override
    public void startConditions() {
        switch (getTarget().getRole())  {
            case "Dps":
                getTarget().setDamage(getTarget().getDamage() * 4);
                break;
            case "Healer":
                getTarget().setDamage(getTarget().getDamage() * 3);
                setType(HEALING_TAKEN_MODIFIER);
                break;
            case "Tank":
                getTarget().setDamage(getTarget().getDamage() * 4);
                setType(DAMAGE_TAKEN_MODIFIER);
                break;
        }

    }
}
