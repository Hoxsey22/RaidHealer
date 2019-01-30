package com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 1/5/2018.
 */

public class WebEffect extends HealingAbsorbEffect {

    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner    : The owner of the debuff.
     */
    public WebEffect(Boss owner) {
        super(owner, 50);
        setName("Web Effect");
        setDescription("Webs a target causing healing absorption on the target. Healing will reduce the healing " +
                "absorption and prevent the target from being dinner.");
        setIcon(owner.getAssets().getTexture(owner.getAssets().webIcon));
    }


    /**
     * A debuff is a negative status effect that is commonly from a boss and is
     * put on a raid member.
     *
     * @param owner    : The owner of the debuff.
     * @param modValue : The value of the debuff.
     */
    public WebEffect(Boss owner, int modValue) {
        super(owner, modValue);
        setName("Web Effect");
        setIcon(owner.getAssets().getTexture(owner.getAssets().webIcon));
        setDescription("Web is a healing absorb place on a member and if not healed in time," +
                " they will be lunch.");
    }
}
