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
                "Calls the beyond for protection, reducing all damage by 20%.",
                7,
                2f,
                0,
                10f,
                100f,
                assets.getSound(assets.divineProtectionSFX),
                assets);
        setDescription("Grants all ally units a buff that will reduce all damage taken by 40% for 15 seconds.");
        setImage(getAssets().getTexture(getAssets().divineProtectionIcon));
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
