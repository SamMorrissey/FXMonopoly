/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gameinitsettings;

import fxmonopoly.utils.Dialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class for the GameInitSettings window
 *
 * @author Sam Morrissey
 */
public class GameInitSettingsController implements Initializable {
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
     * Initializes the controller class.
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
            else {
                
            }
            
        }); 
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
            private Rectangle rect = new Rectangle(115.0, 15.0);
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if(empty) {
                    setGraphic(null);
                }
                else {
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
                        case "GREEN":
                            rect.setFill(Color.GREEN);
                            break;
                        case "LAWNGREEN":
                            rect.setFill(Color.LAWNGREEN);
                            break;
                        case "PURPLE":
                            rect.setFill(Color.PURPLE);
                            break;
                        case "SALMON":
                            rect.setFill(Color.SALMON);
                            break;
                        case "RED":
                            rect.setFill(Color.RED);
                            break;
                        case "MAROON":
                            rect.setFill(Color.MAROON);
                            break;
                        case "BURLYWOOD":
                            rect.setFill(Color.BURLYWOOD);
                            break;
                        case "ORANGE":
                            rect.setFill(Color.ORANGE);
                            break;
                        case "YELLOW":
                            rect.setFill(Color.YELLOW);
                            break;
                        default:
                            break;
                    }
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
        pieceData = FXCollections.observableArrayList("BOOT", "CAR", "DOG", "HAT",
                "IRON", "SHIP", "THIMBLE", "BARROW");
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
                    switch (name) {
                        case "BOOT":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Boot.png"));
                            break;
                        case "CAR":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Car.png"));
                            break;
                        case "DOG":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Dog.png"));
                            break;
                        case "HAT":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Hat.png"));
                            break;
                        case "IRON":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Iron.png"));
                            break;
                        case "SHIP":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Ship.png"));
                            break;
                        case "THIMBLE":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Thimble.png"));
                            break;
                        case "BARROW":
                            view.setImage(new Image("fxmonopoly/resources/images/sprites/Wheelbarrow.png"));
                            break;
                        default:
                            break;
                    }
                setGraphic(view);
                }
            }
        });
    }
    
    /**
     * Passes the StageManager for switching to necessary windows.
     * @param manager The StageManager to be utilised
     */
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
}
