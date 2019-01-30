package com.hoxseygames.raidhealer.encounters.spells.Types;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.AudioManager;
import com.hoxseygames.raidhealer.Player;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;
import com.hoxseygames.raidhealer.encounters.spells.Spell;
import com.hoxseygames.raidhealer.encounters.spells.Talents.TalentTree;

/**
 * Created by Hoxsey on 8/31/2017.
 */

@SuppressWarnings("ALL")
public abstract class Castable extends Spell {

    private float castTime;
    private float MIN_CAST_TIME;
    private Timer castTimer;
    private Sound castingSFX;
    private Sound spellSFX;

    /**
     *
     * @param player
     * @param name
     * @param description
     * @param castTime
     * @param output
     * @param costPercentage
     * @param cooldown
     * @param spellSFX
     * @param assets
     */
    protected Castable(Player player, String name, String description, int levelRequirement, float castTime,
                       int output, float costPercentage, float cooldown, Sound spellSFX, Assets assets) {
        super(player, name, description, levelRequirement, output, costPercentage, cooldown, assets);
        setSpellType("Castable");
        this.spellSFX = spellSFX;
        this.castTime = castTime;
        MIN_CAST_TIME = castTime;
        castingSFX = getAssets().getSound(getAssets().castingSFX);
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
        AudioManager.playSFX(castingSFX,true);
        //castingSFX.loop(0.3f);

        final RaidMember sTarget = getOwnerTarget();

        castTimer.scheduleTask(new Timer.Task() {
            int counter = 0;
            @Override
            public void run() {
                counter++;
                getOwner().setSpellCastPercent((counter*0.01f)/castTime);

                if(counter * 0.01f >= castTime)    {
                    AudioManager.stopSFX(castingSFX);
                    //castingSFX.stop();
                    AudioManager.playSFX(spellSFX, false);
                    //spellSFX.play(0.3f);
                    System.out.println("applying spell");
                    applySpell(sTarget);
                    setCasting(false);
                    getOwner().setCasting(isCasting());
                    useMana();
                    startCooldownTimer();
                }
            }
        },0.01f, 0.01f,(int)(castTime/0.01f)-1);
    }

    @Override
    public void resetDefault() {
        super.resetDefault();
        castTime = MIN_CAST_TIME;
    }

    public void stop()  {
        if(castTimer != null) {
            castTimer.stop();
            castTimer.clear();
            getOwner().setSpellCastPercent(0);
            setCasting(false);
            getOwner().setCasting(isCasting());
            castingSFX.stop();
        }
    }

    protected void checkHasteBuild()   {
        if(getOwner().getTalentTree().getTalent(TalentTree.HASTE_BUILD).isSelected())    {
            castTime = MIN_CAST_TIME - 0.5f;
        }
    }

    public float getCastTime() {
        return castTime;
    }

    protected void setCastTime(float castTime) {
        this.castTime = castTime;
    }

    public Sound getCastingSFX() {
        return castingSFX;
    }

    public void setCastingSFX(Sound castingSFX) {
        this.castingSFX = castingSFX;
    }

    public Sound getSpellSFX() {
        return spellSFX;
    }

    public void setSpellSFX(Sound spellSFX) {
        this.spellSFX = spellSFX;
    }

    protected float getMIN_CAST_TIME() {
        return MIN_CAST_TIME;
    }

    protected void setMIN_CAST_TIME(float MIN_CAST_TIME) {
        this.MIN_CAST_TIME = MIN_CAST_TIME;
    }

    public Timer getCastTimer() {
        return castTimer;
    }

    public void setCastTimer(Timer castTimer) {
        this.castTimer = castTimer;
    }
}
