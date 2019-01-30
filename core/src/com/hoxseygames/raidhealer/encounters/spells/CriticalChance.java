package com.hoxseygames.raidhealer.encounters.spells;

import java.util.Random;

/**
 * Created by Hoxsey on 6/29/2017.
 */
public class CriticalChance {

    private Random diceRoll;
    private int max;
    private int min;
    private int chanceThreshold;

    public CriticalChance(int chanceThreshold) {
        this.chanceThreshold = chanceThreshold;
        create();
    }

    private void create()    {
        diceRoll = new Random();
        max = 100;
        min = 0;
    }

    private int roll()  {
        return diceRoll.nextInt(max) + min;
    }

    public void setChanceThreshold(int newChanceThreshold)  {
        chanceThreshold = newChanceThreshold;
    }

    public boolean isCritical() {
        if(roll() < chanceThreshold)    {
            System.out.println("CRITICAL!");
            return true;
        }
        else {
            return false;
        }
    }
}
