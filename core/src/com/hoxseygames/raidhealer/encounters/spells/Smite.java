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
                "Smites a boss for a small amount of damage that will heal the most injured ally unit for a faction of the damage.",
                7,
                1.25f,
                5,
                0.5f,
                0.5f,
                assets.getSound(assets.smiteSFX),
                assets);
        setDescription("Damages the enemy and heals an ally unit with the lowest health for the damage dealt to the enemy.");
        setImage(getAssets().getTexture(getAssets().smiteIcon));
    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();

        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            setOutput(getOutput()+5);
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
