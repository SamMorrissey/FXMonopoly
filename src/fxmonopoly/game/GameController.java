/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.game.utils.controller.BoardButton;
import fxmonopoly.game.utils.controller.BoardPopulating;
import fxmonopoly.game.utils.controller.DecisionSystem;
import fxmonopoly.game.utils.controller.DialogContent;
import fxmonopoly.game.utils.controller.SpriteManipulation;
import fxmonopoly.gamedata.players.CPUPlayer;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.gamedata.players.UserPlayer;
import fxmonopoly.utils.GameDialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.interfacing.LateData;
import fxmonopoly.utils.interfacing.Manageable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
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
    private DecisionSystem system;
    
    private HashMap<Player, ImageView> sprites;
    private HashMap<Player, Color> colours;
    
    private ArrayList<BoardButton> board;
    
    @FXML
    private ScrollPane scroll;
    
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
        system = new DecisionSystem(model, this);
        
        iconifiedButton.setOnAction(e -> manager.setIconified());
        
        exitButton.setOnAction(e ->  manager.getGameDialog(GameDialogs.EXIT).showAndWait() );
        
        rollDiceButton.setOnAction(e -> DialogContent.diceRollAndMovePane(manager.getGameDialog(GameDialogs.BLANK), 
                                                                          manager.getGameDialog(GameDialogs.BLANK),
                                                                          this,
                                                                          model,
                                                                          board));
        
        tradeButton.setOnAction(e -> DialogContent.tradeOfferDialog(this, manager.getGameDialog(GameDialogs.BLANK), model, board));
        
        statsButton.setOnAction(e -> DialogContent.statsDialog(model, manager.getGameDialog(GameDialogs.BLANK), colours, sprites, board));
        
        jailEscapeButton.setOnAction(e -> {
            if(model.getUser().isInJail()) {
                if(model.getActivePlayer().hasGOJFCard()) {
                    model.useActivePlayerGOJFCard();
                    model.getUser().exitJail();
                }
                else {
                    model.getActivePlayer().addCash(-50);
                    model.getUser().exitJail();
                }
            }
        });
        
        endTurnButton.setOnAction(e -> {
            model.nextPlayer();
            
        });
        
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
        BoardPopulating.generateButtons(board, boardPane, colours, model, manager);
        
        model.createAndAddUser(name);
        model.createAndAddCPU();
        sprites.put(model.getUser(), new ImageView(new Image("fxmonopoly/resources/images/sprites/" + sprite + ".png")));
        colours.put(model.getUser(), Color.valueOf(colour));
       
        array.get(0).remove(sprite);
        array.get(1).remove(colour);
        
        model.setController(this);
        
        for(Player player : model.getInitialList()) {
            if(player != model.getUser()) {
                sprites.put(player, new ImageView(new Image("fxmonopoly/resources/images/sprites/" + array.get(0).get(0) + ".png")));
                colours.put(player, Color.valueOf(array.get(1).get(0)));
                
                for(ObservableList observe : array) {
                    observe.remove(0);
                }
            }
        }
        
        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    DialogContent.diceRollPane(manager.getGameDialog(GameDialogs.BLANK), model);
                    SpriteManipulation.populateInitialPositions(sprites, board, boardAnchor);
                    generateUIBindings();
                    if(model.getActivePlayer() instanceof CPUPlayer) {
                        system.rollDiceAndMove();
                    }
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
        activePlayerName.setStyle("-fx-text-fill: #" + colours.get(model.getActivePlayer()).toString().substring(2) + ";");
        
        userSprite.imageProperty().setValue(sprites.get(model.getUser()).getImage());
        
        userCash.textProperty().setValue(String.valueOf("£" + model.getUser().getCash()));
    }
    
    /**
     * Registers change listeners on relevant properties to update specific element 
     * properties.
     */
    private void uiListeners() {
        model.getActivePlayerProperty().addListener((observable, oldValue, newValue) -> {
            activePlayerSprite.imageProperty().setValue(sprites.get(model.getActivePlayer()).getImage());
            activePlayerName.setStyle("-fx-text-fill: #" + colours.get(model.getActivePlayer()).toString().substring(2) + ";");
            activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
            activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
 
            if(model.getActivePlayer().getTurnsInjail() == 3) {
                model.activePlayerPayToExitJail();
            }
            
            if(newValue instanceof CPUPlayer) {
                system.jailDecision();
                system.rollDiceAndMove();
                pathTransition(model);
                runNextMove();
            }
        });
        
        model.getActivePlayerCashProperty().addListener(e -> {
            activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
            if(model.getActivePlayerCashProperty().getValue() < 0) {
                bankruptcyResolutionAction();
            }
        });
        
        model.getUser().getCashProperty().addListener(e -> {
            userCash.textProperty().setValue(String.valueOf("£" + model.getUser().getCash()));
        });
        
        model.getPlayerListSizeProperty().addListener(e -> {
            if(model.getPlayerListSizeProperty().getValue() == 1) {
                DialogContent.endGameDialog(manager, model, manager.getGameDialog(GameDialogs.BLANK), colours, sprites);
            }
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
    
    /**
     * Deals with the action required on a bankruptcy loop.
     */
    private void bankruptcyResolutionAction() {
        if(model.getActivePlayer() instanceof CPUPlayer) {
            system.bankruptcyResolution();
            if(model.getActivePlayer().getCash() < 0) {
                if(model.isActivePlayerBankrupt())
                    model.removeActivePlayerFromGame();
                else 
                    DialogContent.bidDialog(manager.getGameDialog(GameDialogs.BLANK), model, board);
            }
        }
        else {
            if(model.getActivePlayer().getCash() < 0) {
                if(model.isActivePlayerBankrupt())
                     model.removeActivePlayerFromGame();
                else
                    DialogContent.bankruptcyResolutionDialog(model, manager.getGameDialog(GameDialogs.BLANK), board);
            } 
        }
    }
    
    /**
     * Deals with the action required to resolve a trade.
     */
    public void tradeResolution() {
        if(model.getActiveTrade().getPlayerTo() instanceof CPUPlayer) {
            system.specifiedPlayerRespondToTrade(model.getActiveTrade().getPlayerTo());
        }
        else {
            DialogContent.tradeReceivedDialog(manager.getGameDialog(GameDialogs.BLANK), model, board);
        }
    }
    
    /**
     * Deals with the action required to resolve a bid.
     */
    public void bidResolution() {
        if(model.getActiveBid() != null) {
            for(Player player : model.getPlayerList()) {
                if(player instanceof CPUPlayer && player.getCash() > 0) {
                    system.makeBid(player);
                }
            }
            
            for(Player player : model.getPlayerList()) {
                if(player instanceof UserPlayer && player.getCash() > 0) {
                    DialogContent.bidDialog(manager.getGameDialog(GameDialogs.BLANK), model, board);
                    
                    if(model.getActiveBid() != null && !model.getActiveBid().getHighestBidder().isEmpty() && !(model.getActiveBid().getHighestBidder().get(0) instanceof UserPlayer)) {
                        DialogContent.secondaryBidDialog(manager.getGameDialog(GameDialogs.BLANK), model, board);
                    }
                }
            }
        }
    }
    
    /**
     * Prints the specified string in the colour of the specified player.
     * @param string The string to display.
     * @param player The player to get the colour of.
     */
    public void printToTextFlow(String string, Player player) {
        Text text = new Text(string);
        text.setStyle("-fx-font-size: 12; " +
                      "-fx-font-weight: bold; " +
                      "-fx-fill: #" + String.valueOf(colours.get(player)).substring(2) + ";");
        
        printOut.getChildren().add(text);
        printOut.heightProperty().addListener( e -> {
            printOut.layout();
            scroll.setVvalue( 1.0d ); 
            }
        );
    }    
    
    /**
     * Retrieves the decision system associated with this controller.
     * @return The decision system.
     */
    public DecisionSystem getDecisionSystem() {
        return system;
    }
    
    /**
     * Creates a path transition for the affected sprite.
     * @param model The model to utilise.
     */
    public void pathTransition(GameModel model) {
        ImageView sprite = sprites.get(model.getActivePlayer());
        Path path = new Path();
        
        double[] i = SpriteManipulation.getSinglePositionInsets(model.getActivePlayer().getPosition(), model.getActivePlayer().getIsInJailProperty().getValue());
        
        Bounds bounds = board.get(model.getActivePlayer().getPosition()).getBoundsInParent();
        
        double adjustedX = bounds.getMinX() + i[0];
        double adjustedY = bounds.getMinY() + i[1]; 
        
        path.getElements().add(new MoveTo(sprite.boundsInParentProperty().getValue().getMinX(), sprite.boundsInParentProperty().getValue().getMinY()));
        path.getElements().add(new LineTo(adjustedX, adjustedY));
        
        PathTransition pathTran = new PathTransition();
        pathTran.setDuration(Duration.millis(1500));
        pathTran.setNode(sprite);
        pathTran.setPath(path);
        pathTran.play();
        pathTran = null;
    }
    
    /**
     * Runs the next move of the game, should be called after the pathTransition method.
     */
    public void runNextMove() {
        if(model.userIsActive()) {
            activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
            activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
                
            Platform.runLater(() -> {
                DialogContent.getNewPositionDialog(manager, this, model, board);
                activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
                activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
            });      
        }
        else {
            
            printToTextFlow(model.getActivePlayer().getName() + " has reached " + model.retrieveLocation(model.getActivePlayer().getPosition()).getName() + "\n", model.getActivePlayer());
            activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
            Platform.runLater(() -> {
                getDecisionSystem().positionAction();
                activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
                activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
                if(model.getActivePlayer().getCanRoll()) {
                    getDecisionSystem().rollDiceAndMove();
                }
                else {
                    getDecisionSystem().developmentDecision();
                    getDecisionSystem().makeTrade();
                        
                    model.nextPlayer();
                        
                }    
            });
        }
    }
}
