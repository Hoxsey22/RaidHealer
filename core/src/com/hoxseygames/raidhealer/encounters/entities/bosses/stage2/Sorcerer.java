package com.hoxseygames.raidhealer.encounters.entities.bosses.stage2;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Agony;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.BlanketCorruption;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.FireBreath;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Fireball;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Pyroblast;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.AgonyEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BurnEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.CorruptionEffect;

/**
 * Created by Hoxsey on 8/26/2017.
 */

public class Sorcerer extends Boss {

    private Agony agony;
    private BlanketCorruption blanketCorruption;
    private Fireball fireball;
    private Pyroblast pyroblast;
    private FireBreath fireBreath;

    public Sorcerer(Assets assets) {
        super("Sorcerer",
                Strings.SORCERER_DESCRIPTION,
                240,
                new Raid(12,assets),
                assets);
        setId(11);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(0);

        agony = new Agony(this);

        blanketCorruption = new BlanketCorruption(this,40f);

        fireball = new Fireball(this, 3f);

        pyroblast = new Pyroblast(this, 5f);
        pyroblast.setDamage(60);

        fireBreath = new FireBreath(this);
        fireBreath.setSpeed(35f);

        getPhaseManager().addPhase(new Phase(this, 0, fireball, agony, blanketCorruption, fireBreath));

        loadDebuff(new BurnEffect(this), new CorruptionEffect(this), new AgonyEffect(this));
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewTalentText();
        }
    }

    public Agony getAgony() {
        return agony;
    }

    public void setAgony(Agony agony) {
        this.agony = agony;
    }

    public BlanketCorruption getBlanketCorruption() {
        return blanketCorruption;
    }

    public void setBlanketCorruption(BlanketCorruption blanketCorruption) {
        this.blanketCorruption = blanketCorruption;
    }

    public Fireball getFireball() {
        return fireball;
    }

    public void setFireball(Fireball fireball) {
        this.fireball = fireball;
    }

    public Pyroblast getPyroblast() {
        return pyroblast;
    }

    public void setPyroblast(Pyroblast pyroblast) {
        this.pyroblast = pyroblast;
    }

    public FireBreath getFireBreath() {
        return fireBreath;
    }

    public void setFireBreath(FireBreath fireBreath) {
        this.fireBreath = fireBreath;
    }
}
