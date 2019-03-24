package com.hoxseygames.raidhealer.encounters.entities.bosses.stage3;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.DoubleAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Rampage;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.ThunderStorm;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.PoisonSpit;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.PoisonEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.StoneSkinEffect;

/**
 * Created by Hoxsey on 10/11/2017.
 */

public class Hydra extends Boss {

    private ThunderStorm thunderStorm;
    private DoubleAttack doubleAttack;
    private PoisonSpit p1Poison;
    private Rampage rampage;
    private AutoAttack autoAttack;


    public Hydra(Assets assets) {
        super("Ion, The Hydra",
                Strings.ION_DESCRIPTION,
                210,
                new Raid(2,3,7, assets),
                assets);
        setId(15);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(20);

        autoAttack = new AutoAttack(this, 1.5f);
        doubleAttack = new DoubleAttack(this, 13f);
        p1Poison = new PoisonSpit(this, 5.1f, 4);
        rampage = new Rampage(this, 15, 5f);
        thunderStorm = new ThunderStorm(this, 15f);

        getPhaseManager().addPhase(new Phase(this, 40f, autoAttack, doubleAttack,p1Poison, thunderStorm));
        getPhaseManager().addPhase(new Phase(this, 15f, rampage));

        loadDebuff(new StoneSkinEffect(this), new PoisonEffect(this));

    }

    @Override
    public void start() {
        super.start();
        for(int i = 0; i < getEnemies().getRaidMembers().size(); i++)   {
            getEnemies().getRaidMember(i).addStatusEffect(new StoneSkinEffect(this));
        }
        System.out.println("Phase Manager - number of phases: "+getPhaseManager().getPhases().size()+".");
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId())
        getRewardPackage().addNewLevelText();
    }

}