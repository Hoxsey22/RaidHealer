package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class Flurry extends Mechanic{

    private Timer channel;
    private int sfxIndex;

    @SuppressWarnings("unused")
    public Flurry(Boss owner) {
        super("Flurry", 10, 20f, owner);
        setAnnounce();
    }

    public Flurry(Boss owner, int damage, float speed) {
        super("Flurry", damage, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        //pausePhase();
        startChannel();
        //getTimer().stop();
        pause();
    }

    private void startChannel()  {
        channel = new Timer();

        channel.scheduleTask(new Timer.Task() {
            int count =  0;
            ArrayList<RaidMember> random  = getRaid().getRandomRaidMember(1);
            @Override
            public void run() {
                try {
                    if (count != 10) {
                        count++;
                        if (random != null) {
                            if (random != null && random.size() != 0) {
                                triggerSFX();
                                random.get(0).takeDamage(getDamage());
                            }
                            if (random.get(0).isDead()) {
                                random = getRaid().getRandomRaidMember(1);
                            }
                        }
                    } else {
                        channel.stop();
                        channel.clear();
                        resume();
                    }
                } catch (IndexOutOfBoundsException e) {
                    if(channel != null) {
                        channel.stop();
                        channel.clear();
                    }
                }
            }
        },0.5f,0.5f,10);
    }

    public void triggerSFX()    {
        switch ((sfxIndex%6)) {
            case 0:
                AudioManager.playSFX(getAssets().getSound(getAssets().swordSwing1SFX), false);
                break;
            case 1:
                AudioManager.playSFX(getAssets().getSound(getAssets().swordSwing2SFX), false);
                break;
            case 2:
                AudioManager.playSFX(getAssets().getSound(getAssets().swordSwing3SFX), false);
                break;
            case 3:
                AudioManager.playSFX(getAssets().getSound(getAssets().swordSwing4SFX), false);
                break;
            case 4:
                AudioManager.playSFX(getAssets().getSound(getAssets().swordSwing5SFX), false);
                break;
            case 5:
                AudioManager.playSFX(getAssets().getSound(getAssets().swordSwing6SFX), false);
                break;
        }
        sfxIndex++;
    }


}
