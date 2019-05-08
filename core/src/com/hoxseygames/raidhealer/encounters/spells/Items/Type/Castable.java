package com.hoxseygames.raidhealer.encounters.spells.Items.Type;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

public abstract class Castable extends Item {


    private float castTime;
    private Timer castTimer;
    private Sound castingSfx;
    private Sound sfx;
    /**
     * @param player
     * @param name
     * @param description
     * @param iconTexture
     * @param quantity
     * @param output
     * @param cooldown
     * @param needsTarget
     */
    public Castable(Player player, String name, String description, int goldCost, Texture iconTexture, int quantity,
                    int output, float cooldown, boolean needsTarget, float castTime, Sound castingSfx, Sound sfx) {
        super(player, name, description, goldCost, iconTexture, quantity, output, cooldown, needsTarget);

        this.castTime = castTime;
        this.castingSfx = castingSfx;
        this.sfx = sfx;
    }

    @Override
    public void castSpell() {
        if(isCastable())    {
            startCastTimer();
        }
    }

    private void startCastTimer()    {
        castTimer = new Timer();
        setCasting(true);
        getOwner().setCasting(isCasting());
        AudioManager.playSFX(castingSfx,true);

        final RaidMember finalTarget = getOwnerTarget();

        castTimer.scheduleTask(new Timer.Task() {
            int counter = 0;
            @Override
            public void run() {
                counter++;
                getOwner().setSpellCastPercent((counter * 0.01f) / castTime);

                if (needsTarget)
                    if (finalTarget.isDead()) {
                        stop();
                    }

                if(counter * 0.01f >= castTime)    {
                    AudioManager.stopSFX(castingSfx);
                    AudioManager.playSFX(sfx, false);

                    use();

                    setCasting(false);

                    getOwner().setCasting(isCasting());

                    startCooldownTimer();
                }

            }
        },0.01f, 0.01f,(int)(castTime/0.01f)-1);
    }

    @Override
    public void stop()  {
        if(castTimer != null) {
            castTimer.stop();
            castTimer.clear();
            getOwner().setSpellCastPercent(0);
            setCasting(false);
            getOwner().setCasting(isCasting());
            castingSfx.stop();
        }
    }


}
