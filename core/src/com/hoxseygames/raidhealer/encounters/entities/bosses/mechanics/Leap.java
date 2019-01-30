package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.VenomEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class Leap extends Mechanic{


    private Timer leapTimer;
    private final int numOfTargets;
    private ArrayList<RaidMember> targets;

    public Leap(Boss owner, int damage, float speed, int numOfTargets) {
        super("Leap", damage, speed, owner);
        this.numOfTargets = numOfTargets;
        setAnnounce();
    }

    @Override
    public void action() {
        startChannel();
        //pausePhase();
        pause();
    }

    private void startChannel()  {
        leapTimer = new Timer();

        targets = getOwner().getEnemies().getRandomRaidMember(numOfTargets);
        leapTimer.scheduleTask(new Timer.Task() {
            int count =  0;

            @Override
            public void run() {
                if(count != targets.size()) {
                    AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
                    targets.get(count).takeDamage(getDamage());
                    targets.get(count).addStatusEffect(new VenomEffect(getOwner()));
                }
                else    {
                    leapTimer.stop();
                    leapTimer.clear();
                    resumePhase();
                }
                count++;
            }
        },0.5f,0.5f,targets.size());
    }
    @Override
    public void stop() {
        super.stop();
        if(leapTimer != null) {
            leapTimer.stop();
            leapTimer.clear();
        }
    }

}
