package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 6/18/2017.
 */
class FlashHeal extends Heal {


    public FlashHeal(Player player, Assets assets) {
        super(player, assets);
        setName("Flash Heal");

        setDescription("Heals an ally unit quickly for "+getOutput()+"hp, but at the cost of more mana.");

        setImage(getAssets().getTexture(getAssets().flashIcon));

        setMIN_CAST_TIME(0.7f);

        setCostPercentage(2.2f);

        setMIN_COST(2.2f);

        setCastTime(getMIN_CAST_TIME());

        setCriticalChance(getMIN_CRITICAL());

        setLevelRequirement(5);
    }

    @Override
    public void applySpell(RaidMember target) {
        super.applySpell(target);

        if(getOwner().getTalentTree().getTalent(TalentTree.RENEWING_NOVA).isSelected())    {
            applyRenewingNova(target);
        }
    }
}
