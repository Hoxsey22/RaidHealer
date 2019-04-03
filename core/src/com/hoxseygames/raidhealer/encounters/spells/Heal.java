package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.Castable;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class Heal extends Castable {


    public Heal(Player player, Assets assets) {
        super(player,
                "Heal",
                "An efficient heal that heal an ally unit for a moderate amount.",
                0,
                1.5f,
                40,
                1f,
                0.5f,
                assets.getSound(assets.healSFX),
                assets);
        setDescription("Heals an ally unit for "+getOutput()+"hp.");
        setImage(getAssets().getTexture(getAssets().healIcon));
    }

    @Override
    public void applySpell(RaidMember target) {
        int currentOutput = getOutput();
        if(getOwner().isHolyShockIncrease())   {
            currentOutput = getOutput() + (int)(getOutput()*0.5);
            getOwner().setHolyShockIncrease(false);
        }
        if(getOwner().getTalentTree().getTalent(TalentTree.LIFEBOOM).isSelected())   {
            if(target.getStatusEffectList().contains("Lifeboom Effect"))   {
                currentOutput = currentOutput + (int)(getOutput()*0.3);
            }
        }

        if(getOwner().getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected())    {
            applyCriticalHealerII(target, currentOutput);
        }
        else if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())   {
            applyMasteringHealing(target, currentOutput);
        }
        else    {
            target.receiveHealing(currentOutput, getCriticalChance().isCritical());
        }
        
        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            applyAtonement(target);
        }

    }


    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();
        checkHasteBuild();
    }
}
