package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.Castable;

import java.util.Random;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class HolyNova extends Castable {

    private final int MIN_NUM_OF_TARGETS = 3;
    private Random chainHealChance;

    public HolyNova(Player player, Assets assets) {
        super(player,
                "Holy Nova",
                "An explosion of holy light that heals several ally units for a moderate amount.",
                4,
                1.8f,
                25,
                3.5f,
                1f,
                assets.getSound(assets.holyNovaSFX),
                assets);
        setDescription("Heals the targeted ally unit and 4 other injured ally unit for "+getOutput()+"hp.");
        setImage(getAssets().getTexture(getAssets().holyNovaIcon));
        setNumOfTargets(MIN_NUM_OF_TARGETS);
        chainHealChance = new Random();
    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();
        checkHasteBuild();
        if(getOwner().getTalentTree().getTalent(TalentTree.SUPER_NOVA).isSelected())    {
            setNumOfTargets(4);
        }
        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            setOutput((int)(getOutput()*0.8f));
        }
        if(getOwner().getTalentTree().getTalent(TalentTree.RENEWING_NOVA).isSelected())  {
            setCooldown(4f);
            setNumOfTargets(getNumOfTargets()+1);
        }
    }

    @Override
    public void applySpell(RaidMember target)    {
        getRandomTargets(target);

        if(getOwner().getTalentTree().getTalent(TalentTree.CRITICAL_HEALER_II).isSelected())    {
            for(int i = 0; i < getTargets().size(); i++) {
                applyCriticalHealerII(getTargets().get(i),getOutput());
            }
            applyCriticalHealerII(target, getOutput());
        }
        else if(getOwner().getTalentTree().getTalent(TalentTree.RENEWING_NOVA).isSelected())  {
            for(int i = 0; i < getTargets().size(); i++) {
                applyRenewingNova(getTargets().get(i));
                getTargets().get(i).receiveHealing((int)(getOutput()*0.2f), getCriticalChance().isCritical());
            }
            applyRenewingNova(target);
            target.receiveHealing((int)(getOutput()*0.2f), getCriticalChance().isCritical());
        }
        else if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())   {
            for(int i = 0; i < getTargets().size(); i++) {
                applyMasteringHealing(getTargets().get(i), (int)(getOutput()*(1-((i+1)*0.05))));
            }
            applyMasteringHealing(target, getOutput());

        }
        else    {
            target.receiveHealing(getOutput(), getCriticalChance().isCritical());
            for(int i = 0; i < getTargets().size(); i++) {
                getTargets().get(i).receiveHealing(getOutput(), getCriticalChance().isCritical());
            }
        }

        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected()) {
            for(int i = 0; i < getTargets().size(); i++) {
                applyAtonement(getTargets().get(i));
            }
            applyAtonement(target);
        }
    }

    @Override
    protected void getRandomTargets(RaidMember currentTarget) {
        if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())   {
            int roll = chainHealChance.nextInt(100);
            if(roll > 85)    {
                targets = getOwner().getRaid().getRaidMembersWithLowestHp(numOfTargets + 3, currentTarget);
            }
            else if(roll > 60)   {
                targets = getOwner().getRaid().getRaidMembersWithLowestHp(numOfTargets+2,currentTarget);
            }
            else if(roll > 10)   {
                targets = getOwner().getRaid().getRaidMembersWithLowestHp(numOfTargets+1, currentTarget);
            }
            else    {
                super.getRandomTargets(currentTarget);
            }

        }
        else {
            super.getRandomTargets(currentTarget);
        }
    }

    @Override
    public void resetDefault()  {
        super.resetDefault();
        setNumOfTargets(MIN_NUM_OF_TARGETS);
    }
}
