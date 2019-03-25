package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.BarrierEffect;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.InstantCast;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class Barrier extends InstantCast {

    public Barrier(Player player, Assets assets) {
        super(player,"Barrier", "Places a barrier around the ally unit, absorbing incoming damage.",
                6,
                1,
                40,
                2.3f,
                4f,
                assets.getSound(assets.barrierSFX),
                assets);
        setDescription("Grants an ally unit a barrier that prevents "+getOutput()+" damage.");
        setImage(this.getAssets().getTexture(getAssets().barrierIcon));
        checkTalents();
    }

    @Override
    public void checkTalents() {
        resetDefault();

        if(getOwner().getTalentTree().getTalent(TalentTree.BARRIER_MASTER).isSelected())    {
            setCooldown(1f);
            setCostPercentage(2.0f);
        }
        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            setOutput(60);
        }
    }

    @Override
    public void applySpell(RaidMember target) {
        target.applyShield(getOutput());
        target.addStatusEffect(new BarrierEffect(getOwner()));

        if(getOwner().getTalentTree().getTalent(TalentTree.DISCIPLINE).isSelected())    {
            applyAtonement(target);
        }
    }
}
