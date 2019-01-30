package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/2/2017.
 */

public class Rampage extends Mechanic{

    private Timer channel;
    private int sfxIndex;

    public Rampage(Boss owner) {
        super("Rampage", 10, 20f, owner);
        setAnnounce();
    }

    public Rampage(Boss owner, int damage, float speed) {
        super("Rampage", damage, speed, owner);
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
                if(count != 20) {
                    ArrayList<RaidMember> randoms  = getRaid().getRandomRaidMember(5);
                    count++;
                    for (int i = 0; i < randoms.size(); i++)    {
                        if(!randoms.get(i).isDead())    {
                            triggerSFX();
                            randoms.get(i).takeDamage(getDamage());
                        }
                    }
                }
                else    {
                    channel.stop();
                    channel.clear();
                    resume();
                }
            }
        },1f,1f,20);
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

    @Override
    public void stop() {
        super.stop();
        if(channel != null) {
            channel.stop();
            channel.clear();
        }
    }
}
