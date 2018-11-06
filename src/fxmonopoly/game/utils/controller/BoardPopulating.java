/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameController;
import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.board.locations.BaseOwnableLocation;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.utils.GameDialogs;
import fxmonopoly.utils.StageManager;

import java.util.ArrayList;
import java.util.HashMap;

import fxmonopoly.utils.interfacing.NodeReference;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
     * @param manager The manager to retrieve dialogs from.
     */
    public static void generateButtons(ArrayList<BoardButton> board, GridPane grid, HashMap<Player, Color> map, GameModel model, StageManager manager) {
        
        int i;
        for(i = 0; i < 40; i++) {
            board.add(new BoardButton());
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
            
            Dialog position = manager.getGameDialog(GameDialogs.BLANK);
            int indexOf = board.indexOf(button);
            
            button.setOnAction(e -> {
                if(button.isOwnable() && !button.isOwned()) {
                    DialogContent content = new DialogContent();
                    Dialog dialog = content.genericPositionDialog(position, 170);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    Image image = GameController.getBoardLocationImage(board, indexOf);
                    ((ImageView) content.retrieveNodeByName(NodeReference.BOARD_LOCATION_IMAGE.name())).setImage(image);
                    Label label = content.retrieveNodeByName(NodeReference.BOARD_LOCATION_TEXT.name());
                    label.setText(model.retrieveLocation(indexOf).getName() + "\n" + "You must purchase this property to reveal more information");
                    dialog.showAndWait();
                }
                else if(button.isOwned()) {
                    if (button.userIsOwner(model.getUser())) {
                        if (button.getLocation() instanceof PropertyLocation) {
                            DialogContent content = new DialogContent();
                            Dialog dialog = content.ownedOwnableLocationDialog(position, 170);
                            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                            Image image = GameController.getBoardLocationImage(board, indexOf);
                            ((ImageView) content.retrieveNodeByName(NodeReference.BOARD_LOCATION_IMAGE.name())).setImage(image);
                            Label label = content.retrieveNodeByName(NodeReference.BOARD_LOCATION_TEXT.name());
                            label.setText(((BaseOwnableLocation) board.get(indexOf).getLocation()).getUserOwnedString());
                            content.retrieveNodeByName(NodeReference.DEVELOP_OWNED_PROPERTY.name()).addEventFilter(ActionEvent.ACTION, event ->
                                model.userDevelopProperty((PropertyLocation) board.get(indexOf).getLocation())
                            );
                            content.retrieveNodeByName(NodeReference.UNDEVELOP_OWNED_PROPERTY.name()).addEventFilter(ActionEvent.ACTION, event ->
                                model.userRegressProperty((PropertyLocation) board.get(indexOf).getLocation())
                            );
                            Button mortgage = content.retrieveNodeByName(NodeReference.MORTGAGE_OWNED_PROPERTY.name());
                            mortgage.addEventFilter(ActionEvent.ACTION, event -> {
                                mortgageProcess(board, indexOf, model);
                                if (mortgage.getText().equals("Mortgage")) mortgage.setText("Demortgage");
                                else mortgage.setText("Mortgage");
                            });
                            dialog.showAndWait();

                        } else {
                            DialogContent.genericOwnedPropertySetUp(
                                position,
                                GameController.getBoardLocationImage(board, indexOf),
                                ((BaseOwnableLocation) board.get(indexOf).getLocation()).getUserOwnedString(),
                                () -> mortgageProcess(board, indexOf, model)
                            );
                        }
                    } else {
                        DialogContent.genericUnownedPropertyDialog(
                            position,
                            GameController.getBoardLocationImage(board, board.indexOf(button)),
                            model.retrieveLocation(board.indexOf(button)).getName() + "\n" + "Owner: " + ((BaseOwnableLocation) board.get(board.indexOf(button)).getLocation()).getOwner().getName()
                        );
                    }
                }
                else {
                    DialogContent content = new DialogContent();
                    Dialog dialog = content.genericPositionDialog(position, 150);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    Image image = GameController.getBoardLocationImage(board, indexOf);
                    ((ImageView) content.retrieveNodeByName(NodeReference.BOARD_LOCATION_IMAGE.name())).setImage(image);
                    Label label = content.retrieveNodeByName(NodeReference.BOARD_LOCATION_TEXT.name());
                    label.setText(model.retrieveLocation(indexOf).getName());
                    dialog.showAndWait();
                }
            });
            
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

    private static void mortgageProcess(ArrayList<BoardButton> board, int indexOf, GameModel model) {
        if(((PropertyLocation) board.get(indexOf).getLocation()).getMortgaged())
            model.deMortgageLocation(board.get(indexOf).getLocation());
        else
            model.mortgageLocation(board.get(indexOf).getLocation());

    }
    
    /**
     * Creates a ChangeListener for both the level of development on a property,
     * and the owner of the property in order to switch styles correctly.
     * @param button The BoardButton on which to attach the listener.
     * @param map The map of colours to assimilate style correctly.
     */
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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

    private static void populateGridPane(ArrayList<BoardButton> board, GridPane grid) {
        for (int i = 0; i < board.size(); i++) {
            if (i < 10) {
                grid.add(board.get(i), 10 - i, 10);
            } else if (i < 20) {
                grid.add(board.get(i), 0, 20 - i);
            } else if (i < 30) {
                grid.add(board.get(i), i - 20, 0);
            } else if (i < 40) {
                grid.add(board.get(i), 10, i - 30);
            }
        }
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
