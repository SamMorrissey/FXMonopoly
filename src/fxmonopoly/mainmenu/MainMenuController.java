/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;

/**
 *
 * @author Slipshod
 */
public class MainMenuController implements Initializable {
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
        
        exitButton.setOnAction(e -> manager.getStage().close());
        
        aboutButton.setOnAction(e -> aboutDialog());
        
        singlePlayerButton.setOnAction(e -> manager.changeScene(View.GAME_INIT));
    }    
    
    /*
     * Passes the StageManager for switching to necessary windows
     * @param manager The StageManager to be utilised
    */
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
    
    /*
     * Generates the Dialog on activation of the About button
     */
    private void aboutDialog() {
        // Creates the dialog and removes any decoration
        Alert aboutAlert = new Alert(Alert.AlertType.NONE);
        aboutAlert.initStyle(StageStyle.TRANSPARENT);
        
        // Synchronises the dialog with the styling css file
        aboutAlert.getDialogPane().getStylesheets().add(getClass().getResource("MainMenuStyle.css").toExternalForm());
        aboutAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
        // Allows the Dialog to track the position of the Main Menu
        aboutAlert.initModality(Modality.APPLICATION_MODAL);
        aboutAlert.initOwner(manager.getStage());
        
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
}
