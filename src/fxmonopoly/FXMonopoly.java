/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import fxmonopoly.mainmenu.MainMenuController;
import fxmonopoly.utils.StageManager;

/**
 *
 * @author Slipshod
 */
public class FXMonopoly extends Application {
    private StageManager manager;
    
    @Override
    public void start(Stage stage) throws Exception {
        // Creates the FXMLLoader to be utilised throughout the application as well
        // as the root hierarchy for the MainMenu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu/MainMenuLayout.fxml"));
        Parent root = (Parent) loader.load();
        
        // Sets the root hierarchy and styling for the initial MainMenu scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("mainmenu/MainMenuStyle.css").toExternalForm());
        
        // Other necessary setup items to get the window displaying correctly
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        
        // Creates the StageManager by passing the Stage and FXML laoder of the 
        // application. Also does the initial linking of the MainMenu instance to
        // the Stage Manager. JavaFX stage not available to transfer until this point.
        manager = new StageManager(stage, loader);
        MainMenuController controller = (MainMenuController) loader.getController();
        controller.setStageManager(manager);
    }

    /**
     * The secondary method for initialising the program since JavaFX utilises
     * its own launcher as opposed to a main method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
