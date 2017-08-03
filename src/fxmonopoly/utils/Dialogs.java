/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This enum contains all of the Non-dynamic Dialogs utilised in this application, non-dynamic
 * in this case referring to a single definitive context of use, where interchangeability
 * of content is unnecessary.
 * <p>
 * The purpose of separating the Dialogs to this enum is to reduce the complexity and
 * instantiation times of the Controller classes, by removing components that are not
 * directly displayed in the main Scene, whilst also ensuring that the StageManager
 * is the only class to be able to pass a Stage instance.
 * @author Sam P. Morrissey
 */
public enum Dialogs {
    
    ABOUT {
        @Override
        void display(Stage stage) {
            // Creates the dialog and removes any decoration
            Alert aboutAlert = new Alert(Alert.AlertType.NONE);
            aboutAlert.initStyle(StageStyle.TRANSPARENT);
        
            // Synchronises the dialog with the styling css file
            aboutAlert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("fxmonopoly/resources/DialogsStyle.css").toExternalForm());
            aboutAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
            // Allows the Dialogs to track the position of the Main Menu
            aboutAlert.initModality(Modality.APPLICATION_MODAL);
            aboutAlert.initOwner(stage);
        
            // Sets the content of the Dialogs and displays it
            aboutAlert.setContentText("Author: Sam P. Morrissey \n"
                                 +"Created as an MSc Computer Science Project \n"
                                 +"\n"
                                 +"To register a complaint you have with this program, please write " 
                                 +"it on the highest currency demonimation you have and file it in the "
                                 +"nearest available shredder. \n"
                                 +"\n"
                                 +"All rights to Monopoly® are property of Hasbro inc. \n"
                                 +"This work is produced under the non-commercial research and private study "
                                 +"UK copyright guidelines.");
            aboutAlert.getButtonTypes().add(ButtonType.OK);
            aboutAlert.showAndWait();
        }
    },
    GAME_INIT_BAD_SEL {
        @Override
        void display(Stage stage) {
            // Creates the dialog and removes any decoration
            Alert selectionAlert = new Alert(Alert.AlertType.NONE);
            selectionAlert.initStyle(StageStyle.TRANSPARENT);
        
            // Synchronises the dialog with the styling css file
            selectionAlert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("fxmonopoly/resources/DialogsStyle.css").toExternalForm());
            selectionAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
            // Allows the Dialogs to track the position of the Main Menu
            selectionAlert.initModality(Modality.APPLICATION_MODAL);
            selectionAlert.initOwner(stage);
        
            // Sets the content of the Dialogs and displays it
            selectionAlert.setContentText("Oops. \n"
                                 +"\n"
                                 +"Player name must contain at least a single character.\n"
                                 +"You must also select your colour and piece before continuing.");
            selectionAlert.getButtonTypes().add(ButtonType.OK);
            selectionAlert.showAndWait();
        }
    },
    RROLLED {
        @Override
        void display(Stage stage) {
            Alert rrolledAlert = new Alert(Alert.AlertType.NONE);
            rrolledAlert.initStyle(StageStyle.TRANSPARENT);
        
            // Synchronises the dialog with the styling css file
            rrolledAlert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("fxmonopoly/resources/DialogsStyle.css").toExternalForm());
            rrolledAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
            // Generates the necessary Media files
            Media rrolled = new Media(getClass().getClassLoader().getResource("fxmonopoly/resources/easter/Rolled.mp4").toString());
            MediaPlayer player = new MediaPlayer(rrolled);
            MediaView viewer = new MediaView(player);
        
            // Other set-up elements
            player.setAutoPlay(true);
            viewer.setFitHeight(240.0);
            viewer.setFitWidth(320.0);
            rrolledAlert.getDialogPane().setMaxWidth(340.0);
            rrolledAlert.getDialogPane().setContent(viewer);
        
            // Allows the Dialogs to track the position of the Main Menu
            rrolledAlert.initModality(Modality.APPLICATION_MODAL);
            rrolledAlert.initOwner(stage);
    
        
            //rrolledAlert.getButtonTypes().add(ButtonType.OK);
            //final Button btOk = (Button) rrolledAlert.getDialogPane().lookupButton(ButtonType.OK);
            ButtonType topKekM8 = new ButtonType(">:(", ButtonBar.ButtonData.OK_DONE);
            rrolledAlert.getButtonTypes().add(topKekM8);
            rrolledAlert.showAndWait();
            player.stop();
        }
    };
    
    /**
     * Displays the specified Dialog window.
     * @param stage The stage utilised to set ownership of the Dialog.
     */
    abstract void display(Stage stage);
}
