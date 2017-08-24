/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.gamedata.players.Player;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
     * Populates the starting positions on the grid for all sprites.
     * @param sprites The sprites to manipulate.
     * @param board The list of BoardButtons.
     * @param anchor The Parent AnchorPane of the board and sprites.
     */
    public static void populateInitialPositions(HashMap<Player, ImageView> sprites, ArrayList<BoardButton> board, AnchorPane anchor) {
        
        int gap = 90 / (sprites.keySet().size());
        int i = 0;
        for(ImageView sprite : sprites.values()) {
            anchor.getChildren().add(sprite);
            
            sprite.setTranslateX((board.get(0).getBoundsInParent().getMinX() + (gap * i)));
            sprite.setTranslateY((board.get(0).getBoundsInParent().getMinY() + 25));
            i++;
        }
    }
    
    /**
     * Retrieves the insets for when a single player is at a specified location.
     * @param boardPosition The board position of the location.
     * @param inJail Whether the player is in jail or not.
     * @return The insets to utilise.
     */
    public static double[] getSinglePositionInsets(int boardPosition, boolean inJail) {
        double[] i = new double[2];
        
        if(boardPosition == 0 || boardPosition < 10) {
            i[0] = 28;
            i[1] = 55;
        }
        else if(boardPosition == 10 && inJail) {
            i[0] = 45;
            i[1] = 10;
        }
        else if(boardPosition == 10) {
            i[0] = 20;
            i[1] = 70;
        }
        else if(boardPosition > 10 && boardPosition < 20) {
            i[0] = 40;
            i[1] = 28;
        }
        else if(boardPosition == 20) {
            i[0] = 65;
            i[1] = 20;
        }
        else if(boardPosition > 20 && boardPosition < 30) {
            i[0] = 28;
            i[1] = 40;
        }
        else if(boardPosition == 30) {
            i[0] = 65;
            i[1] = 65;
        }
        else if(boardPosition > 30) {
            i[0] = 55;
            i[1] = 28;
        }
        
        return i;
    }
}
