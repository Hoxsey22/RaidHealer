package com.hoxseygames.raidhealer.encounters.spells.Items.Type;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

public abstract class Channel extends Item {

    private float castTime;
    private int ticksPerCast;
    private Timer castTimer;
    private Sound castingSFX;
    private Sound spellSFX;
    private boolean isTriggerSFX;
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
    public Channel(Player player, String name, String description, int goldCost, Texture iconTexture, int quantity,
                   int output, float cooldown, boolean needsTarget, float castTime, int ticksPerCast,
                   Sound castingSFX, Sound spellSFX, boolean isTriggerSFX) {
        super(player, name, description, goldCost, iconTexture, quantity, output, cooldown, needsTarget);

        this.castTime = castTime;
        this.ticksPerCast = ticksPerCast;
        this.castingSFX = castingSFX;
        this.isTriggerSFX = isTriggerSFX;
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
        AudioManager.playSFX(castingSFX, !isTriggerSFX);
        final float tickTime = (castTime/ticksPerCast)-0.01f;

        final RaidMember sTarget = getOwnerTarget();

        castTimer.scheduleTask(new Timer.Task() {
            int counter = 0;
            int tCounter = 0;

            @Override
            public void run() {
                counter++;
                tCounter++;

                getOwner().setSpellCastPercent(1f-(counter*0.01f)/castTime);

                if(tCounter * 0.01f >= tickTime)    {
                    tCounter = 0;
                    use();
                }
                if(counter * 0.01f >= castTime)    {
                    AudioManager.stopSFX(castingSFX);
                    setCasting(false);
                    getOwner().setCasting(isCasting());
                    startCooldownTimer();
                    stop();
                }
            }
        },0.01f, 0.01f,(int)(castTime/0.01f)-1);
    }

    public void stop()  {
        if(castTimer != null) {
            castTimer.stop();
            castTimer.clear();
            getOwner().setSpellCastPercent(0);
            setCasting(false);
            getOwner().setCasting(isCasting());
            startCooldownTimer();
            castingSFX.stop();
        }
    }
}
