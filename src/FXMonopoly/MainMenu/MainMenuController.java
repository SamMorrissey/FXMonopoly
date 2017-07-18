/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMonopoly.MainMenu;

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

/**
 *
 * @author Slipshod
 */
public class MainMenuController implements Initializable {
    private Stage stage;
    
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
                                 +"To register a complaint with this product, please write it on the " 
                                 +"highest currency demonimation you have and file it in the "
                                 +"nearest available shredder.");
        aboutAlert.getButtonTypes().add(ButtonType.OK);
        aboutAlert.showAndWait();
    }
    
    /*
     * Sets the stage variable of this class and activates the movable
     * Window trait
     * @param stage The stage currently in use
     */
    public void setStageAndMovable(Stage stage) {
        this.stage = stage;
        enablePositionChange();
    }
    /*
     * Sets mouse events on the stage to allow the user to drag the window
     * since decoration is non-existent
     * @param primaryStage The parent stage of the scene 
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
