package com.hoxseygames.raidhealer.encounters.entities.bosses.stage3;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Agony;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.AutoAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.FireBreath;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Flurry;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Ignite;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Pyroblast;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.RipTankSwap;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.TailSwipe;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.UnstableMagic;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.UnstablePyroblast;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.AgonyEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BurnEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.IgniteEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.RipEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.UnstableMagicEffect;

/**
 * Created by Hoxsey on 10/11/2017.
 */


/*Need to add another mech*/
public class DeathDragon extends Boss {

    private Agony agony;
    private Pyroblast pyroblast;
    private AutoAttack autoAttackp1;

    private UnstableMagic unstableMagic;
    private UnstablePyroblast unstablePyroblast;
    private AutoAttack autoAttackp2;

    private AutoAttack autoAttackp3;
    private RipTankSwap ripTankSwap;
    private TailSwipe tailSwipe;
    private Flurry flurry;
    private FireBreath fireBreath;
    private Ignite ignite;


    public DeathDragon(Assets assets) {
        super("Resurrected Sorcerer",
                Strings.RESURRECTED_SORCERER_DESCRIPTION,
                530,
                new Raid(2,3,7, assets),
                assets);
        setId(16);
        create();
    }

    @Override
    public void create() {
        super.create();

        setDamage(20);


        // Phase 1
        agony = new Agony(this, 7f);
        agony.setDamage(25);
        pyroblast = new Pyroblast(this, 2.5f);
        pyroblast.setDamage(40);
        autoAttackp1 = new AutoAttack(this,2f);


        // Phase 2
        unstableMagic = new UnstableMagic(this);
        unstablePyroblast = new UnstablePyroblast(this, 1.5f);
        autoAttackp2 = new AutoAttack(this,3f);

        // Phase 3
        autoAttackp3 = new AutoAttack(this, 2f);
        ripTankSwap = new RipTankSwap(this, 10f);
        flurry = new Flurry(this, 15, 20f);
        tailSwipe = new TailSwipe(this, 30f);
        fireBreath = new FireBreath(this, 8, 30f);
        ignite = new Ignite(this, 15f);

        Phase phase1 = new Phase(this, 75, agony, pyroblast,autoAttackp1);

        Phase phase2 = new Phase(this, 60f, unstableMagic,unstablePyroblast, autoAttackp2);
        phase2.setNameChange();
        phase2.setNameChange("Deformed Sorcerer");

        Phase phase3 = new Phase(this, 0, autoAttackp3, ripTankSwap, flurry,tailSwipe,fireBreath, ignite);
        phase3.setNameChange();
        phase3.setNameChange("Death Dragon");


        getPhaseManager().addPhase(phase1);
        getPhaseManager().addPhase(phase2);
        getPhaseManager().addPhase(phase3);

        loadDebuff(new AgonyEffect(this), new BurnEffect(this), new UnstableMagicEffect(this),
                new RipEffect(this), new BleedEffect(this), new IgniteEffect(this));

    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId())
            getRewardPackage().addNewLevelText();
    }

}