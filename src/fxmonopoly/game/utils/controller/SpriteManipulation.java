/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

/**
 * Provides manipulations on player sprites to ensure that consistency and accurate
 * position keeping are maintained.
 * <p>
 * Exclusively provides static methods since this utility has no need to represent
 * any state, it merely manipulates existing states.
 * @author Sam P. Morrissey
 */
public class SpriteManipulation {
    
    /**
     * Ensures that the class cannot be instantiated, since it has no reason to 
     * be, as it has no state and only static methods.
     */
    private SpriteManipulation() {}
    
    /**
     * Retrieves the insets for when a single player is at a specified location.
     * @param boardPosition The board position of the location.
     * @return The insets to utilise.
     */
    private static double[] getSinglePositionInsets(int boardPosition) {
        double[] i = new double[2];
        
        if(boardPosition == 0 || boardPosition < 10) {
            i[0] = 8;
            i[1] = 40;
        }
        else if(boardPosition == 10) {
            i[0] = 0;
            i[1] = 0;
        }
        else if(boardPosition > 10 && boardPosition < 20) {
            i[0] = 10;
            i[1] = 8;
        }
        else if(boardPosition == 20) {
            i[0] = 45;
            i[1] = 0;
        }
        else if(boardPosition > 20 && boardPosition < 30) {
            i[0] = 8;
            i[1] = 10;
        }
        else if(boardPosition == 30) {
            i[0] = 45;
            i[1] = 45;
        }
        else if(boardPosition > 30) {
            i[0] = 40;
            i[1] = 8;
        }
        
        return i;
    }
}
