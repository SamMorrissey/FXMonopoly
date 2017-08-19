/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gameinitsettings;

import fxmonopoly.utils.Dialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;
import fxmonopoly.utils.interfacing.Manageable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class for the GameInitSettings window
 *
 * @author Sam Morrissey
 */
public class GameInitSettingsController implements Initializable, Manageable {
    private StageManager manager;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private ListView<String> colourSelection;
    
    @FXML
    private ListView<String> pieceSelection;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button okButton;
    
    // The observable lists utilised for the colour and sprite selections
    private ObservableList<String> colourData;
    private ObservableList<String> pieceData;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateColours();
        populateSprites();
        
        backButton.setOnAction(e -> manager.changeScene(View.MAIN_MENU));
        
        okButton.setOnAction(e -> {
            if(nameField.getText().isEmpty() || colourSelection.getSelectionModel().getSelectedItem() == null ||
                    pieceSelection.getSelectionModel().getSelectedItem() == null) {
                
                manager.getDialog(Dialogs.GAME_INIT_BAD_SEL);
            }
            else if(nameField.getText().contains("Rick") || nameField.getText().contains("rick") || nameField.getText().contains("Astley") || nameField.getText().contains("astley")) {
                manager.getDialog(Dialogs.RROLLED);
                
                
            }
            else {
                manager.changeScene(View.GAME);
            }
            
        }); 
    }    
    
    /**Sets the StageManager utilised within this controller.
     * @param manager The StageManager to be utilised by this controller.
     */
    @Override
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
    
    /**
     * Populates the CellFactory of the colour selection ListView, with rectangles
     * filled on the basis of an Observable collection of Strings.
     */
    private void populateColours() {
        colourData = FXCollections.observableArrayList("BLUE", "CYAN","AQUAMARINE", "LAWNGREEN", "GREEN", "PURPLE", 
                                                       "SALMON", "RED", "MAROON", "BURLYWOOD", "ORANGE", "YELLOW");
        colourSelection.setItems(colourData);
        
        colourSelection.setCellFactory(e -> new ListCell<String>() {
            private Rectangle rect = new Rectangle(115.0, 14.0);
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if(empty) {
                    setGraphic(null);
                }
                else {
                    
                    /*
                    switch (name) {
                        case "BLUE":
                            rect.setFill(Color.BLUE);
                            break;
                        case "AQUAMARINE":
                            rect.setFill(Color.AQUAMARINE);
                            break;
                        case "CYAN":
                            rect.setFill(Color.CYAN);
                            break;
                        case "green":
                            rect.setFill(Color.GREEN);
                            break;
                        case "lawngreen":
                            rect.setFill(Color.LAWNGREEN);
                            break;
                        case "purple":
                            rect.setFill(Color.PURPLE);
                            break;
                        case "salmon":
                            rect.setFill(Color.SALMON);
                            break;
                        case "red":
                            rect.setFill(Color.RED);
                            break;
                        case "maroon":
                            rect.setFill(Color.MAROON);
                            break;
                        case "burlywood":
                            rect.setFill(Color.BURLYWOOD);
                            break;
                        case "orange":
                            rect.setFill(Color.ORANGE);
                            break;
                        case "yellow":
                            rect.setFill(Color.YELLOW);
                            break;
                        default:
                            break;
                    }
                    */
                    
                rect.setFill(Color.valueOf(name));
                setGraphic(rect);
                }
            }
        });
    }
    
    /**
     * Populates the CellFactory of the board piece selection ListView, with in game
     * sprites based on an Observable collection of Strings.
     */
    private void populateSprites() {
        pieceData = FXCollections.observableArrayList("Boot", "Car", "Dog", "Hat", "Iron",
                                                      "Ship", "Thimble", "Barrow");
        pieceSelection.setItems(pieceData);
        
        pieceSelection.setCellFactory(e -> new ListCell<String>() {
            private ImageView view = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if(empty) {
                    setGraphic(null);
                }
                else {
                    
                    /*
                    switch (name) {
                        case "boot":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Boot.png"));
                            break;
                        case "car":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Car.png"));
                            break;
                        case "dog":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Dog.png"));
                            break;
                        case "hat":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Hat.png"));
                            break;
                        case "iron":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Iron.png"));
                            break;
                        case "ship":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Ship.png"));
                            break;
                        case "thimble":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Thimble.png"));
                            break;
                        case "barrow":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Wheelbarrow.png"));
                            break;
                        default:
                            break;
                    }
                    */
                view.setImage(new Image("fxmonopoly/resources/images/sprites/" + name + ".png"));
                setGraphic(view);
                }
            }
        });
    }
    
    private void passLateData() {
        
        
        /*
        ImageView sprite = new ImageView(new Image("fxmonopoly/resources/images/sprites/" + pieceSelection.getSelectionModel().toString()));
        Color color = Color.valueOf(colourSelection.getSelectionModel().getSelectedItem());
        String name = nameField.getText();
        */
        
        ArrayList<ObservableList<String>> array = new ArrayList<>();
        array.add(pieceData);
        array.add(colourData);
        
        ImageView view = new ImageView(new Image("fxmonopoly/resources/image/sprites/" + pieceSelection.getSelectionModel().getSelectedItem() + ".png"));
        Color colour = Color.valueOf(colourSelection.getSelectionModel().getSelectedItem());
        
        array.get(0).remove(pieceSelection.getSelectionModel().getSelectedItem());
        array.get(1).remove(colourSelection.getSelectionModel().getSelectedItem());
        
        manager.getLateData().lateDataPass(view,
                                           colour, 
                                           nameField.getText(),
                                           array);
    }
    
}
