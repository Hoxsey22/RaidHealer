package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.DivineProtectionEffect;
import com.hoxseygames.raidhealer.encounters.spells.Types.Castable;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class DivineProtection extends Castable {

    private ArrayList<Barrier> barriers;
    private boolean isSelectedCriticalHealerII;
    private boolean isSelectedResurgence;


    public DivineProtection(Player player, Assets assets) {
        super(player,
                "Divine Protection",
                "Grants all ally units a buff that will reduce all damage taken by 40% for 15 seconds.",
                assets.getTexture(assets.divineProtectionIcon),
                7,
                2f,
                0,
                10f,
                100f,
                assets.getSound(assets.divineProtectionSFX),
                assets);
    }

    @Override
    public void applySpell(RaidMember target) {
        for(int i = 0; i < getOwner().getRaid().getRaidMembers().size(); i++)   {
            getOwner().getRaid().getRaidMembers().get(i).addStatusEffect(new DivineProtectionEffect(getOwner()));
        }
    }

    @Override
    public void checkTalents() {}

}
