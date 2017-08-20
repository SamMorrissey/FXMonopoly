/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.die;

import java.util.Random;

/**
 * Defines the Die class, utilised to avoid having different Random instances
 * and calls dotted all around the application.
 * @author Sam P. Morrissey
 */
public class Die {
    
    private final Random die1;
    private final Random die2;
    
    private int dieOneRoll;
    private int dieTwoRoll;
    
    /**
     * Creates a new  Die instance containing two Random instances pertaining to
     * each die.
     */
    public Die() {
        die1 = new Random();
        die2 = new Random();
    }
    
    /**
     * Rolls the first die and returns the value of the roll.
     * @return The value of the roll.
     */
    public int rollFirstDie() {
        dieOneRoll = die1.nextInt(6) + 1;
        return dieOneRoll;
    }
    
    /**
     * Retrieves the value of the last rollFirstDie method call.
     * @return The previous roll value.
     */
    public int getDieOneLastValue() {
        return dieOneRoll;
    }
    
    /**
     * Rolls the second die and returns the value of the roll.
     * @return The value of the roll.
     */
    public int rollSecondDie() {
        dieTwoRoll = die2.nextInt(6) + 1;
        return dieTwoRoll;
    }
    
    /**
     * Retrieves the value of the last rollSecondDie method call.
     * @return The previous roll value.
     */
    public int getDieTwoLastValue() {
        return dieTwoRoll;
    }
    
    /**
     * Retrieves the combined value of the last rolls of the two die. Therefore
     * should be used after a call to roll both die has been made.
     * @return The total value of both die rolls.
     */
    public int dieRollTotal() {
        return dieOneRoll + dieTwoRoll;
    }
    
    /**
     * Checks the previous die roll values to check whether the result was a 
     * double i.e. equal to each other.
     * @return True if the die are doubles, false otherwise.
     */
    public boolean isDoubles() {
        return dieOneRoll == dieTwoRoll;
    }
}
