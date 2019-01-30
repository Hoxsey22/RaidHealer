package com.hoxseygames.raidhealer.encounters.entities.bosses.stage3;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.BloodLink;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Cleave;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.ConsumingShadow;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.SwarmingShadow;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.ConsumingShadowEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.VampiricBiteEffect;

/**
 * Created by Hoxsey on 8/26/2017.
 */

public class BloodQueen extends Boss {

    private BloodLink bloodLink;
    private Cleave cleave;
    private SwarmingShadow swarmingShadow;
    private ConsumingShadow consumingShadow;

    public BloodQueen(Assets assets) {
        super("Blood Queen",
                Strings.BLOOD_QUEEN_DESCRIPTION,
                600,
                new Raid(12,assets),
                assets);
        setId(14);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(15);

        bloodLink = new BloodLink(this, 2f);
        cleave = new Cleave(this, 4f);
        cleave.setNumOfTargets(4);
        cleave.setDamage(35);
        consumingShadow = new ConsumingShadow(this, 5f);

        swarmingShadow = new SwarmingShadow(this, 12, 8f);

        getPhaseManager().addPhase(new Phase(this, 70f, bloodLink, cleave));
        getPhaseManager().addPhase(new Phase(this, 6f, consumingShadow));
        getPhaseManager().addPhase(new Phase(this, 34f, swarmingShadow));

        loadDebuff(new VampiricBiteEffect(this), new ConsumingShadowEffect(this));
    }

    @Override
    public void start() {
        super.start();
        getEnemies().getRaidMembers().get(7).addStatusEffect(new VampiricBiteEffect(this));
        System.out.println("Phase Manager - number of phases: "+getPhaseManager().getPhases().size()+".");
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId())
            getRewardPackage().addNewLevelText();
    }

}
