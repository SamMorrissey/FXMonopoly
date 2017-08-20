/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.game.utils.controller.BoardButton;
import fxmonopoly.game.utils.controller.BoardPopulating;
import fxmonopoly.game.utils.controller.DialogContent;
import fxmonopoly.game.utils.controller.SpriteManipulation;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.utils.GameDialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.interfacing.LateData;
import fxmonopoly.utils.interfacing.Manageable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.util.Duration;

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
    private Button endTurnButton;
    
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
    private Label activePlayerLocationName;
    
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
        
        rollDiceButton.setOnAction(e -> DialogContent.diceRollAndMovePane(manager.getGameDialog(GameDialogs.BLANK), model));
        
        jailEscapeButton.setOnAction(e -> {
            if(model.getActivePlayer().hasGOJFCard()) {
                model.useActivePlayerGOJFCard();
            }
            else {
                model.getActivePlayer().addCash(-50);
            }
        });
        
        endTurnButton.setOnAction(e -> model.nextPlayer());
        
        //tradeButton.dis
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
     * @param sprite The ImageView to be associated with the user instance.
     * @param colour The colour to be associated with the user instance.
     * @param name The name to be associated with the player instance.
     * @param array The 
     */
    @Override
    public void lateDataPass(String sprite, String colour, String name, ArrayList<ObservableList<String>> array) {
        model.createAndAddUser(name);
        model.createAndAddCPU();
        sprites.put(model.getUser(), new ImageView(new Image("fxmonopoly/resources/images/sprites/" + sprite + ".png")));
        colours.put(model.getUser(), Color.valueOf(colour));
        
        array.get(0).remove(sprite);
        array.get(1).remove(colour);
        
        for(Player player : model.getInitialList()) {
            if(player != model.getUser()) {
                sprites.put(player, new ImageView(new Image("fxmonopoly/resources/images/sprites/" + array.get(0).get(0) + ".png")));
                colours.put(player, Color.valueOf(array.get(1).get(0)));
                
                for(ObservableList observe : array) {
                    observe.remove(0);
                }
            }
        }
        
        PauseTransition pause = new PauseTransition(Duration.millis(3000));
        pause.setOnFinished(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    DialogContent.diceRollPane(manager.getGameDialog(GameDialogs.BLANK), model);
                    SpriteManipulation.populateInitialPositions(sprites, board, boardAnchor);
                    generateUIBindings();
                }
            });
        });
        pause.play();
        
        
    }
    
    /**
     * Creates the necessary bindings to ensure that the visible elements react to the 
     * relevant game state.
     */
    public void generateUIBindings() {
        initialPropertyValues();
        uiListeners();
        uiElementDisableListeners();
    }
    
    /**
     * Sets the initial UI element property values.
     */
    private void initialPropertyValues() {
        activePlayerSprite.imageProperty().setValue(sprites.get(model.getActivePlayer()).getImage());
        
        activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
        
        activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
        
        activePlayerName.textProperty().bind(model.getActivePlayerNameProperty());
        
        userSprite.imageProperty().setValue(sprites.get(model.getUser()).getImage());
        
        userCash.textProperty().setValue(String.valueOf("£" + model.getUser().getCash()));
    }
    
    /**
     * Registers change listeners on relevant properties to update specific element 
     * properties.
     */
    private void uiListeners() {
        model.getActivePlayerProperty().addListener(e -> {
            activePlayerSprite.imageProperty().setValue(sprites.get(model.getActivePlayer()).getImage());
        });
        
        model.getActivePlayerCashProperty().addListener(e -> {
            activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
        });
        
        model.getActivePlayerLocationNameProperty().addListener(e -> {
            activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
        });
        
        model.getUser().getCashProperty().addListener(e -> {
            userCash.textProperty().setValue(String.valueOf("£" + model.getUser().getCash()));
        });
        
        model.getActivePlayer().getPositionProperty().addListener(e -> {
            SpriteManipulation.pathTransition(sprites.get(model.getActivePlayer()), model, board);
        });
    }
    
    /**
     * Registers bindings on the disableProperty of affected nodes to ensure that
     * these elements are only active when the game state dictates.
     */
    private void uiElementDisableListeners() {
        rollDiceButton.disableProperty().bind(model.getUser().getCanRollProperty().not());
        
        jailEscapeButton.disableProperty().bind(Bindings.createBooleanBinding(() -> 
                                               model.getUser().getIsInJailProperty().getValue() && model.getUserIsActiveProperty().getValue(),
                                               model.getUser().getIsInJailProperty(), 
                                               model.getUserIsActiveProperty()).not());
        
        endTurnButton.disableProperty().bind(Bindings.createBooleanBinding(() ->
                                            model.getUserIsActiveProperty().getValue() && !model.getUser().getCanRoll(),
                                            model.getUserIsActiveProperty(),
                                            model.getUser().getCanRollProperty()).not());
    }
    
}
