/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.players.Player;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

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
     * Performs a path transition on the specified sprite, utilising the model for
     * ensuring consistency, and the board list to get the position bounds.
     * @param sprite The sprite to animate.
     * @param model The model to utilise.
     * @param board The board to check bounds positioning.
     */
    public static void pathTransition(ImageView sprite, GameModel model, ArrayList<BoardButton> board, Dialog position) {
        Path path = new Path();
        
        double[] i = getSinglePositionInsets(model.getActivePlayer().getPosition(), model.getActivePlayer().getIsInJailProperty().getValue());
        
        Bounds bounds = board.get(model.getActivePlayer().getPosition()).getBoundsInParent();
        
        double adjustedX = bounds.getMinX() + i[0];
        double adjustedY = bounds.getMinY() + i[1]; 
        
        path.getElements().add(new MoveTo(sprite.boundsInParentProperty().getValue().getMinX(), sprite.boundsInParentProperty().getValue().getMinY()));
        path.getElements().add(new LineTo(adjustedX, adjustedY));
        
        PathTransition pathTran = new PathTransition();
        pathTran.setDuration(Duration.millis(1500));
        pathTran.setNode(sprite);
        pathTran.setPath(path);
        pathTran.setOnFinished(e -> {
            model.processRequiredPositionAction();
            if(model.userIsActive()) {
                Platform.runLater(() -> {
                    DialogContent.getNewPositionDialog(position, model, board);
                });
                
            }
        });
        pathTran.play();
    }
    
    /**
     * Retrieves the insets for when a single player is at a specified location.
     * @param boardPosition The board position of the location.
     * @return The insets to utilise.
     */
    private static double[] getSinglePositionInsets(int boardPosition, boolean inJail) {
        double[] i = new double[2];
        
        if(boardPosition == 0 || boardPosition < 10) {
            i[0] = 28;
            i[1] = 55;
        }
        else if(boardPosition == 10 && inJail) {
            i[0] = 65;
            i[1] = 65;
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
