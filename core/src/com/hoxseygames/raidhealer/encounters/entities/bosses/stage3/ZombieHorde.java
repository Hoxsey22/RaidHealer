package com.hoxseygames.raidhealer.encounters.entities.bosses.stage3;

import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Strings;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.Phase;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.ZombieAttack;
import com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics.ZombieBite;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.InfectedEffect;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.ZombieBiteEffect;

/**
 * Created by Hoxsey on 8/26/2017.
 */

public class ZombieHorde extends Boss {

    private ZombieAttack zombieAttack;
    private ZombieBite zombieBite;

    public ZombieHorde(Assets assets) {
        super("Zombie Horde",
                Strings.HORDE_OF_ZOMBIE_DESCRIPTION,
                240,
                new Raid(12,assets),
                assets);
        setId(13);
        create();
    }

    @Override
    public void create() {
        super.create();
        setDamage(0);

        zombieAttack = new ZombieAttack(this, 1.5f);
        zombieBite = new ZombieBite(this, 9f);
        zombieBite.setNumOfTargets(2);

        getPhaseManager().addPhase(new Phase(this, 0, zombieAttack, zombieBite));

        loadDebuff(new ZombieBiteEffect(this), new InfectedEffect(this));
    }

    @Override
    public void start() {
        super.start();
        for(int i = 0; i < getEnemies().getRaidMembers().size(); i++)   {
            getEnemies().getRaidMember(i).addStatusEffect(new InfectedEffect(this));
        }
    }

    @Override
    public void reward() {
        if(getPlayer().getLevel() >= getId()) {
            getRewardPackage().addNewLevelText();
        }
    }

    public ZombieAttack getZombieAttack() {
        return zombieAttack;
    }

    public void setZombieAttack(ZombieAttack zombieAttack) {
        this.zombieAttack = zombieAttack;
    }

    public ZombieBite getZombieBite() {
        return zombieBite;
    }

    public void setZombieBite(ZombieBite zombieBite) {
        this.zombieBite = zombieBite;
    }
}
