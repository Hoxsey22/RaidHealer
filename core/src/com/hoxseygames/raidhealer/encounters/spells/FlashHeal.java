package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class FlashHeal extends Heal {

    public FlashHeal(Player player, Assets assets) {
        super(player,
                "Flash Heal",
                "Heals an ally unit quickly for 40hp, but at the cost of more mana.",
                assets.getTexture(assets.flashIcon),
                5,
                0.7f,
                2.2f,
                assets);
    }

    @Override
    public void applySpell(RaidMember target) {
        super.applySpell(target);

        if(getOwner().getTalentTree().getTalent(TalentTree.RENEWING_NOVA).isSelected())    {
            applyRenewingNova(target);
        }
    }
}
