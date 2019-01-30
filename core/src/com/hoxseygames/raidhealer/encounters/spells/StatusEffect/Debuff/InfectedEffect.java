package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.ZombieHorde;

/**
 * Created by Hoxsey on 12/10/2017.
 */

public class InfectedEffect extends Debuff {

    private ZombieHorde zombieHorde;

    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner       : The owner of the buff.
     */
    public InfectedEffect(Boss owner) {
        super(owner,
                3,
                "Infected Effect",
                "Infected target increases the damage and the health of the Zombie Horde if the target dies.",
                owner.getAssets().getTexture(owner.getAssets().diseaseIcon),
                600f,
                0.1f,
                0,
                false);
        zombieHorde = (ZombieHorde) owner;
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

    @Override
    public void remove() {
        if(!zombieHorde.isDead()) {
            zombieHorde.setDamage(zombieHorde.getDamage()+1);
            getOwner().receiveHealing(100, false);
        }
        super.remove();
    }

}
