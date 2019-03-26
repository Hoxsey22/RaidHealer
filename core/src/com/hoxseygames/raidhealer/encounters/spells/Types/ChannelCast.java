package com.hoxseygames.raidhealer.encounters.spells.Types;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Spell;

/**
 * Created by Hoxsey on 8/31/2017.
 */

@SuppressWarnings("ALL")
public abstract class ChannelCast extends Spell {

    private float castTime;
    private float MIN_CAST_TIME;
    private int ticksPerCast;
    private int MIN_TICK_PER_CAST;
    private Timer castTimer;
    private Sound castingSFX;
    private Sound spellSFX;
    private boolean isTriggerSFX;

    /**
     *
     * @param player
     * @param name
     * @param description
     * @param castTime
     * @param output
     * @param costPercentage
     * @param cooldown
     * @param assets
     */
    protected ChannelCast(Player player, String name, String description, int levelRequirement, float castTime, int ticksPerCast,
                          int output, float costPercentage, float cooldown, Sound castingSFX, boolean isTriggerSFX, Assets assets) {
        super(player, name, description, levelRequirement, output, costPercentage, cooldown, assets);
        setSpellType("Channeled");
        this.castTime = castTime;
        MIN_CAST_TIME = castTime;
        this.ticksPerCast = ticksPerCast;
        MIN_TICK_PER_CAST = ticksPerCast;
        this.castingSFX = castingSFX;
        this.isTriggerSFX = isTriggerSFX;
    }

    @Override
    public void castSpell() {
        if(isCastable())    {
            startCastTimer();
        }

    }

    @Override
    public void applySpell(RaidMember target)    {
        target.receiveHealing(getOutput(), getCriticalChance().isCritical());
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
                    applySpell(sTarget);
                }
                if(counter * 0.01f >= castTime)    {
                    AudioManager.stopSFX(castingSFX);
                    setCasting(false);
                    getOwner().setCasting(isCasting());
                    useMana();
                    startCooldownTimer();
                    stop();
                }
            }
        },0.01f, 0.01f,(int)(castTime/0.01f)-1);
    }

    @Override
    public void resetDefault() {
        super.resetDefault();
        castTime = MIN_CAST_TIME;
        ticksPerCast = MIN_TICK_PER_CAST;
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

    protected void setCastTime(float castTime) {
        this.castTime = castTime;
    }

    protected float getMIN_CAST_TIME() {
        return MIN_CAST_TIME;
    }

    protected void setTicksPerCast(int ticksPerCast) {
        this.ticksPerCast = ticksPerCast;
    }


}
