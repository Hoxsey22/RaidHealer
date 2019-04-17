package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.Castable;

/**
 * Created by Hoxsey on 8/31/2017.
 */

public class Smite extends Castable {

    /**
     * @param player
     * @param assets
     */
    public Smite(Player player, Assets assets) {
        super(player, "Smite",
                "Deals 5 damage to the boss and heals the most injured ally unit for 5hp.",
                assets.getTexture(assets.smiteIcon),
                7,
                1.25f,
                5,
                0.5f,
                0.5f,
                assets.getSound(assets.smiteSFX),
                assets);
        checkTalents();

    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();

        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            setOutput(getOutput()+5);
            setDescription("Deals "+getOutput()+" damage to the boss and heals all ally units with atonement " +
                    "buff for "+getOutput()+"hp. If atonement isn't present, the most injured ally unit will be " +
                    "healed for "+getOutput()+"hp.");
        }
        checkHasteBuild();
    }

    @Override
    public void applySpell(RaidMember target) {
        //RaidMember lowest = getOwner().getRaid().getRaidMemberWithLowestHp();
        int newOutput = getOwner().getBoss().takeDamage(getOutput(), getCriticalChance().isCritical());

        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            if(getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").size() > 0) {
                if (getOwner().getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected()) {
                    for (int i = 0; i < getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").size(); i++) {
                        applyCriticalHealerII(getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").get(i), newOutput);
                    }
                } else {
                    for (int i = 0; i < getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").size(); i++) {
                        getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").get(i).receiveHealing(newOutput, getCriticalChance().isCritical());
                    }
                }
            }
            else {
                getOwner().getRaid().getRaidMemberWithLowestHp().receiveHealing(newOutput, getCriticalChance().isCritical());
            }
        }
        else    {
            getOwner().getRaid().getRaidMemberWithLowestHp().receiveHealing(newOutput, getCriticalChance().isCritical());
        }
    }


}
