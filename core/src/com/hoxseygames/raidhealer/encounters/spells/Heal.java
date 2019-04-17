package com.hoxseygames.raidhealer.encounters.spells;

import com.badlogic.gdx.graphics.Texture;
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
                "Heals an ally unit for 40hp.",
                assets.getTexture(assets.healIcon),
                0,
                1.5f,
                40,
                1f,
                0.5f,
                assets.getSound(assets.healSFX),
                assets);
    }

    // flash heal
    public Heal(Player player, String name, String description, Texture iconTexture, int levelRequirement, float castTime, float costPercentage, Assets assets) {
        super(player,
                name,
                description,
                iconTexture,
                levelRequirement,
                castTime,
                40,
                costPercentage,
                0.5f,
                assets.getSound(assets.healSFX),
                assets);
        setMIN_CAST_TIME(castTime);
        setMIN_COST(costPercentage);
        setCriticalChance(getMIN_CRITICAL());
    }

    // greater heal
    public Heal(Player player, String name, String description,  Texture iconTexture, int levelRequirement, float castTime, int output, float costPercentage, Assets assets) {
        super(player,
                name,
                description,
                iconTexture,
                levelRequirement,
                castTime,
                output,
                costPercentage,
                0.5f,
                assets.getSound(assets.healSFX),
                assets);
        setMIN_CAST_TIME(castTime);
        setMIN_COST(costPercentage);
        setCriticalChance(getMIN_CRITICAL());
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
