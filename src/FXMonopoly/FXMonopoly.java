/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMonopoly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import FXMonopoly.MainMenu.MainMenuController;

/**
 *
 * @author Slipshod
 */
public class FXMonopoly extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu/MainMenuDocument.fxml"));
        Parent root = (Parent) loader.load();
        MainMenuController controller = (MainMenuController) loader.getController();
        
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainMenu/MainMenuStyle.css").toExternalForm());
        
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        controller.setStageAndMovable(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
