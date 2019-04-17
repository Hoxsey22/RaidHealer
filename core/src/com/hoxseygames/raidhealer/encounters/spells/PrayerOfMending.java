package com.hoxseygames.raidhealer.encounters.spells;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Buff.PrayerOfMendingEffect;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;
import com.hoxseygames.raidhealer.encounters.spells.Types.Castable;

/**
 * Created by Hoxsey on 6/18/2017.
 */
public class PrayerOfMending extends Castable {

    public PrayerOfMending(Player player, Assets assets) {
        super(player,
                "Prayer of Mending",
                "A ward is placed on an ally unit that heals the ally unit when damaged and will jump to a new target.",
                assets.getTexture(assets.prayerOfMendingIcon),
                4,
                1.5f,
                25,
                2f,
                8,
                assets.getSound(assets.pomTriggerSFX),
                assets);
        setDescription("Grants an ally unit a buff that will heal the ally unit with this buff for "+getOutput()+"hp and jumps to the most injured ally unit and does this 5 times.");
        setNumOfTargets(5);
    }

    @Override
    public void applySpell(RaidMember target) {
        target.addStatusEffect(new PrayerOfMendingEffect(getOwner(), 20, getNumOfTargets()));
    }

    @Override
    public void resetDefault() {
        super.resetDefault();
        setNumOfTargets(5);
    }

    @Override
    public void checkTalents() {
        resetDefault();

        checkCriticalHealer();
        checkHasteBuild();
    }

    public void checkSuperNova()    {
        if(getOwner().getTalentTree().getTalent(TalentTree.SUPER_NOVA).isSelected())    {
            setNumOfTargets(6);
        }
    }
}
