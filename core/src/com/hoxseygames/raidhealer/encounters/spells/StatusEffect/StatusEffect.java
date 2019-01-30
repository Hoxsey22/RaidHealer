package com.hoxseygames.raidhealer.encounters.spells.StatusEffect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

/**
 * Created by Hoxsey on 11/28/2017.
 */

public abstract class StatusEffect{

    public static final int NONE = 0;
    public static final int DAMAGE_TAKEN_MODIFIER = 1;
    public static final int HEALING_TAKEN_MODIFIER = 2;


    private String name;
    private int id;
    private String description;
    private boolean isActive;
    private RaidMember target;
    private Texture icon;
    private float duration;
    private float speed;
    private float startTime;
    private int modValue;
    private int numOfTicks;
    private boolean dispellable;
    protected boolean isDispelled;
    private Timer timer;
    private Assets assets;
    private StatusEffectList parent;
    private int type;

    /**
     * @param id: ID of the status effect and should be unique.
     * @param name: Name of the status effect.
     * @param description: A brief description of the status effect.
     * @param texture: The texture that will create the icon.
     * @param duration: The time of which the status effect will last.
     * @param speed: The time of which the status effect will apply effect.
     * @param modValue: The mod value that will change a specific stat.
     * @param dispellable : Check if this status effect can be dispel.
     */
    protected StatusEffect(int id, String name, String description, Texture texture, float duration, float speed, int modValue, boolean dispellable, Assets assets)   {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = texture;
        this.duration = duration;
        this.speed = speed;
        this.modValue = modValue;
        isActive = true;
        numOfTicks = (int)(duration/speed);
        this.dispellable = dispellable;
        timer = new Timer();
        this.assets = assets;
        //this.parent = parent;
    }

    protected abstract void startConditions();

    public void start() {
        startConditions();

        timer.scheduleTask(new Timer.Task() {
            int currentCount;
            int tickCount = (int)(speed/0.1f);
            int durationCount = (int)(duration/0.1f);

            @Override
            public void run() {
                additionalConditions();
                if(isActive) {
                    currentCount++;
                    if (currentCount % tickCount == 0) {
                        applyEffect();
                    }
                    if (currentCount == durationCount) {
                        remove();
                    }
                }
            }
        },0.1f, 0.1f);
    }

    public void dispel()    {
        if(this.isDispellable())    {
            isDispelled = true;
            remove();
        }
    }

    public void pause() {
        if(timer != null)   {
            timer.stop();
            isActive = false;
        }
    }

    public void resume()    {
        if(timer != null)   {
            timer.start();
            isActive = true;
        }
    }

    public void remove()    {
        if(timer != null) {
            timer.stop();
            timer.clear();
        }

        isActive = false;

        removeFromParent();
    }

    protected void removeFromParent()  {
        if(parent != null)  {
            parent.getStatusEffects().remove(this);
        }
    }

    protected abstract void additionalConditions();

    public abstract void applyEffect();

    public abstract int modifyOutput(int output);

    /*****************************************************************
     *
     *                      GETTERS AND SETTERS
     *
     ******************************************************************/
    
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    protected RaidMember getTarget() {
        return target;
    }

    public void setTarget(RaidMember target) {
        this.target = target;
    }

    public Texture getIcon() {
        return icon;
    }

    protected void setIcon(Texture icon) {
        this.icon = icon;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    protected int getModValue() {
        return modValue;
    }

    public void setModValue(int modValue) {
        this.modValue = modValue;
    }

    public int getNumOfTicks() {
        return numOfTicks;
    }

    public void setNumOfTicks(int numOfTicks) {
        this.numOfTicks = numOfTicks;
    }

    public boolean isDispellable() {
        return dispellable;
    }

    public void setDispellable(boolean dispellable) {
        this.dispellable = dispellable;
    }

    protected Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public StatusEffectList getParent() {
        return parent;
    }

    public void setParent(StatusEffectList parent) {
        this.parent = parent;
    }

    public int getType() {
        return type;
    }

    protected void setType(int type) {
        this.type = type;
    }

    public boolean isDispelled() {
        return isDispelled;
    }

    public void setDispelled(boolean dispelled) {
        isDispelled = dispelled;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public String toString()    {
        return getName()+"\n" +
                "ID: "+getId()+"\n" +
                "Description: "+getDescription()+"\n" +
                "Target :";
    }
}
