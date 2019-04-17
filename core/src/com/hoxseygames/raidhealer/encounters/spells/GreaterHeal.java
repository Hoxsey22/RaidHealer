package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 6/18/2017.
 */
class GreaterHeal extends Heal {

    public GreaterHeal(Player player, Assets assets) {
        super(player,
                "Greater Heal",
                "Heals an ally unit for 70hp, but at the cost of a slow cast time.",
                assets.getTexture(assets.greaterHealerIcon),
                6,
                2f,
                70,
                2.2f,
                assets);
    }
}
