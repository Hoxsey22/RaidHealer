package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class ThunderStorm extends Mechanic{

    private Timer powerlessTimer;

    public ThunderStorm(Boss owner) {
        super("Thunder Storm", 0, 35f, owner);
        setAnnounce();
    }

    public ThunderStorm(Boss owner, float speed) {
        super("Thunder Storm", 0, speed, owner);
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().electricStrikeSFX), false);
        for(int i = 0; i < getRaid().getRaidMembers().size(); i++)   {
            getRaid().getRaidMember(i).takeDamage(100);
        }
        startPowerlessTimer();
        getParentPhase().pauseMechanics();
        //pausePhase();

    }

    private void startPowerlessTimer()    {

        getOwner().getAnnouncement().setText(getOwner().getName()+" is powerless for just 7 seconds!");
        powerlessTimer = new Timer();

        powerlessTimer.scheduleTask(new Timer.Task() {
            int count = 0;
            @Override
            public void run() {

                getOwner().getAnnouncement().setText("");
                getRaid().takeDamage(100);
                if(count == 2)    {
                    powerlessTimer.stop();
                    powerlessTimer.clear();
                    resume();
                }
                count++;
            }
        },7f,7f, 2);
    }

    @Override
    public void stop() {
        super.stop();
        if(powerlessTimer != null){
            powerlessTimer.stop();
            powerlessTimer.clear();
        }
    }
}
