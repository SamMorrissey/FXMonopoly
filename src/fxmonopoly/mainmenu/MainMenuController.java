/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.mainmenu;

import fxmonopoly.utils.Dialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;
import fxmonopoly.utils.interfacing.Manageable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * The controller class for the MainMenu window. Implements the Manageable interface
 * to ensure sufficient instantiation, as all functions in this class operate through
 * calls to its StageManager variable.
 * @author Sam P. Morrissey
 */
public class MainMenuController implements Initializable, Manageable {
    private StageManager manager;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Button aboutButton;
    
    @FXML
    private Button singlePlayerButton;
    
    /**
     * Initialises the Controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singlePlayerButton.setOnAction(e -> manager.changeScene(View.GAME_INIT));
        
        aboutButton.setOnAction(e -> manager.getDialog(Dialogs.ABOUT));
        
        exitButton.setOnAction(e -> manager.exitProgram());
    }    
    
    /**
     * Sets the StageManager utilised by this controller.
     * @param manager The StageManager for this controller.
     */
    @Override
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
}
