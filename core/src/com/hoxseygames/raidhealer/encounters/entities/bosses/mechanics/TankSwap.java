package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.Debuff;

/**
 * Created by Hoxsey on 8/4/2017.
 */

public class TankSwap extends Mechanic {

    private Debuff debuff;

    public TankSwap(Boss owner) {
        super("Tank Swap",0,8f,owner);
        setMainTank();
        setOffTank();
        setBgMech();
    }

    public TankSwap(Boss owner, float speed) {
        super("Tank Swap",0,speed,owner);
        setMainTank();
        setOffTank();
        setBgMech();
    }

    public TankSwap(Boss owner, float speed, Debuff debuff) {
        super("Tank Swap",0,speed,owner);
        this.debuff = debuff;
        setMainTank();
        setOffTank();
        setBgMech();
    }

    @Override
    public void action() {
        if(debuff != null)    {
            getOwner().getTarget().addStatusEffect(debuff);
        }
        tankSwap();

        System.out.println("TANK SWAP!");
    }

    private void tankSwap()  {
        if(getOffTank().isDead() || getMainTank().isDead())   {
            if(getMainTank().isDead()) {
                getOwner().setTarget(getOwner().getOffTank());
                stop();
                return;
            }
            else    {
                getOwner().setTarget(getOwner().getMainTank());
                stop();
                return;
            }
        }

        if( getOwner().getTarget().equals(getMainTank())) {
            getOwner().setTarget(getOwner().getOffTank());
        }
        else {
            getOwner().setTarget(getOwner().getMainTank());
        }
    }
}
