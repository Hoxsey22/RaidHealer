package com.hoxseygames.raidhealer.encounters.spells.Types;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 9/7/2017.
 */

public abstract class Periodical extends InstantCast {

    private float duration;
    private float MIN_DURATION;
    private float speed;
    private float MIN_SPEED;
    protected Timer durationTimer;

    /**
     * @param player
     * @param name
     * @param description
     * @param numOfTargets
     * @param output
     * @param costPercentage
     * @param costPercentage
     * @param cooldown
     * @param spellSFX
     * @param assets
     */
    protected Periodical(Player player, String name, String description, Texture imageIcon, int levelRequirement, int numOfTargets,
                         int output, float costPercentage, float cooldown, float duration, float speed, Sound spellSFX, Assets assets) {
        super(player, name, description, imageIcon, levelRequirement, numOfTargets, output, costPercentage, cooldown, spellSFX, assets);
        setSpellType("Periodical");
        this.duration = duration;
        MIN_DURATION = duration;
        this.speed = speed;
        MIN_SPEED = speed;

    }

    @Override
    public void applySpell(RaidMember target) {
        startDurationTimer();
    }

    protected abstract void startDurationTimer();
    public abstract void checkLifeboom();

    protected void checkHasteBuild()   {
        if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected())    {
            speed = MIN_SPEED - 0.25f;
        }
    }

    protected void checkAoD()  {
        if(getOwner().getTalentTree().getTalent(TalentTree.AOD).isSelected())    {

            setOutput(10);
            if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected()) {
                setDuration(10);
                setSpeed(1.25f);
            }
            else {
                setDuration(12);
                setSpeed(1.5f);
            }

        }
    }

    @Override
    public void resetDefault() {
        super.resetDefault();
        duration = MIN_DURATION;
        speed = MIN_SPEED;
    }

    public void stop() {
        if(durationTimer != null)    {
            durationTimer.stop();
            durationTimer.clear();
        }
    }

    public float getDuration() {
        return duration;
    }

    public float setDuration(float duration) {
        this.duration = duration;
        return duration;
    }

    public float getMIN_DURATION() {
        return MIN_DURATION;
    }

    public void setMIN_DURATION(float MIN_DURATION) {
        this.MIN_DURATION = MIN_DURATION;
    }

    public float getSpeed() {
        return speed;
    }

    public float setSpeed(float speed) {
        this.speed = speed;
        return speed;
    }

    public float getMIN_SPEED() {
        return MIN_SPEED;
    }

    public void setMIN_SPEED(float MIN_SPEED) {
        this.MIN_SPEED = MIN_SPEED;
    }

    public Timer getDurationTimer() {
        return durationTimer;
    }

    public void setDurationTimer(Timer durationTimer) {
        this.durationTimer = durationTimer;
    }
}
