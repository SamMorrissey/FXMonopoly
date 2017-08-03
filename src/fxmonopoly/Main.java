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
 * The base class of the application to specify the initial launch conditions.
 * @author Sam P. Morrissey
 */
public class Main extends Application {
    
    /**
     * The default JavaFX method for starting the application, as opposed to the
     * main method.
     * @param stage The Stage on which the application is displayed.
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        StageManager manager = new StageManager(stage);
    }

    /**
     * The secondary method for initialising the program since JavaFX utilises
     * its own launcher as opposed to a main method.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
