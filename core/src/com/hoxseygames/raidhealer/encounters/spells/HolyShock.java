package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.InstantCast;

/**
 * Created by Hoxsey on 12/6/2017.
 */

public class HolyShock extends InstantCast {
    /**
     * @param player
     * @param assets
     */
    public HolyShock(Player player, Assets assets) {
        super(player, "Holy Shock",
                "Instantly shocks an ally unit with holy light for a moderate amount and also empowers the next Heal by 50%.",
                7,
                1,
                35,
                2f,
                12f,
                assets.getSound(assets.holyShockSFX),
                assets);
        setDescription("Heals an ally unit instantly for "+getOutput()+"hp and grants the user a buff that will increase output of the next Heal by 200%.");
        setImage(getAssets().getTexture(getAssets().criticalHealer2Icon));
    }

    @Override
    public void applySpell(RaidMember target) {

        if(getOwner().getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected())    {
            applyCriticalHealerII(target, getOutput());
        }
        else if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())   {
            applyMasteringHealing(target, getOutput());
        }
        else    {
            target.receiveHealing(getOutput(), getCriticalChance().isCritical());
        }

        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            applyAtonement(target);
        }


        getOwner().setHolyShockIncrease(true);

    }


    @Override
    public void checkTalents() {
        resetDefault();
        checkCriticalHealer();
    }
}
