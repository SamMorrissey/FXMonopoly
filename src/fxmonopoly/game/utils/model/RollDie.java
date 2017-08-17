/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.die.Die;

/**
 * Supplies the die roll methods, both to move players and just to get the roll
 * numbers.
 * <p>
 * Both methods are static since this class manipulates the game, but does not 
 * need to have an internal representation as it is not an active participant of
 * game state.
 * @author Sam P. Morrissey
 */
public final class RollDie {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private RollDie() {}
    
    /**
     * Rolls the die and moves the player the corresponding distance.
     * @param data The game data to manipulate.
     * @return The int values of both die.
     */
    public static int[] rollDieAndMove(GameData data) {
        int[] i = new int[2];
        
        if(data.getActivePlayer().getCanRoll()) {
            i[0] = data.getDie().rollFirstDie();
            i[1] = data.getDie().rollSecondDie();
        
        
            if(i[0] == i[1] && data.getDoublesInARow() == 2) {
                data.getActivePlayer().moveTo(10);
                data.getActivePlayer().enterJail();
                data.getActivePlayer().setCanRoll(false);
            }
            else if(i[0] == i[1] && !data.getActivePlayer().isInJail()) {
                data.incrementDoublesInARow();
                data.getActivePlayer().setCanRoll(true);
                data.getActivePlayer().moveBy(i[0] + i[1]);
            }
            else if(i[0] == i[1] && data.getActivePlayer().isInJail()) {
                data.getActivePlayer().exitJail();
                data.getActivePlayer().setCanRoll(false);
                data.getActivePlayer().moveBy(i[0] + i[1]);
            }
            else if(i[0] != i[1] && !data.getActivePlayer().isInJail()) {
                data.getActivePlayer().setCanRoll(false);
                data.getActivePlayer().moveBy(i[0] + i[1]);
            }
            else {
                data.getActivePlayer().setCanRoll(false);
            }
        }
        
        return i;
    }
    
    /**
     * Rolls the die and retrieves the value of the die rolls.
     * @param die The die to utilise.
     * @return    The values of both die.
     */
    public static int[] rollDie(Die die) {
        int[] i = new int[2];
        
        i[0] = die.rollFirstDie();
        i[1] = die.rollSecondDie();
        
        return i;
    }
}
