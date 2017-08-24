/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;

import fxmonopoly.game.GameController;
import fxmonopoly.utils.interfacing.LateData;
import fxmonopoly.utils.interfacing.Manageable;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Initialises the singleton Stage of the entire application, whilst dealing with
 * any transitions between different Scenes in the application, including a transitional
 * animation. Requires a View enum value to specify the scene to transition to.
 * <p>
 * Also provides a way of displaying non-Dynamic Dialogs that are utilised 
 * throughout the program from one location, as well as provide a way to get the base
 * of dynamic dialogs which can have their content modified. Primarily done to avoid the 
 * Stage being utilised in external classes, since via the aforementioned method, this
 * class will pass the Stage variable needed for dialog ownership.
 * @author Sam P. Morrissey
 */
public class StageManager {
    // The single constant stage to be utilised by the manager
    private final Stage stage;
    
    private FXMLLoader loader;
    
    private double xOffset;
    private double yOffset;
    
    // For resuming a game in progress, only set upon the start of a game, returned
    // to null upon a game being completed.
    private Scene gameScene;
    
    /**
     * Creates the StageManager utilised throughout the application.
     * @param stage The stage on which the StageManager acts.
     */
    public StageManager(Stage stage) {
        this.stage = stage;
        initialDisplay();
    }
    
    /*
    public Stage getStage() {
        return stage;
    }
    */
    
    /**
     * Retrieves the specified dialog attached to the input enum value.
     * @param dialog The specified dialog
     */
    public void getDialog(Dialogs dialog) {
        dialog.display(stage);
    }
    
    /**
     * Retrieves the specified game dialog attached to the input enum value.
     * @param dialog The dialog to pass.
     * @return The dialog specified.
     */
    public Dialog getGameDialog(GameDialogs dialog) {
        return dialog.getDialog(stage);
    }
    
    /**
     * Retrieves a LateData instance if one is available, otherwise returns null.
     * @return The LateData instance if available, otherwise null.
     */
    public LateData getLateData() {
        if(loader != null && loader.getController() instanceof GameController) {
            LateData data = (LateData) loader.getController();
            return data;
        }
        
        return null;
    }
    
    /**
     * Causes the stage to close, thereby exiting the program. Exactly equivalent
     * to a stage.close() call.
     */
    public void exitProgram() {
        stage.close();
    }
    
    /**
     * Iconifies the program, that is to say hides it whilst leaving it available
     * to reopen in the system tray.
     */
    public void setIconified() {
        stage.setIconified(true);
    }
    
    /**
     * Gets the content necessary to display the initial scene of the program,
     * and initialises all necessary variables. Also does some initial stage styling,
     * such as setting the initStyle, Icon and Title. 
     * <p>
     * It then calls enablePositionChange to activate click and drag window movement.
     * Finally sets this StageManager instance as the StageManager for the initialised
     * scene.
     */
    private void initialDisplay() {
        //Create and "initialise" used elements to null, for later assignment to actual values
        Parent root;
        loader = null;
        Scene scene = null;
        
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("fxmonopoly/mainmenu/MainMenuLayout.fxml"));
            root = loader.load();
            scene = new Scene(root);
        }
        catch (IOException e) {
            errorDialog("There was an I/O problem.", e);
        }
        
        // Sets the initial stage parameters
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("fxmonopoly/resources/images/Icon.png"));
        stage.setTitle("FXMonopoly");
        stage.setScene(scene);
        stage.show();
        
        // Enables stage click and drag movement
        enablePositionChange();
        // Passes this StageManager instance to the Controller of the initialised Parent
        passStageManager();
    }
    
    /**
     * Loads and displays the node hierarchy as generated by the specific .fxml
     * file attached to the View enum utilised.
     * @param view The window to be switched to.
     */
    public void changeScene(final View view) {
        try {
            Parent viewHierarchy = loadHierarchy(view.getFXMLPath());
            runTransition(viewHierarchy);
            passStageManager();
        }
        catch (Exception e) {
            errorDialog("There was a problem", e);
        }
    }
    
     /**
      * Resets the parent file in order to obtain the correct specifications, since
      * no setter method is available for the Parent class.
      * @param fxmlFilePath The local address for the necessary fxml file.
      * @return             The Parent node generated from the fxml file.
      */
    private Parent loadHierarchy(String fxmlFilePath) {
        Parent root = null;
        loader = null;
        
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(fxmlFilePath));
            root = loader.load();
        }
        catch (IOException e) {
            errorDialog("There was an I/O problem.", e);
        }
        
        return root;
    }
    
    /**
     * The method that passes this instance of the StageManager to the initialised
     * controller of the new scene. Utilises the setStageManager methods present in
     * this application's custom controllers.
     */
    private void passStageManager() {
        /*
        if(loader.getController() instanceof MainMenuController) {
            MainMenuController control = loader.getController();
            control.setStageManager(this);
        }
        else if(loader.getController() instanceof GameInitSettingsController) {
            GameInitSettingsController control = loader.getController();
            control.setStageManager(this);
        }
        else if(loader.getController() instanceof GameController) {
            GameController control = loader.getController();
            control.setStageManager(this);
        }
        else if(loader.getController() instanceof TradeController) {
            TradeController control = loader.getController();
            control.setStageManager(this);
        }
        */ 
        
        if(loader.getController() instanceof Manageable) {
            
            Manageable manage = loader.getController();
            manage.setStageManager(this);
        }
    }
    
    /**
     * Calls the stage transition to generate a smooth transition to the specified
     * root node size.
     * @param root The root node generated by the FXMLLoader, to be transitioned to.
     */
    private void runTransition(final Parent root) {
        StageTransition.runTransition(stage, root);
    }
    
    /**
     * Creates a dialog displaying the exception information.
     * @param message The message to be displayed
     * @param exception The exception causing the dialog
     */
    private void errorDialog(String message, Exception exception) {
        Alert aboutAlert = new Alert(Alert.AlertType.NONE);
        aboutAlert.initStyle(StageStyle.TRANSPARENT);
        
        aboutAlert.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("fxmonopoly/resources/DialogsStyle.css").toExternalForm());
        aboutAlert.getDialogPane().getStyleClass().add("dialog-pane");
        
        aboutAlert.initModality(Modality.APPLICATION_MODAL);
        aboutAlert.initOwner(stage);
        
        aboutAlert.setContentText(message + "\n"
                                 +exception.getCause()
                                 );
        aboutAlert.getButtonTypes().add(ButtonType.OK);
        aboutAlert.showAndWait();
        stage.close();
    }
    
    /**
     * Enables the window to be repositioned via clicking and dragging on 
     * the root node and other non-focusable nodes such as ImageViews. Required
     * since the default style of the Stage is set to transparent and cannot be
     * repositioned otherwise. 
     * <p>
     * Only requires a single call, as long as scene still refers to the same
     * initial scene generated (since currently the Root of the Scene is altered
     * instead of switching the Scene itself). Otherwise this must be called again.
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
