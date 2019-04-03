package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 7/26/2017.
 */

public class ZombieAttack extends Mechanic {

    public ZombieAttack(Boss owner) {
        super("Auto Attack", 0, 2.5f, owner);
        setBgMech();
    }

    public ZombieAttack(Boss owner, float speed) {
        super("Auto Attack", 0, speed, owner);
        setBgMech();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> targets = getOwner().getEnemies().getRandomRaidMember(8);
        for (int i = 0; i < targets.size() ;i++)
            targets.get(i).takeDamage(getZombieDamage());
    }

    private int getZombieDamage()    {
        if(getOwner().getHpPercent() > 0.89f)    {
            return getOwner().getDamage()+10;
        }
        if(getOwner().getHpPercent() > 0.79f)    {
            return getOwner().getDamage()+9;
        }
        if(getOwner().getHpPercent() > 0.69f)    {
            return getOwner().getDamage()+8;
        }
        if(getOwner().getHpPercent() > 0.59f)    {
            return getOwner().getDamage()+7;
        }
        if(getOwner().getHpPercent() > 0.49f)    {
            return getOwner().getDamage()+6;
        }
        if(getOwner().getHpPercent() > 0.39f)    {
            return getOwner().getDamage()+5;
        }
        if(getOwner().getHpPercent() > 0.29f)    {
            return getOwner().getDamage()+4;
        }
        if(getOwner().getHpPercent() > 0.19f)    {
            return getOwner().getDamage()+3;
        }
        if(getOwner().getHpPercent() > 0.09f)    {
            return getOwner().getDamage()+2;
        }
        if(getOwner().getHpPercent() > 0.0f)    {
            return getOwner().getDamage()+1;
        }
        return 2;
    }

}
