/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.game.utils.controller.BoardButton;
import fxmonopoly.game.utils.controller.BoardPopulating;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.utils.GameDialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.interfacing.LateData;
import fxmonopoly.utils.interfacing.Manageable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

/**
 * Contains the necessary state and interaction definitions for the Game to run,
 * when combined with the GameView (Layout and Style files) and the GameModel.
 *
 * @author Sam P. Morrissey
 */
public class GameController implements Initializable, Manageable, LateData {
    private StageManager manager;
    
    private GameModel model;
    
    private HashMap<Player, ImageView> sprites;
    private HashMap<Player, Color> colours;
    
    private ArrayList<BoardButton> board;
    
    @FXML
    private TextFlow printOut;
    
    @FXML
    private GridPane boardPane;
    
    @FXML
    private AnchorPane boardAnchor;

    @FXML
    private Button exitButton;
    
    @FXML
    private Button iconifiedButton;
    
    @FXML
    private Button rollDiceButton;
    
    @FXML
    private Button tradeButton;
    
    @FXML
    private Button statsButton;
    
    @FXML
    private Button jailEscapeButton;
    
    @FXML
    private ImageView userSprite;
    
    @FXML
    private Label userCash;
    
    @FXML
    private ImageView activePlayerSprite;
    
    @FXML
    private Label activePlayerName;
    
    @FXML
    private Label activePlayerCash;
    
    @FXML
    private Label activeUserLocationName;
    
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sprites = new HashMap<>();
        colours = new HashMap<>();
        board = new ArrayList<>();
        model = new GameModel();
        
        
        BoardPopulating.generateButtons(board, boardPane, colours, model);
        
        iconifiedButton.setOnAction(e -> manager.setIconified());
        
        exitButton.setOnAction(e ->  manager.getGameDialog(GameDialogs.EXIT).showAndWait() );
    }    
    
    /**
     * Sets the StageManager utilised within this controller.
     * @param manager The StageManager to be utilised within this controller.
     */
    @Override
    public void setStageManager(StageManager manager) {
        this.manager = manager;
    }
    
    /**
     * Defines the action performed on a LateData pass, necessary for the Game
     * Init Settings Controller to pass its information to this controller, without
     * unnecessarily exposing the implementation.
     * @param image The ImageView to be associated with the user instance.
     * @param colour The colour to be associated with the user instance.
     * @param name The name to be associated with the player instance.
     */
    @Override
    public void lateDataPass(ImageView sprite, Color colour, String name, ArrayList<ObservableList<String>> array) {
        model.createAndAddUser(name);
        model.createAndAddCPU();
        sprites.put(model.getUser(), sprite);
        colours.put(model.getUser(), colour);
        
        
        
        for(Player player : model.getInitialList()) {
            if(player != model.getUser()) {
                sprites.put(player, new ImageView(new Image("fxmonopoly/resources/images/sprites/" + array.get(0).get(0) + ".png")));
                colours.put(player, Color.valueOf(array.get(1).get(0)));
                
                for(ObservableList observe : array) {
                    observe.remove(0);
                }
            }
        }
        
    }
    
}
