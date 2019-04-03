package com.hoxseygames.raidhealer.encounters.spells;

import java.util.Random;

/**
 * Created by Hoxsey on 12/1/2017.
 */

public class CriticalDice {

    private static final Random diceRoll = new Random();

    public static boolean roll(int playerCritical){

        int roll = diceRoll.nextInt(100);

        return roll < playerCritical;

    }

    public static boolean roll(int critical, int max, int min)    {
        int roll = diceRoll.nextInt(max)+min;

        return roll < critical;
    }
}
