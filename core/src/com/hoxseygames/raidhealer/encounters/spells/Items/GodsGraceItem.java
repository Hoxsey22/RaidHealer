package com.hoxseygames.raidhealer.encounters.spells.Items;

import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Items.Type.Castable;

public class GodsGraceItem extends Castable {

    public GodsGraceItem(Player player) {
        super(player,
                "God's Grace",
                "Pray to God for redemption and his grace will be revealed. All allies " +
                        "will be resurrected. Before casting this item, there must be at least one " +
                        "ally unit alive.",
                1,
                player.getAssets().getTexture(player.getAssets().divineHymnIcon),
                0,
                100,
                120f,
                false,
                5f,
                player.getAssets().getSound(player.getAssets().barrierSFX),
                player.getAssets().getSound(player.getAssets().healSFX));
    }

    @Override
    protected void applySpell() {
        for (int i = 0; i < getOwner().getRaid().getRaidMembers().size();i++)   {
            getTarget().setDead(false);
            getTarget().receiveHealing(50,false);
        }
    }

    @Override
    public void checkTalents() {

    }

    @Override
    public void applySpell(RaidMember target) {

    }
}
