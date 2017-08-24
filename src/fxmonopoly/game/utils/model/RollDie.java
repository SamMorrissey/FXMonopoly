/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.game.GameController;
import fxmonopoly.game.GameModel;
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
    
    /**
     * Rolls the die and moves the player the corresponding distance.
     * @param controller The controller to utilise for path transitions.
     * @param model The model to call nextPlayer on.
     * @param data The data to manipulate.
     * @param diceRolls The dice roll values.
     */
    public static void diceMove(GameController controller, GameModel model, GameData data, int[] diceRolls) {
        if(data.getActivePlayer().getCanRoll()) {
        
            if(diceRolls[0] == diceRolls[1] && data.getDoublesInARow() == 2) {
                data.getActivePlayerProperty().getValue().moveTo(10);
                data.getActivePlayerProperty().getValue().enterJail();
                controller.pathTransition(model);
                data.getActivePlayer().setCanRoll(false);
                controller.runNextMove();
            }
            else if(diceRolls[0] == diceRolls[1] && !data.getActivePlayer().isInJail()) {
                data.incrementDoublesInARow();
                data.getActivePlayerProperty().getValue().setCanRoll(true);
                data.getActivePlayerProperty().getValue().moveBy(diceRolls[0] + diceRolls[1]);
                model.processRequiredPositionAction(); 
                controller.pathTransition(model);
                controller.runNextMove();
            }
            else if(diceRolls[0] == diceRolls[1] && data.getActivePlayer().isInJail()) {
                data.getActivePlayerProperty().getValue().exitJail();
                data.getActivePlayerProperty().getValue().setCanRoll(false);
                data.getActivePlayerProperty().getValue().moveBy(diceRolls[0] + diceRolls[1]);
                model.processRequiredPositionAction();
                controller.pathTransition(model);
                controller.runNextMove();
            }
            else if(diceRolls[0] != diceRolls[1] && data.getActivePlayer().isInJail()) {
                data.getActivePlayerProperty().getValue().setCanRoll(false);
                model.nextPlayer();
            }
            else if(diceRolls[0] != diceRolls[1] && !data.getActivePlayer().isInJail()) {
                data.getActivePlayerProperty().getValue().setCanRoll(false);
                data.getActivePlayerProperty().getValue().moveBy(diceRolls[0] + diceRolls[1]);
                model.processRequiredPositionAction();
                controller.pathTransition(model);
                controller.runNextMove();
            }
            else {
                data.getActivePlayerProperty().getValue().setCanRoll(false);
            }
        }
    }
}
