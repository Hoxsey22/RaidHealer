package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.Assets;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;
import com.hoxseygames.raidhealer.encounters.entities.raid.Raid;
import com.hoxseygames.raidhealer.encounters.entities.raid.RaidMember;

/**
 * Created by Hoxsey on 7/12/2017.
 */

public abstract class Mechanic {

    private String name;
    private Boss owner;
    private Timer timer;
    private RaidMember target;
    private RaidMember mainTank;
    private RaidMember offTank;
    private int damage;
    private float speed;
    private int duration;
    private boolean announce;
    private boolean isActive;
    private String announcementString;
    private Timer announcementTimer;
    private Phase parentPhase;
    private boolean bgMech;


   public Mechanic(String name, int damage, float speed, Boss owner)   {
        this.owner = owner;
        this.name = name;
        this.damage = damage;
        this.speed = speed;

        target = getOwner().getTarget();
        isActive = false;
        announcementString = getOwner().getName()+" is about to "+name+".";
        create();
    }

    public Mechanic(String name, int damage, float speed, Phase phase, boolean bgMech, Boss owner)   {
        this.owner = owner;
        this.name = name;
        this.damage = damage;
        this.speed = speed;
        parentPhase = phase;
        this.bgMech = bgMech;

        target = getOwner().getTarget();
        isActive = false;
        announcementString = getOwner().getName()+" is about to "+name+".";
        create();
    }

    private void create()    {
        System.out.println("Mechanic created");
    }

    public void pausePhase()    {
        if(parentPhase != null) {
            parentPhase.pauseMechanics(this);
        }
    }

   public void resumePhase()   {
        if(parentPhase != null) {
            this.pause();
            parentPhase.resumeMechanics();
        }
    }

    protected abstract void action();

    public void start()    {
        System.out.println("Timer started!");
        timer = new Timer();

        getTimer().scheduleTask(new Timer.Task() {
            int msp = 0;

            @Override
            public void run() {
                msp++;
                if(msp == (int)((speed-1.5f)*10) && !bgMech)    {
                    if(isActive) {
                        pausePhase();
                        if (announce)
                            getOwner().getAnnouncement().setText(getOwner().getName() + " is about to " + name + ".");
                    }
                    else {
                        msp= msp-20;
                    }
                }
                else if(msp == (int)(speed*10))    {
                    if(!bgMech)
                        resumePhase();
                    else {
                        msp = 0;
                    }

                    if(announce)
                        getOwner().getAnnouncement().setText("");
                    action();

                }
                else if(msp == (int)((speed+1.5f)*10))   {
                    msp = 0;
                }
            }
        },0.1f, 0.1f);

        isActive = true;
    }

    public void stop()  {
        isActive = false;
        if(timer != null)    {
            getTimer().stop();
            getTimer().clear();
            System.out.println(name+" announcement timer stopped");
        }
    }

    public void pause() {

        isActive = false;
        if(timer != null)   {
            getTimer().stop();
            System.out.println(name+" timer paused");
        }
    }

    public void resume() {

        isActive = true;
        if(timer != null)   {
            getTimer().start();
            System.out.println(name+" timer resumed");
        }
    }

    public void applyMechanic()  {
        System.out.println("Mechanic applied");
    }


    public String getName() {
        return name;
    }

   public Boss getOwner() {
        return owner;
    }

   public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

   public RaidMember getTarget() {
        return target;
    }

   public void setTarget(RaidMember target) {
        this.target = target;
    }

   public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        if(timer != null)   {
            getTimer().stop();
            getTimer().delay((long)speed);
            getTimer().start();
        }
        this.speed = speed;
    }

    void setMainTank()    {
        mainTank = getOwner().getMainTank();
    }

    void setOffTank()    {
        offTank = getOwner().getOffTank();
    }

    public Raid getRaid() {
        return getOwner().getEnemies();
    }

    public void setRaid(Raid raid) {
        getOwner().setRaid(raid);
    }

    public boolean isAnnounce() {
        return announce;
    }

    public void setAnnounce() {
        this.announce = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Boss owner) {
        this.owner = owner;
    }

   public RaidMember getMainTank() {
        return getOwner().getMainTank();
    }

    public void setMainTank(RaidMember mainTank) {
        this.mainTank = mainTank;
    }

    RaidMember getOffTank() {
        return getOwner().getOffTank();
    }

    public void setOffTank(RaidMember offTank) {
        this.offTank = offTank;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAnnouncementString() {
        return announcementString;
    }

    public void setAnnouncementString(String announcementString) {
        this.announcementString = announcementString;
    }

    public Timer getAnnouncementTimer() {
        return announcementTimer;
    }

    public void setAnnouncementTimer(Timer announcementTimer) {
        this.announcementTimer = announcementTimer;
    }

    public Phase getParentPhase() {
        return parentPhase;
    }

    public void setParentPhase(Phase parentPhase) {
        this.parentPhase = parentPhase;
    }

    public boolean isBgMech() {
        return bgMech;
    }

    public void setBgMech() {
        this.bgMech = true;
    }

    public Assets getAssets()  {
        return getOwner().getAssets();
    }
}
