package com.hoxseygames.raidhealer.encounters.entities.bosses.stage2;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Fireball;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Pyroblast;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.SeedOfCorruption;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BurnEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.CorruptionEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.SeedOfCorruptionEffect;

/**
 * Created by Hoxsey on 8/26/2017.
 */

public class Apprentice extends Boss {

    private SeedOfCorruption seedOfCorruption;
    private Fireball fireball;
    private Pyroblast pyroblast;

    public Apprentice(Assets assets) {
        super("Apprentice",
                Strings.APPRENTICE_DESCRIPTION,
                210,
                new Raid(12,assets),
                assets);
        setId(10);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(0);

        fireball = new Fireball(this, 2f);
        fireball.setDamage(25);
        pyroblast = new Pyroblast(this, 5f);
        pyroblast.setDamage(60);
        seedOfCorruption = new SeedOfCorruption(this);

        getPhaseManager().addPhase(new Phase(this,0, fireball, seedOfCorruption));

        loadDebuff(new BurnEffect(this), new SeedOfCorruptionEffect(this), new CorruptionEffect(this));
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
            getRewardPackage().addNewTalentText();
        }
    }

// --Commented out by Inspection START (5/29/2018 8:13 PM):
//    public SeedOfCorruption getSeedOfCorruption() {
//        return seedOfCorruption;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:13 PM)

// --Commented out by Inspection START (5/29/2018 8:13 PM):
//    public void setSeedOfCorruption(SeedOfCorruption seedOfCorruption) {
//        this.seedOfCorruption = seedOfCorruption;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:13 PM)

// --Commented out by Inspection START (5/29/2018 8:13 PM):
//    public Fireball getFireball() {
//        return fireball;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:13 PM)

// --Commented out by Inspection START (5/29/2018 8:13 PM):
//    public void setFireball(Fireball fireball) {
//        this.fireball = fireball;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:13 PM)

// --Commented out by Inspection START (5/29/2018 8:13 PM):
//    public Pyroblast getPyroblast() {
//        return pyroblast;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:13 PM)

// --Commented out by Inspection START (5/29/2018 8:13 PM):
//    public void setPyroblast(Pyroblast pyroblast) {
//        this.pyroblast = pyroblast;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:13 PM)
}
