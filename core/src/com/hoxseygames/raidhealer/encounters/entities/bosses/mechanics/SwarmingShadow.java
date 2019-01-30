package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class SwarmingShadow extends Mechanic{


    private Timer channel;

    public SwarmingShadow(Boss owner) {
        super("Swarming Shadow", 10, 15f, owner);
        setAnnounce();
    }

    public SwarmingShadow(Boss owner, int damage, float speed) {
        super("Swarming Shadow", damage, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        startChannel();
        pause();
    }

    private void startChannel()  {
        channel = new Timer();

        channel.scheduleTask(new Timer.Task() {
            int count =  0;
            @Override
            public void run() {
                if(count != 4) {
                    count++;
                    AudioManager.playSFX(getAssets().getSound(getAssets().firecastSFX), false);
                    for(int i = 0; i <  getOwner().getEnemies().getRaidMembers().size(); i++)   {
                        getOwner().getEnemies().getRaidMember(i).takeDamage(getDamage());
                    }
                }
                else    {
                    channel.stop();
                    channel.clear();
                    resumePhase();
                }
            }
        },0.5f,0.5f,4);
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
