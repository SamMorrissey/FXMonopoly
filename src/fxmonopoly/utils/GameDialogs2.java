package fxmonopoly.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public enum GameDialogs2 {

    EXIT {
        @Override
        Dialog getDialog(Stage stage) {
            return
                GameDialogs2
                    .basicConstruction(stage)
                    .setContentText("Are you sure you wish to exit FXMonopoly?")
                    .applyButton(ButtonType.OK)
                    .applyCustomManipulation(alert ->
                        alert.getDialogPane()
                             .lookupButton(ButtonType.OK)
                             .addEventHandler(ActionEvent.ACTION, e -> stage.close()))
                    .applyButton(ButtonType.CANCEL)
                    .eject();
        }
    },
    BLANK {
        @Override
        Dialog getDialog(Stage stage) {
            return GameDialogs2.basicConstruction(stage).eject();
        }
    };


    abstract Dialog getDialog(Stage stage);

    private static DialogProcessBuilder basicConstruction(Stage stage) {
        return
            new DialogProcessBuilder("fxmonopoly/resources/GameDialogsStyle.css")
                .applyCustomManipulation(alert -> alert.initOwner(stage));
    }
}
