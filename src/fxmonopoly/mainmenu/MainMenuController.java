/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.mainmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;
import fxmonopoly.utils.Dialogs;

/**
 *
 * @author Slipshod
 */
public class MainMenuController implements Initializable {
    private StageManager manager;
    
    private double xOffset;
    private double yOffset;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Button aboutButton;
    
    @FXML
    private Button singlePlayerButton;
    
    @FXML
    private ImageView banner;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        singlePlayerButton.setOnAction(e -> manager.changeScene(View.GAME_INIT));
        
        aboutButton.setOnAction(e -> manager.getDialog(Dialogs.ABOUT));
        
        exitButton.setOnAction(e -> manager.getStage().close());
    }    
    
    /*
     * Passes the StageManager for switching between windows
     * @param manager The StageManager to be utilised
    */
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
    
}
