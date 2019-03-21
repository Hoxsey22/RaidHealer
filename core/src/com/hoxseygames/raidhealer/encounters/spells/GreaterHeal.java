package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;

/**
 * Created by Hoxsey on 6/18/2017.
 */
class GreaterHeal extends Heal {

    public GreaterHeal(Player player, Assets assets) {
        super(player, assets);
        setName("Greater Heal");
        setOutput(60);

        setMIN_OUTPUT(70);

        setDescription("Heals an ally unit for "+getOutput()+"hp.");

        setImage(getAssets().getTexture(getAssets().greaterHealerIcon));

        setCostPercentage(2.2f);

        setMIN_COST(2.2f);

        setMIN_CAST_TIME(2f);

        setCastTime(getMIN_CAST_TIME());

        setLevelRequirement(6);
    }
}
