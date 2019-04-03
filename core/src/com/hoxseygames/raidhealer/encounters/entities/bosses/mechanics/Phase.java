package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import com.badlogic.gdx.utils.Timer;
import com.hoxseygames.raidhealer.encounters.entities.bosses.Boss;

import java.util.ArrayList;

/**
 * Phase is a container for multiple mechanics that also keeps track of how long the mechanics will
 * be applied. The parent, PhaseManager will manage the phase to stop or to start.
 *
 * Created by Hoxsey on 8/20/2017.
 *
 */

public class Phase {

    private String name;
    private PhaseManager parent;
    private Boss owner;
    private float length;
    private float percentage;
    private float delay;
    private boolean isActive;
    private Timer timer;
    private ArrayList<Mechanic> mechanics;
    private boolean isTimed;
    private boolean isNameChange;
    private String nameChange;

    /**
     * @param owner: The Boss which owns the phase.
     * @param length: The length of which the phase last.
     * @param mechanics: List of all the mechanics that exist in the phase.
     */
    public Phase(Boss owner, float length, Mechanic...mechanics) {
        this.owner = owner;
        this.length = length;
        name = "";
        isActive = false;
        this.mechanics = new ArrayList<>();
        delay = 0f;
        isTimed = true;

        for (Mechanic mech:mechanics) {
            mech.setParentPhase(this);
            this.mechanics.add(mech);
        }
    }

    /**
     * @param owner: The Boss which owns the phase.
     * @param length: The length of which the phase last.
     * @param mechanics: List of all the mechanics that exist in the phase.
     */
    public Phase(Boss owner, String name, float length, Mechanic...mechanics) {
        this.owner = owner;
        this.length = length;
        this.name = name;
        isActive = false;
        this.mechanics = new ArrayList<>();
        delay = 0f;
        isTimed = true;

        for (Mechanic mech:mechanics) {
            mech.setParentPhase(this);
            this.mechanics.add(mech);
        }
    }

    /**
     * @param owner: The Boss which owns the phase.
     * @param mechanics: List of all the mechanics that exist in the phase.
     */
    public Phase(Boss owner, int percentage, Mechanic...mechanics) {
        this.owner = owner;
        this.percentage = (float)percentage/100;
        name = "";
        isActive = false;
        this.mechanics = new ArrayList<>();
        delay = 0f;
        isTimed = false;

        for (Mechanic mech:mechanics) {
            mech.setParentPhase(this);
            this.mechanics.add(mech);
        }
    }

    /**
     * @param owner: The Boss which owns the phase.
     * @param mechanics: List of all the mechanics that exist in the phase.
     */
    public Phase(Boss owner,String name, int percentage, Mechanic...mechanics) {
        this.owner = owner;
        this.percentage = (float)percentage/100;
        this.name = name;
        isActive = false;
        this.mechanics = new ArrayList<>();
        delay = 0f;
        isTimed = false;

        for (Mechanic mech:mechanics) {
            mech.setParentPhase(this);
            this.mechanics.add(mech);
        }
    }

    /**
     * @param owner: The Boss which owns the phase.
     * @param length: The length of which the phase last.
     * @param mechanics: List of all the mechanics that exist in the phase.
     */
    public Phase(Boss owner, float length, float delay, Mechanic...mechanics) {
        this.owner = owner;
        this.length = length;
        name = "";
        isActive = false;
        this.mechanics = new ArrayList<>();
        this.delay = delay;

        for (Mechanic mech:mechanics) {
            mech.setParentPhase(this);
            this.mechanics.add(mech);
        }
    }


    /**
     * Starts all mechanics and timer of which the phase will last.
     */
    public void start() {
        timer = new Timer();
        isActive = true;
        if(isNameChange)    {
            changeBossName();
        }
        /*
        if(name.equalsIgnoreCase("")) {
            Timer phaseTitleTimer = new Timer();
            getOwner().getAnnouncement().setText(name);
            phaseTitleTimer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    getOwner().getAnnouncement().setText("");
                }
            }, 1f, 1);
        }*/
        startMechanics();

        if(isTimed) {
            getTimer().scheduleTask(new Timer.Task() {
                int count = 0;
                @Override
                public void run() {
                    count++;
                    if(count == length*10) {
                        getTimer().stop();
                        getTimer().clear();
                        stopMechanics();

                        parent.nextPhase();
                    }
                }
            }, 0.1f,0.1f);
        }
        else    {
            getTimer().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if(getOwner().getHpPercent() <= percentage ) {
                        getTimer().stop();
                        getTimer().clear();
                        stopMechanics();

                        parent.nextPhase();
                    }
                }
            },0.1f,0.1f);
        }
    }

    /**
     * Stops the phase.
     */
    private void stop()  {
        if(timer != null)   {
            getTimer().stop();
        }
        if(mechanics != null)    {
            stopMechanics();
        }

    }

    /**
     * Clears the phase.
     */
    public void clear() {
        if(timer != null) {
            stop();
            getTimer().clear();
        }
        if(mechanics != null)   {
            for(int i = 0; i < mechanics.size(); i++)   {
                mechanics.remove(i);
            }
        }
    }

    /**
     * Starts all the mechanics listed in the phase.
     */
    private void startMechanics()   {
        for(int i = 0; i < mechanics.size(); i++)   {
            mechanics.get(i).start();
        }
    }

    /**
     * Stops all the mechanics listed in the phase.
     */
    private void stopMechanics()   {
        for(int i = 0; i < mechanics.size(); i++)   {
            mechanics.get(i).stop();
        }
        getOwner().getAnnouncement().setText("");
    }

    public void pauseMechanics(Mechanic currentMechanic)    {
        long millis=System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);

        for(int i = 0; i < mechanics.size(); i++)   {
            if(!currentMechanic.getName().equalsIgnoreCase(mechanics.get(i).getName()) && !mechanics.get(i).isBgMech()) {
                mechanics.get(i).pause();
            }
        }
    }

    public void pauseMechanics()    {
        long millis=System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);

        for(int i = 0; i < mechanics.size(); i++)   {
            mechanics.get(i).pause();
        }
    }

    public void resumeMechanics()   {
        for(int i = 0; i < mechanics.size(); i++)   {
            if(!mechanics.get(i).isBgMech()) {
                mechanics.get(i).resume();
            }
        }
    }

    private void changeBossName()    {
        getOwner().getNameText().setText(nameChange);
    }

    /*****************************************************************
     *
     *                      GETTERS AND SETTERS
     *
     ******************************************************************/

    public PhaseManager getParent() {
        return parent;
    }

    public void setParent(PhaseManager parent) {
        this.parent = parent;
    }

    private Boss getOwner() {
        return owner;
    }

    public void setOwner(Boss owner) {
        this.owner = owner;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public String getNameChange() {
        return nameChange;
    }

    public void setNameChange(String nameChange) {
        this.nameChange = nameChange;
    }

    public boolean isNameChange() {
        return isNameChange;
    }

    public void setNameChange() {
        isNameChange = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public ArrayList<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(ArrayList<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }
}
