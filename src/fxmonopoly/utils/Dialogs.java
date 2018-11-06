package fxmonopoly.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.function.Consumer;

public enum Dialogs {

    ABOUT ( stage ->
        basicActivation(new DialogProcessBuilder(), stage)
            .accept("Author: Sam P. Morrissey \n"
                +"Created as an MSc Computer Science Project \n"
                +"\n"
                +"To register a complaint you have with this program, please write "
                +"it on the highest currency demonimation you have and file it in the "
                +"nearest available shredder. \n"
                +"\n"
                +"All rights to Monopoly® are property of Hasbro inc. \n"
                +"This work is produced under the non-commercial research and private study "
                +"UK copyright guidelines.")
    ),
    GAME_INIT_BAD_SEL ( stage ->
        basicActivation(new DialogProcessBuilder(), stage)
            .accept(
                "Oops. \n"
                +"\n"
                +"Player name must contain at least a single character.\n"
                +"You must also select your colour and piece before continuing.")
    ),
    GAME_INIT_CPU_STRING ( stage ->
        basicActivation(new DialogProcessBuilder(), stage)
            .accept("Your name is an expression of you, try and be creative.")
    ),
    RROLLED ( stage -> {
        DialogProcessBuilder builder = new DialogProcessBuilder();
        builder
            .applyCustomManipulation(alert -> initOwnerOfAlert(alert, stage))
            .setUpRRollContent()
            .activateDialogAndStopMediaPlayerOnFinish();
    });

    /**
     * Displays the specified Dialog window.
     * @param stage The stage utilised to set ownership of the Dialog.
     */
    private Consumer<Stage> display;

    Dialogs(Consumer<Stage> display) { this.display = display; }

    public void display(Stage stage) { display.accept(stage); }

    private static void initOwnerOfAlert(Alert alert, Stage stage) { alert.initOwner(stage); }

    private static Consumer<String> basicActivation(DialogProcessBuilder builder, Stage stage) {
        return contentText ->
            builder
                .setContentText(contentText)
                .applyCustomManipulation(alert -> initOwnerOfAlert(alert, stage))
                .applyButton(ButtonType.OK)
                .activateDialog();
    }
}

