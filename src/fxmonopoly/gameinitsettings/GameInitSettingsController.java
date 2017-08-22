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
            else if(nameField.getText().startsWith("cpu") || nameField.getText().startsWith("CPU")) {
                manager.getDialog(Dialogs.GAME_INIT_CPU_STRING);
            }
            else if(nameField.getText().contains("Rick") || nameField.getText().contains("rick") || nameField.getText().contains("Astley") || nameField.getText().contains("astley")) {
                manager.getDialog(Dialogs.RROLLED);
                
                manager.changeScene(View.GAME);
                ArrayList<ObservableList<String>> array = new ArrayList<>();
                array.add(pieceData);
                array.add(colourData);
        
                manager.getLateData().lateDataPass("Rolled",
                                                   colourSelection.getSelectionModel().getSelectedItem(), 
                                                   nameField.getText(),
                                                   array);
            }
            else {
                manager.changeScene(View.GAME);
                passLateData();
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
                                                      "Ship", "Thimble", "Wheelbarrow");
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
                    view.setImage(new Image("fxmonopoly/resources/images/sprites/" + name + ".png"));
                    setGraphic(view);
                }
            }
        });
    }
    
    /**
     * Passes the late data on to the late data object.
     */
    private void passLateData() {
        ArrayList<ObservableList<String>> array = new ArrayList<>();
        array.add(pieceData);
        array.add(colourData);
        
        manager.getLateData().lateDataPass(pieceSelection.getSelectionModel().getSelectedItem(),
                                           colourSelection.getSelectionModel().getSelectedItem(), 
                                           nameField.getText(),
                                           array);
    }
    
}
