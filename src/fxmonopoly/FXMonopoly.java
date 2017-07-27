/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly;

import javafx.application.Application;
import javafx.stage.Stage;
import fxmonopoly.utils.StageManager;

/**
 *
 * @author Sam P. Morrissey
 */
public class FXMonopoly extends Application {
  
    @Override
    public void start(Stage stage) throws Exception {
        StageManager manager = new StageManager(stage);
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
