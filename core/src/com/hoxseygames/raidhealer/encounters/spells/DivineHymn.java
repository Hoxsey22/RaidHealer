package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.ChannelCast;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class DivineHymn extends ChannelCast {

    public DivineHymn(Player player, Assets assets) {
        super(player, "Divine Hymn",
                "A glorious hymn is rang throughout the raid, healing all ally units several times.",
                7,
                4f,
                4,
                20,
                5f,
                100f,
                assets.getSound(assets.divineHymnSFX),
                true,
                assets);
        setDescription("Heals the majority of the raid for "+getOutput()+"hp every second for 4 seconds");
        setImage(getAssets().getTexture(getAssets().divineHymnIcon));
    }

    @Override
    public void applySpell(RaidMember target) {
        ArrayList<RaidMember> randoms = getOwner().getRaid().getRaidMembersWithLowestHp(7/*(int)(owner.getRaid().raidMembers.size()*0.7f)*/);

        for(int i = 0; i < randoms.size(); i++)   {
            if(getOwner().getTalentTree().getTalent(TalentTree.MASTERING_HEALING).isSelected())   {
                applyMasteringHealing(randoms.get(i), getOutput());
            }
            else {
                randoms.get(i).receiveHealing(getOutput(), getCriticalChance().isCritical());
            }
        }
        target.receiveHealing(getOutput(), getCriticalChance().isCritical());
    }

    @Override
    public void useMana() {
        if (getOwner().getTalentTree().getTalent(TalentTree.HOLY_FOCUS).isSelected())
            getOwner().receiveMana(getCost());
        else
            super.useMana();
    }

    @Override
    public void checkTalents() {
    }
}
