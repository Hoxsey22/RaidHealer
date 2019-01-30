package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.CorruptionEffect;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class BlanketCorruption extends Mechanic{

    @SuppressWarnings("unused")
    public BlanketCorruption(Boss owner) {
        super("Blanket Corruption", 0, 65f, owner);
        setAnnounce();
    }

    public BlanketCorruption(Boss owner, float speed) {
        super("Blanket Corruption", 0, speed, owner);
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().debuffSFX), false);
        for(int i = 0; i < getOwner().getEnemies().getRaidMembers().size(); i++)   {
            CorruptionEffect corruptionEffect = new CorruptionEffect(getOwner(), true);
            corruptionEffect.setModValue(5);

            getOwner().getEnemies().getRaidMembers().get(i).addStatusEffect(corruptionEffect);
        }
    }
}
