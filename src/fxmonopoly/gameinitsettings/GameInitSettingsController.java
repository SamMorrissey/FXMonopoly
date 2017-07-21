/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gameinitsettings;

import fxmonopoly.utils.StageManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Slipshod
 */
public class GameInitSettingsController implements Initializable {
    private StageManager manager;
    
    private double xOffset;
    private double yOffset;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private ListView<String> colourSelection;
    
    @FXML
    private ListView<String> pieceSelection;
    
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
        
        okButton.setOnAction(e -> {
            if(nameField.getText().isEmpty() || colourSelection.getSelectionModel().getSelectedItem() == null ||
                    pieceSelection.getSelectionModel().getSelectedItem() == null) {
                incorrectSelectionDialog();
            }
        });
        
    }    
    
    public void populateColours() {
        colourData = FXCollections.observableArrayList("BLUE", "CYAN","AQUAMARINE", "LAWNGREEN", "GREEN", 
                "PURPLE", "SALMON", "RED", "MAROON", "BURLYWOOD", "ORANGE", "YELLOW");
        colourSelection.setItems(colourData);
        
        colourSelection.setCellFactory(e -> new ListCell<String>() {
            private Rectangle rect = new Rectangle(120.0, 15.0);
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if(empty) {
                    setGraphic(null);
                }
                else {
                    if(name.equals("BLUE"))
                        rect.setFill(Color.BLUE);
                    else if(name.equals("AQUAMARINE"))
                        rect.setFill(Color.AQUAMARINE);
                    else if(name.equals("CYAN"))
                        rect.setFill(Color.CYAN);
                    else if(name.equals("GREEN"))
                        rect.setFill(Color.GREEN);
                    else if(name.equals("LAWNGREEN"))
                        rect.setFill(Color.LAWNGREEN);
                    else if(name.equals("PURPLE"))
                        rect.setFill(Color.PURPLE);
                    else if(name.equals("SALMON"))
                        rect.setFill(Color.SALMON);
                    else if(name.equals("RED"))
                        rect.setFill(Color.RED);
                    else if(name.equals("MAROON"))
                        rect.setFill(Color.MAROON);
                    else if(name.equals("BURLYWOOD"))
                        rect.setFill(Color.BURLYWOOD);
                    else if(name.equals("ORANGE"))
                        rect.setFill(Color.ORANGE);
                    else if(name.equals("YELLOW"))
                        rect.setFill(Color.YELLOW);
                setGraphic(rect);
                }
            }
        });
    }
    
    public void populateSprites() {
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
                    if(name.equals("BOOT"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Boot.png"));
                    else if(name.equals("CAR"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Car.png"));
                    else if(name.equals("DOG"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Dog.png"));
                    else if(name.equals("HAT"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Hat.png"));
                    else if(name.equals("IRON"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Iron.png"));
                    else if(name.equals("SHIP"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Ship.png"));
                    else if(name.equals("THIMBLE"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Thimble.png"));
                    else if(name.equals("BARROW"))
                        view.setImage(new Image("fxmonopoly/resources/images/sprites/Wheelbarrow.png"));
                    setGraphic(view);
                }
            }
        });
    }
    
    /*
     * Creates the StageManager for switching to necessary windows
     * @param manager The StageManager to be utilised
    */
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
    
    private void incorrectSelectionDialog() {
        // Creates the dialog and removes any decoration
        Alert selectionAlert = new Alert(Alert.AlertType.NONE);
        selectionAlert.initStyle(StageStyle.TRANSPARENT);
        
        // Synchronises the dialog with the styling css file
        selectionAlert.getDialogPane().getStylesheets().add(getClass().getResource("GameInitSettingsStyle.css").toExternalForm());
        selectionAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Allows the Dialog to track the position of the Main Menu
        selectionAlert.initModality(Modality.APPLICATION_MODAL);
        System.out.println(manager);
        selectionAlert.initOwner(manager.getStage());
        
        // Sets the content of the Dialog and displays it
        selectionAlert.setContentText("Oops. \n"
                                 +"\n"
                                 +"Player name must contain at least a single character.\n"
                                 +"You must also select your colour and piece before continuing.");
        selectionAlert.getButtonTypes().add(ButtonType.OK);
        selectionAlert.showAndWait();
    }
}
