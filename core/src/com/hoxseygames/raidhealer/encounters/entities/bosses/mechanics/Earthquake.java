package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 8/18/2017.
 */

public class Earthquake extends Mechanic {

    private int numOfTargets;
    private Timer channel;
    private int sfxIndex;

    @SuppressWarnings("unused")
    public Earthquake(Boss owner) {
        super("Earthquake", 8, 15f, owner);
        setAnnounce();
    }

    public Earthquake(Boss owner, float speed) {
        super("Earthquake", 8, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        startChannel();
        getTimer().stop();
    }

    private void startChannel()  {
        channel = new Timer();

        channel.scheduleTask(new Timer.Task() {
            int count =  0;
            @Override
            public void run() {
                if(count != 3) {
                    count++;
                    AudioManager.playSFX(getAssets().getSound(getAssets().earthquake1SFX), false);
                    getRaid().takeDamage(getDamage());
                }
                else    {
                    channel.stop();
                    channel.clear();
                    getTimer().start();
                }
            }
        },0.5f,0.5f,5);
    }

    public void triggerSFX()    {
        switch ((sfxIndex%3)) {
            case 0:
                AudioManager.playSFX(getAssets().getSound(getAssets().earthquake1SFX), false);
                break;
            case 1:
                AudioManager.playSFX(getAssets().getSound(getAssets().earthquake2SFX), false);
                break;
            case 2:
                AudioManager.playSFX(getAssets().getSound(getAssets().earthquake3SFX), false);
                break;
        }
        sfxIndex++;
    }

    @Override
    public void stop() {
        super.stop();
        if(channel != null) {
            channel.stop();
            channel.clear();
        }
    }

}
