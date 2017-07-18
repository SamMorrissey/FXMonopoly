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

/**
 *
 * @author Slipshod
 */
public class MainMenuController implements Initializable {
    private Stage stage;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Button aboutButton;
    
    @FXML
    private Button singlePlayerButton;
    
    @FXML
    private ImageView banner;
    
    private double xOffset;
    private double yOffset;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        exitButton.setOnAction(e -> {
            stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
                });
        
        aboutButton.setOnAction(e -> aboutDialog());    
    }    
    
    public void aboutDialog() {
        Alert aboutAlert = new Alert(Alert.AlertType.NONE);
        aboutAlert.initStyle(StageStyle.TRANSPARENT);
        aboutAlert.getDialogPane().getStylesheets().add(getClass().getResource("MainMenuStyle.css").toExternalForm());
        aboutAlert.getDialogPane().getStyleClass().add("dialog-pane");
        aboutAlert.setContentText("Author: Sam P. Morrissey \n"
                                 +"Created as an MSc Computer Science Project \n"
                                 +"\n"
                                 +"To register a complaint with this product, please write it on the " 
                                 +"highest currency demonimation you have and file it in the "
                                 +"nearest available shredder.");
        
        aboutAlert.getButtonTypes().add(ButtonType.OK);
        aboutAlert.showAndWait();
    }
    
    public void setStageAndMovable(Stage stage) {
        this.stage = stage;
        enablePositionChange(stage);
    }
    public void enablePositionChange(Stage primaryStage) {
        Scene scene = exitButton.getScene();
        scene.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }
}
