/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.function.Consumer;

/**
 *
 * @author Slipshod
 */
public class DialogProcessBuilder {

    private Alert dialog;

    public DialogProcessBuilder() {
        defaultInit();
        stylingInit("fxmonopoly/resources/DialogsStyle.css");
    }

    public DialogProcessBuilder(String cssPath) {
        defaultInit();
        stylingInit(cssPath);
    }

    private void defaultInit() {
        dialog = new Alert(Alert.AlertType.NONE);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.initModality(Modality.APPLICATION_MODAL);
    }

    private void stylingInit(String cssPath) {
        dialog.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource(cssPath).toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialog-pane");
    }

    public Dialog eject() { return dialog; }

    DialogProcessBuilder setContentText(String text) { dialog.setContentText(text); return this; }

    DialogProcessBuilder applyButton(ButtonType type) { dialog.getButtonTypes().add(type); return this; }

    DialogProcessBuilder applyCustomManipulation(Consumer<Alert> manipulation) { manipulation.accept(dialog); return this; }

    void activateDialog() { dialog.showAndWait(); }

    void activateDialogWithOnFinish(Consumer<Alert> onFinish) {
        activateDialog();
        onFinish.accept(dialog);
    }

    void activateDialogAndStopMediaPlayerOnFinish(){
        activateDialog();
        ((MediaView) dialog.getDialogPane().getContent()).getMediaPlayer().stop();
    }

    DialogProcessBuilder setUpRRollContent() {
        Media rrolled = new Media(getClass().getClassLoader().getResource("fxmonopoly/resources/easter/Rolled.mp4").toString());
        MediaPlayer player = new MediaPlayer(rrolled);
        MediaView viewer = new MediaView(player);

        player.setAutoPlay(true);
        viewer.setFitHeight(240.0);
        viewer.setFitWidth(320.0);
        dialog.getDialogPane().setMaxWidth(340.0);
        dialog.getDialogPane().setContent(viewer);

        ButtonType topKekM8 = new ButtonType(">:(", ButtonBar.ButtonData.OK_DONE);
        dialog.getButtonTypes().add(topKekM8);

        return this;
    }
}
