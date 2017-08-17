/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sam P. Morrissey
 */
public enum GameDialogs {
    
    EXIT {
        @Override
        Alert getDialog(Stage stage) {
            Alert exitAlert = new Alert(Alert.AlertType.NONE);
            exitAlert.initStyle(StageStyle.TRANSPARENT);
        
            exitAlert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("fxmonopoly/resources/GameDialogsStyle.css").toExternalForm());
            exitAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
            exitAlert.initModality(Modality.APPLICATION_MODAL);
            exitAlert.initOwner(stage);
        
            // Sets the content of the Dialogs and displays it
            exitAlert.setContentText("Are you sure you wish to exit FXMonopoly?");
            
            exitAlert.getButtonTypes().add(ButtonType.OK);
            exitAlert.getDialogPane().lookupButton(ButtonType.OK).addEventHandler(ActionEvent.ACTION, e -> stage.close());
            
            exitAlert.getButtonTypes().add(ButtonType.CANCEL);

            return exitAlert;
        }
    };
    
    abstract Alert getDialog(Stage stage);
}
