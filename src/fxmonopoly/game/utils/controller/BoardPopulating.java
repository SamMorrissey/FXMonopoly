/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.players.Player;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

/**
 * Provides the restyling methods necessary to ensure that the board buttons are
 * correctly displaying the relevant information to the user.
 * <p>
 * Exclusively contains static methods since this utility has no need to represent
 * any state, it merely manipulates existing states.
 * @author Sam P. Morrissey
 */
public class BoardPopulating {
    
    /**
     * Ensures that the class cannot be instantiated, since it has no reason to 
     * be, as it has no state and only static methods.
     */
    private BoardPopulating() {}
    
    /**
     * Generates the board buttons, populates the GridPane provided with those elements,
     * then enforces the BoardButtons to fill their respective GridCells.
     * @param board The ArrayList in which to place the buttons.
     * @param grid The GridPane in which to locate the buttons.
     * @param map The Map on which to base the styles.
     * @param model The GameModel to utilise.
     */
    public static void generateButtons(ArrayList<BoardButton> board, GridPane grid, HashMap<Player, Color> map, GameModel model) {
        
        int i;
        for(i = 0; i < 40; i++) {
            board.add(new BoardButton());
            //board.get(board.size() - 1).getStyleClass().add("board-button");
            board.get(board.size() - 1).setStyle(initialStyle());   
        }
        
        for(BoardButton button : board) {
            
            button.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            
            if(model.locationIsOwnable(model.retrieveLocation(board.indexOf(button)))) {
                button.setOwnable(true);
                button.setLocation(model.retrieveLocation(board.indexOf(button)));
                
                if(button.getLocation() instanceof PropertyLocation)
                    propertyListeners(button, map);
                else if(button.getLocation() instanceof RailwayLocation)
                    railwayListener(button, map);
                else if(button.getLocation() instanceof UtilityLocation)
                    utilityListener(button, map);
            }
            
            button.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue) {
                    button.setStyle("-fx-border-color: rgba(0, 200, 0, 1);\n" +
                                    "-fx-border-width: 4;\n" +
                                    "-fx-border-style: solid inside; \n" +
                                    "-fx-background-color: transparent;");
                }
                else {
                    button.setStyle(adjustedStyle(button, map));
                }
            });
        }
        
        populateGridPane(board, grid);
        
        for(ColumnConstraints constraint : grid.getColumnConstraints()) {
            constraint.setFillWidth(true);
        }
        
        for(RowConstraints constraint : grid.getRowConstraints()) {
            constraint.setFillHeight(true);
        }
    }
    
    /**
     * Creates a ChangeListener for both the level of development on a property,
     * and the owner of the property in order to switch styles correctly.
     * @param button The BoardButton on which to attach the listener.
     * @param map The map of colours to assimilate style correctly.
     */
    private static void propertyListeners(BoardButton button, HashMap<Player, Color> map) {
        ((PropertyLocation) button.getLocation()).getHousesProperty().addListener((observable, oldValue, newValue) -> {
            button.styleProperty().setValue(adjustedStyle(button, map));
        });
        
        ((PropertyLocation) button.getLocation()).getOwnerProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) 
                button.styleProperty().setValue(adjustedStyle(button, map));
            else
                button.styleProperty().setValue(initialStyle());
        });
    }
    
    /**
     * Creates a ChangeListener for the owner of the Railway.
     * @param button The BoardButton on which to attach the listener.
     * @param map The map of colours to assimilate style correctly.
     */
    private static void railwayListener(BoardButton button, HashMap<Player, Color> map) {
        ((RailwayLocation) button.getLocation()).getOwnerProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) 
                button.styleProperty().setValue(adjustedStyle(button, map));
            else
                button.styleProperty().setValue(initialStyle());
        });
    }
    
    /**
     * Creates a ChangeListener for the owner of the Utility.
     * @param button The BoardButton on which to attach the listener.
     * @param map The map of colours to assimilate style correctly.
     */
    private static void utilityListener(BoardButton button, HashMap<Player, Color> map) {
        ((UtilityLocation) button.getLocation()).getOwnerProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) 
                button.styleProperty().setValue(adjustedStyle(button, map));
            else
                button.styleProperty().setValue(initialStyle());
        });
    }
    
    /**
     * Fills the provided GridPane with the BoardButtons in the specified ArrayList.
     * Places the buttons in their respective grid position.
     * @param board The ArrayList of BoardButtons to populate with.
     * @param grid The GridPane to populate.
     */
    private static void populateGridPane(ArrayList<BoardButton> board, GridPane grid) {
        grid.add(board.get(0), 10, 10);
        grid.add(board.get(1), 9, 10);
        grid.add(board.get(2), 8, 10);
        grid.add(board.get(3), 7, 10);
        grid.add(board.get(4), 6, 10);
        grid.add(board.get(5), 5, 10);
        grid.add(board.get(6), 4, 10);
        grid.add(board.get(7), 3, 10);
        grid.add(board.get(8), 2, 10);
        grid.add(board.get(9), 1, 10);
        grid.add(board.get(10), 0, 10);
        grid.add(board.get(11), 0, 9);
        grid.add(board.get(12), 0, 8);
        grid.add(board.get(13), 0, 7);
        grid.add(board.get(14), 0, 6);
        grid.add(board.get(15), 0, 5);
        grid.add(board.get(16), 0, 4);
        grid.add(board.get(17), 0, 3);
        grid.add(board.get(18), 0, 2);
        grid.add(board.get(19), 0, 1);
        grid.add(board.get(20), 0, 0);
        grid.add(board.get(21), 1, 0);
        grid.add(board.get(22), 2, 0);
        grid.add(board.get(23), 3, 0);
        grid.add(board.get(24), 4, 0);
        grid.add(board.get(25), 5, 0);
        grid.add(board.get(26), 6, 0);
        grid.add(board.get(27), 7, 0);
        grid.add(board.get(28), 8, 0);
        grid.add(board.get(29), 9, 0);
        grid.add(board.get(30), 10, 0);
        grid.add(board.get(31), 10, 1);
        grid.add(board.get(32), 10, 2);
        grid.add(board.get(33), 10, 3);
        grid.add(board.get(34), 10, 4);
        grid.add(board.get(35), 10, 5);
        grid.add(board.get(36), 10, 6);
        grid.add(board.get(37), 10, 7);
        grid.add(board.get(38), 10, 8);
        grid.add(board.get(39), 10, 9);
    }
    
    /**
     * Sets the initial default style String.
     * @return The initial default style String.
     */
    private static String initialStyle() {
        return "-fx-background-color: transparent;";
    }
    
    /**
     * Retrieves the adjusted style string based on the properties related to 
     * each location.
     * @param button The BoardButton to be styled.
     * @param map The map of colours to utilise for styling.
     * @return The CSS style string.
     */
    private static String adjustedStyle(BoardButton button, HashMap<Player, Color> map) {
        
        if(button.getLocation() instanceof PropertyLocation) {
            return propertyStyle((PropertyLocation) button.getLocation(), map);
        }
        else if(button.getLocation() instanceof RailwayLocation) {
            return railwayStyle((RailwayLocation) button.getLocation(), map);
        }
        else if(button.getLocation() instanceof UtilityLocation) {
            return utilityStyle((UtilityLocation) button.getLocation(), map);
        }
        else {
            return initialStyle();
        }
    }
    
    /**
     * Retrieves the specific CSS style string based on the properties of the 
     * specified Location.
     * @param location The location to determine the state of.
     * @param map The map of colours to utilise.
     * @return The CSS style string.
     */
    private static String propertyStyle(PropertyLocation location, HashMap<Player, Color> map) {
        if(location.getOwner() != null) {
            int borderWidth; 
            if(location.getIsHotel()) {
                borderWidth = 7;
            }
            else {
                borderWidth = location.getNumberOfHouses() + 2;
            }
            
            return "-fx-border-width: " + borderWidth + "; \n" +
                   "-fx-border-color: " + "#" + map.get(location.getOwner()).toString().substring(2) + "; \n" +
                   "-fx-border-style: solid inside; \n" +
                   "-fx-background-color: transparent; \n";    
        }
        else {
            return initialStyle();
        }
    }
    
    /**
     * Retrieves the specific CSS style string based on the properties of the 
     * specified Location.
     * @param location The location to determine the state of.
     * @param map The map of colours to utilise.
     * @return The CSS style string.
     */
    private static String railwayStyle(RailwayLocation location, HashMap<Player, Color> map) {
        
        if(location.getOwner() != null) {
            
            return "-fx-border-width: 3; \n" +
                   "-fx-border-color: " + "#" + map.get(location.getOwner()).toString().substring(2) + "; \n" +
                   "-fx-border-style: solid inside; \n" +
                   "-fx-background-color: transparent; \n";  
        }
        else {
            return initialStyle();
        }
    }
    
    /**
     * Retrieves the specific CSS style string based on the properties of the 
     * specified Location.
     * @param location The location to determine the state of.
     * @param map The map of colours to utilise.
     * @return The CSS style string.
     */
    private static String utilityStyle(UtilityLocation location, HashMap<Player, Color> map) {
        
        if(location.getOwner() != null) {
            
            return "-fx-border-width: 3; \n" +
                   "-fx-border-color: " + "#" + map.get(location.getOwner()).toString().substring(2) + "; \n" +
                   "-fx-border-style: solid inside; \n" +
                   "-fx-background-color: transparent; \n";  
        }
        else {
            return initialStyle();
        }
    }
}
