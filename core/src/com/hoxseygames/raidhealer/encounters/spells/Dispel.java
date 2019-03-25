package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.InstantCast;

/**
 * Created by Hoxsey on 12/6/2017.
 */

public class Dispel extends InstantCast {
    /**
     * @param player
     * @param assets
     */
    public Dispel(Player player, Assets assets) {
        super(player, "Dispel",
                "Dispels all dispellable debuffs from the targeted ally unit. ",
                3,
                1,
                0,
                1.5f,
                1f,
                assets.getSound(assets.dispelSFX),
                assets);
        setDescription("Dispels a magic effect off an ally unit.");
        setImage(getAssets().getTexture(getAssets().dispelIcon));
    }

    @Override
    public void applySpell(RaidMember target) {
        target.getStatusEffectList().dispel();
        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            applyAtonement(target);
        }
    }

    @Override
    public void checkTalents() {
    }
}
