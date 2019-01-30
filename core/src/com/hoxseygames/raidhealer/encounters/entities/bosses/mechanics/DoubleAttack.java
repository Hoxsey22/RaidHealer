package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.SunderEffect;

/**
 * Created by Hoxsey on 8/20/2017.
 */

public class DoubleAttack extends Mechanic {

    private Timer secondTimer;

    @SuppressWarnings("unused")
    public DoubleAttack(Boss owner) {
        super("Double Attack", 20, 2f, owner);
        setAnnounce();
    }

    public DoubleAttack(Boss owner, float speed)   {
        super("Double Attack", 20, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        getOwner().getTarget().takeDamage(200);
        secondAttack();
        pausePhase();
    }

    private void secondAttack()  {
        secondTimer = new Timer();

        secondTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX),false);
                getOwner().getTarget().takeDamage(200);
                getOwner().getTarget().addStatusEffect(new SunderEffect(getOwner()));
                getOwner().setTarget(getOwner().getNextThreat());

                secondTimer.stop();
                secondTimer.clear();
                resumePhase();
            }
        },1.5f,1.5f,1);
    }

    @Override
    public void stop() {
        super.stop();
        if(secondTimer != null) {
            secondTimer.stop();
            secondTimer.clear();
        }
    }
}
