package com.hoxseygames.raidhealer.encounters.entities.bosses.mechanics;

import java.util.ArrayList;

/**
 * PhaseManager navigates through all the phases of which the boss has.
 *
 * Created by Hoxsey on 1/10/2018.
 *
 */

public class PhaseManager {

    private Integer index;
    private ArrayList<Phase> phases;

    public PhaseManager()   {
        phases = new ArrayList<>();
        index = 0;
    }

    /**
     * Adds a phase to the manager.
     *
     * @param newPhase: The phase that is being added.
     */
    public void addPhase(Phase newPhase)    {
        newPhase.setParent(this);
        phases.add(newPhase);
    }

    /**
     * Navigates to the next phase.
     */
    public void nextPhase() {
        index++;
        if(index == phases.size())  {
            index = 0;
        }
        startPhase();

    }

    /**
     * Starts the phase that is indexed.
     */
    public void startPhase()    {
        if(phases.size() > 0) {
            phases.get(index).start();
        }
    }

    /**
     * Resets all the phases.
     */
    public void reset() {
        cleanPhases();
        index = 0;
    }

    /**
     * Clears all the Phase's getTimer().
     */
    private void cleanPhases()  {
        if(phases.size() > 0) {
            phases.get(index).clear();
            for (int i = 0; i < phases.size(); i++) {
                phases.get(i).clear();
                phases.remove(i);
            }
            phases.clear();
        }
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ArrayList<Phase> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<Phase> phases) {
        this.phases = phases;
    }
}
