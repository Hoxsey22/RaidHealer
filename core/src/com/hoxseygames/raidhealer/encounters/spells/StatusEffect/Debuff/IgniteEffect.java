package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 11/29/2017.
 */

public class IgniteEffect extends Debuff {
    /**
     */
    public IgniteEffect(Boss owner) {
        super(owner,
                2,
                "Ignite Effect",
                "Dispellable: Burns the target over time and increases the damage over time. It will combust when " +
                        "dispelled or falls off.",
                owner.getAssets().getTexture(owner.getAssets().igniteIcon), //need to change the icon
                12f,
                3f,
                5,
                true);
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
        setModValue(getModValue()+1);
    }

    @Override
    public int modifyOutput(int output) {
        return output;
    }

    @Override
    public void remove() {
        if (isDispelled)
            getOwner().getEnemies().takeDamage(20);
        else
            getOwner().getEnemies().takeDamage(30);

        AudioManager.playSFX(getAssets().getSound(getAssets().explosionSFX), false);
        super.remove();
    }
}
