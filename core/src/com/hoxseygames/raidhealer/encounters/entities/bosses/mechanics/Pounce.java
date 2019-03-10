package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.BleedEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 7/26/2017.
 */

public class Pounce extends Mechanic {

    private Timer leapTimer;
    private int numOfTargets;
    private ArrayList<RaidMember> targets;

    /*
    public Pounce(Boss owner) {
        super("Pounce",30,4f,owner);
        numOfTargets = 3;
        setAnnounce();
    }

    public Pounce(Boss owner, float speed) {
        super("Pounce",30,speed,owner);
        setAnnounce();
    }

    @Override
    public void action() {
        ArrayList<RaidMember> temp  = getRaid().getRandomRaidMember(numOfTargets);

        for (int i = 0; i < temp.size(); i++)   {
            if(temp.get(i) != null) {
                AudioManager.playSFX(getAssets().getSound(getAssets().biteSFX), false);
                temp.get(i).takeDamage(getDamage());
                temp.get(i).addStatusEffect(new BleedEffect(getOwner()));
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }

    /*^-----------Old Code---------------^*/

    public Pounce(Boss owner) {
        super("Leap", 30, 4f, owner);
        this.numOfTargets = 3;
        setAnnounce();
    }

    public Pounce(Boss owner, int damage, float speed, int numOfTargets) {
        super("Leap", damage, speed, owner);
        this.numOfTargets = numOfTargets;
        setAnnounce();
    }

    public Pounce(Boss owner, float speed) {
        super("Leap", 30, speed, owner);
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
                    targets.get(count).addStatusEffect(new BleedEffect(getOwner()));
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

    public int getNumOfTargets() {
        return numOfTargets;
    }

    public void setNumOfTargets(int numOfTargets) {
        this.numOfTargets = numOfTargets;
    }


}
