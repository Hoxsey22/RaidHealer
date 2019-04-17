package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.ChannelCast;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class Penance extends ChannelCast {

    int sfxIndex;

    public Penance(Player player, Assets assets) {
        super(player, "Penance",
                "Throws a volley of holy light at an ally unit. If atonement is present, Penance will be casted on the enemy unit" +
                        " dealing damage and healing all those with atonement.",
                assets.getTexture(assets.penanceIcon),
                9,
                2.5f,
                4,
                15,
                3f,
                8f,
                assets.getSound(assets.penanceTriggerSFX),
                true,
                assets);
        setDescription("Heals an ally unit for "+getOutput()+"hp 4 times.");
        checkTalents();

    }

    @Override
    public void applySpell(RaidMember target) {
        triggerSFX();
        int newOutput;
        if(!getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            target.receiveHealing(getOutput(), getCriticalChance().isCritical());
        }
        else {
            if (getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").size() > 0) {
                newOutput = (int)(getOwner().getBoss().takeDamage(getOutput(), getCriticalChance().isCritical())*0.7);

                if (getOwner().getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected()) {
                    for (int i = 0; i < getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").size(); i++) {
                        applyCriticalHealerII(getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").get(i), newOutput);
                    }
                } else {
                    for (int i = 0; i < getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").size(); i++) {
                        getOwner().getRaid().getBuffedRaidMembers("Atonement Effect").get(i).receiveHealing(newOutput, false);
                    }
                }
            } else {
                if (getOwner().getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected()) {
                    applyCriticalHealerII(target, getOutput());
                } else {
                    target.receiveHealing(getOutput(), getCriticalChance().isCritical());
                }
            }
        }

    }

    public void triggerSFX()    {
        switch ((sfxIndex%4)) {
            case 0:
                AudioManager.playSFX(getAssets().getSound(getAssets().penanceHit1SFX), false);
                break;
            case 1:
                AudioManager.playSFX(getAssets().getSound(getAssets().penanceHit2SFX), false);
                break;
            case 2:
                AudioManager.playSFX(getAssets().getSound(getAssets().penanceHit3SFX), false);
                break;
            case 3:
                AudioManager.playSFX(getAssets().getSound(getAssets().penanceHit4SFX), false);
                break;
        }
        sfxIndex++;
    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();
        if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected())    {
            setCastTime(getMIN_CAST_TIME() - 0.25f);
            setTicksPerCast(5);
        }
        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            setDescription("Deals "+(int)(getOutput()*0.7)+" damage 4 times to the boss and all ally" +
                    " units with atonement buff will be healed for "+(int)(getOutput()*0.7)+"hp each time" +
                    " the boss takes damage from penance.");
        }
    }
}
