package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.AoDEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.Buff;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.LifeboomEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.RenewEffect;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.Periodical;

/**
 * Created by Hoxsey on 12/2/2017.
 */

public class Renew extends Periodical {

    private Buff buff;

    /**
     * @param player
     * @param assets
     */
    public Renew(Player player, Assets assets) {
        super(player, "Renew", "A renewing amount of holy light is place on an ally unit.",
                0,
                1,
                7,
                1f,
                0.5f,
                10f,
                2f,
                assets.getSound(assets.renewSFX), assets);
        setDescription("Heals an ally unit for "+getOutput()+"hp every 2 seconds for 10 seconds.");
        setImage(this.getAssets().getTexture(getAssets().renewIcon));

    }

    @Override
    public void applySpell(RaidMember target) {
        checkLifeboom();
        if (target.getStatusEffectList().contains("Lifeboom Effect")
                && !target.getStatusEffectList().contains("AoD Effect")
                && getOwner().getTalentTree().getTalent(TalentTree.AOD).isSelected()) {

            target.addStatusEffect(new AoDEffect(getOwner(), getDuration(), getSpeed(), getOutput()));
        }
        else {
            target.addStatusEffect(buff);
        }
    }

    @Override
    public void startDurationTimer() {

    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkAoD();
        checkCriticalHealer();
    }

    @Override
    public void checkLifeboom() {
        if(getOwner().getTalentTree().getTalent(TalentTree.LIFEBOOM).isSelected())  {
            if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected())
                buff = new LifeboomEffect(getOwner(), 10.5f, 1.75f,getOutput());
            else
                buff = new LifeboomEffect(getOwner(), getDuration(), getSpeed(),getOutput());
        }
        else    {
            if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected())
                buff = new RenewEffect(getOwner(), 10.5f, 1.75f,getOutput());
            else
                buff = new RenewEffect(getOwner(), getDuration(), getSpeed(),getOutput());
        }
    }
}
