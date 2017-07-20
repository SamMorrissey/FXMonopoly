/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import fxmonopoly.utils.StageManager;

/**
 *
 * @author Slipshod
 */
public class MainMenuController implements Initializable {
    private Stage stage;
    private StageManager manager;
    
    private double xOffset;
    private double yOffset;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Button aboutButton;
    
    @FXML
    private Button singlePlayerButton;
    
    @FXML
    private ImageView banner;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        exitButton.setOnAction(e -> stage.close());
        
        aboutButton.setOnAction(e -> aboutDialog());    
    }    
    
    /*
     * Generates the Dialog on activation of the About button
     */
    public void aboutDialog() {
        // Creates the dialog and removes any decoration
        Alert aboutAlert = new Alert(Alert.AlertType.NONE);
        aboutAlert.initStyle(StageStyle.TRANSPARENT);
        
        // Synchronises the dialog with the styling css file
        aboutAlert.getDialogPane().getStylesheets().add(getClass().getResource("MainMenuStyle.css").toExternalForm());
        aboutAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Allows the Dialog to track the position of the Main Menu
        aboutAlert.initModality(Modality.APPLICATION_MODAL);
        aboutAlert.initOwner(stage);
        
        // Sets the content of the Dialog and displays it
        aboutAlert.setContentText("Author: Sam P. Morrissey \n"
                                 +"Created as an MSc Computer Science Project \n"
                                 +"\n"
                                 +"To register a complaint you have with this product, please write " 
                                 +"it on the highest currency demonimation you have and file it in the "
                                 +"nearest available shredder.");
        aboutAlert.getButtonTypes().add(ButtonType.OK);
        aboutAlert.showAndWait();
    }
    
    /*
     * Sets the Stage and StageManager variables, whilst also activating the 
     * movable window trait, only possible once the scene is fully initialised.
     * @param stage The Stage of the program
     * @param manager The StageManager to be utilised
     */
    public void setStageFull(Stage stage, StageManager manager) {
        setStage(stage);
        setStageManager(manager);
        enablePositionChange();
    }
    
    /*
     * Allows the MainMenuController to manipulate the stage itself as necessary
     * by passing the only instance to the class.
     * @param stage The Stage of the program
    */
    private void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /*
     * Creates the StageManager for switching to necessary windows
     * @param manager The StageManager to be utilised
    */
    private void setStageManager(StageManager manager) {
        this.manager = manager;
    }
    
    /*
     * Sets mouse events on the stage to allow the user to drag the window
     * since decoration is non-existent
     */
    private void enablePositionChange() {
        Scene scene = stage.getScene();
        
        scene.setOnMousePressed(e -> {
            xOffset = stage.getX() - e.getScreenX();
            yOffset = stage.getY() - e.getScreenY();
        });
        
        scene.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + xOffset);
            stage.setY(e.getScreenY() + yOffset);
        });
    }
}
