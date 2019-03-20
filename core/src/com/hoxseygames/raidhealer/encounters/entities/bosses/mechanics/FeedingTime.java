package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.bosses.stage3.MotherSpider;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.StatusEffect.Debuff.WebEffect;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/23/2017.
 */

public class FeedingTime extends Mechanic {

    private float length;
    private Timer feedingTimer;

    @SuppressWarnings("unused")
    public FeedingTime(Boss owner) {
        super("Feeding Time", 0, 5f, owner);
        setAnnounce();
    }

    public FeedingTime(Boss owner, float speed, float length) {
        super("Feeding Time", 0, speed, owner);
        this.length = length;
        setAnnounce();
    }

    @Override
    public void action() {
        AudioManager.playSFX(getAssets().getSound(getAssets().debuffSFX), false);
        for (int i = 0; i < getOwner().getEnemies().getRaidMembers().size(); i++) {
            getOwner().getEnemies().getRaidMembers().get(i).addStatusEffect(new WebEffect(getOwner(),100));
        }
        startFeedingTime();
        pause();
    }

    private void startFeedingTime()  {
        feedingTimer = new Timer();

        feedingTimer.scheduleTask(new Timer.Task() {
            int counter = 0;
            @Override
            public void run() {
                counter++;
                if(counter == ((length-1.5f)*10))  {
                    getOwner().getAnnouncement().setText(getOwner().getName()+" is about to consumer her victims!");
                }
                if(counter == ((length)*10)) {
                    ArrayList<RaidMember> targets = getOwner().getEnemies().getStatusEffectedRaidMembers("Web Effect");
                    for (int i = 0; i < targets.size(); i++) {
                        targets.get(i).takeDamage(999);
                        getOwner().receiveHealing(100);
                    }
                    if(targets.size() > 0)  {
                        getOwner().getAnnouncement().setText(getOwner().getName()+" has eaten her victims and has restored health!");
                    }
                    else    {
                        getOwner().getAnnouncement().setText(getOwner().getName()+" is now hangry!");
                        getOwner().setDamage(getOwner().getDamage()+5);
                        MotherSpider ms = (MotherSpider) getOwner();
                        ms.getLeap().setDamage(getOwner().getDamage()*2);
                        ms.getAutoAttack().setDamage(getOwner().getDamage());
                    }
                    feedingTimer.stop();
                    feedingTimer.clear();
                    resume();
                }
            }
        },0.1f, 0.1f);
    }

    @Override
    public void stop() {
        super.stop();
        if (feedingTimer != null) {
            feedingTimer.stop();
            feedingTimer.clear();
        }
    }
}
