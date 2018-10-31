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
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.players.CPUPlayer;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.gamedata.players.UserPlayer;
import fxmonopoly.utils.GameDialogs;
import fxmonopoly.utils.GameDialogs2;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;
import fxmonopoly.utils.interfacing.LateData;
import fxmonopoly.utils.interfacing.Manageable;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

import fxmonopoly.utils.interfacing.NodeReference;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        
        exitButton.setOnAction(e ->  exitDialog());
        
        rollDiceButton.setOnAction(e -> dieRollAndMoveDialog());
        
        tradeButton.setOnAction(e -> tradeOfferDialog());
        
        statsButton.setOnAction(e -> DialogContent.statsDialog(model, manager.getGameDialog(GameDialogs2.BLANK), colours, sprites, board));
        
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

    private void dieRollAndMoveDialog() {
        int[] dieRolls = model.rollDie();
        DialogContent content = new DialogContent();
        Dialog dialog = content.diceRollAndMovePane(manager.getGameDialog(GameDialogs2.BLANK), dieRolls);
        Button roll = content.registerNodeByName(NodeReference.ROLL_BUTTON.name(), NodeReference.ROLL_BUTTON.getNode());

        roll.setOnAction(ActionEvent.ACTION, e -> {
            HBox box = content.retrieveNodeByName(NodeReference.ROLL_HBOX.name();
            ((ImageView) box.getChildren().get(0)).setImage(new Image("fxmonopoly/resources/images/die/" + dieRolls[0] + ".png"));
            ((ImageView) box.getChildren().get(1)).setImage(new Image("fxmonopoly/resources/images/die/" + dieRolls[1] + ".png"));

            dialog.getDialogPane().lookupButton(ButtonType.CLOSE).setDisable(false);
            roll.disableProperty().setValue(true);
        });
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, e -> {
            model.diceMove(dieRolls);
            pathTransition(model);
        });
        dialog.showAndWait();
    }

    private void dieRollDialog() {
        int[] dieRolls = model.rollDie();
        DialogContent content = new DialogContent();
        Dialog dialog = content.diceRollAndMovePane(manager.getGameDialog(GameDialogs2.BLANK), dieRolls);
        Button roll = content.registerNodeByName(NodeReference.ROLL_BUTTON.name(), NodeReference.ROLL_BUTTON.getNode());
        roll.setOnAction(ActionEvent.ACTION, e -> {
            HBox box = content.retrieveNodeByName(NodeReference.ROLL_HBOX.name());
            ((ImageView) box.getChildren().get(0)).setImage(new Image("fxmonopoly/resources/images/die/" + i[0] + ".png"));
            ((ImageView) box.getChildren().get(1)).setImage(new Image("fxmonopoly/resources/images/die/" + i[1] + ".png"));
            model.reorderList(i[0] + i[1]);

            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            roll.disableProperty().setValue(true);
        });
        DialogContent.diceRollPane(
            manager.getGameDialog(GameDialogs2.BLANK),
            model.rollDie());
    }

    private void exitDialog() {
        manager.getGameDialog(GameDialogs2.EXIT).showAndWait();
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
        pause.setOnFinished(e -> Platform.runLater(this::dieRollDialog));

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
                DialogContent.endGameDialog(
                    manager.getGameDialog(GameDialogs2.BLANK),
                    new ImageView(sprites.get(model.getPlayerList().get(0)).getImage()),
                    model.getPlayerList().get(0).getName() + "has won the Match!",
                    "£" + model.getPlayerList().get(0).getCash(),
                    "-fx-text-fill: #" + String.valueOf(colours.get(model.getPlayerList().get(0))).substring(2) + ";",
                    () -> manager.changeScene(View.MAIN_MENU)
                );
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
                    DialogContent.bidDialog(manager.getGameDialog(GameDialogs2.BLANK), model, board);
            }
        }
        else {
            if(model.getActivePlayer().getCash() < 0) {
                if(model.isActivePlayerBankrupt())
                     model.removeActivePlayerFromGame();
                else
                    DialogContent.bankruptcyResolutionDialog(model, manager.getGameDialog(GameDialogs2.BLANK), board);
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
            tradeReceivedDialog();
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
                    ImageView graphic = new ImageView();
                    if(model.getActiveBid().containsLocation())
                        graphic = getBoardLocationImage(board, model.retrieveLocationPosition(model.getActiveBid().getLocation()));
                    else if(model.getActiveBid().containsGOJFCard())
                        graphic.setImage(new Image("fxmonopoly/resources/images/LeaveJailIcon"));

                    DialogContent.bidDialog(
                        manager.getGameDialog(GameDialogs2.BLANK),
                        graphic,
                        "Enter Max Bid:",
                        model::resolveActiveBid,
                        bidValue -> model.getActiveBid().addBid(model.getUser(), bidValue)
                    );
                    
                    if(model.getActiveBid() != null && !model.getActiveBid().getHighestBidder().isEmpty() && !(model.getActiveBid().getHighestBidder().get(0) instanceof UserPlayer)) {
                        DialogContent.bidDialog(
                            manager.getGameDialog(GameDialogs2.BLANK),
                            graphic,
                            "Max Bid Currently: " + (model.getActiveBid().getSecondHighestBid() + 1) + "\n" + "Enter Max Bid:",
                            model::resolveActiveBid,
                            bidValue -> model.getActiveBid().addBid(model.getUser(), bidValue)
                        );
                    }
                    else {
                        model.resolveActiveBid();
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
        });
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
    }
    
    /**
     * Runs the next move of the game, should be called after the pathTransition method.
     */
    public void runNextMove() {
        if(model.userIsActive()) {
            activePlayerCash.textProperty().setValue(String.valueOf("£" + model.getActivePlayerCashProperty().getValue()));
            activePlayerLocationName.textProperty().setValue(model.retrieveLocation(model.getActivePlayer().getPosition()).getName());
                
            Platform.runLater(() -> {
                getNewPositionDialog();
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
                    try {
                        getDecisionSystem().developmentDecision();
                        getDecisionSystem().makeTrade();
                    } catch (Exception e) { }
                        
                    model.nextPlayer();
                }    
            });
        }
    }

    /**
     * Retrieves the Image from the specified board position.
     * @param board The board to retrieve bounds from.
     * @param position The board position to grab the image of.
     * @return The image created.
     */
    public static Image getBoardLocationImage(ArrayList<BoardButton> board, int position) {
        int minX = (int) board.get(position).getBoundsInParent().getMinX() + 1;
        int minY = (int) board.get(position).getBoundsInParent().getMinY() + 1;

        int width = (int) board.get(position).getBoundsInParent().getWidth() - 2;
        int height = (int) board.get(position).getBoundsInParent().getHeight() - 2;

        Image image = new Image("fxmonopoly/resources/images/Board.png");
        PixelReader reader = image.getPixelReader();
        WritableImage newImage = new WritableImage(reader, minX, minY, width, height);

        ImageView crop = new ImageView(newImage);
        crop.setPreserveRatio(true);
        crop.setRotate(calculateRotation(position));

        if((position > 10 && position < 20) || (position > 30 && position <= 39)) {
            crop.setFitHeight(width);
            crop.setFitWidth(height + (width - height));
        }

        return crop.getImage();
    }

    /**
     * Calculates the board image rotation for utilisation in Dialogs.
     * @param position The position to calculate the rotation from.
     * @return The rotation to apply.
     */
    private static int calculateRotation(int position) {
        if(position >= 0 && position < 10) {
            return 0;
        }
        else if(position > 10 && position < 20) {
            return 270;
        }
        else if(position >= 20 && position <= 30) {
            return 180;
        }
        else {
            return 90;
        }
    }

    /**
     * Provides the methods for retrieving the necessary dialog.
     * @param manager The manager to utilise.
     * @param controller The controller to pass.
     * @param model The model to pass.
     * @param board The BoardButton list to pass.
     */
    public void getNewPositionDialog() {
        Location location = model.retrieveLocation(model.getActivePlayer().getPosition());
        Image image = getBoardLocationImage(board, model.getActivePlayer().getPosition());
        ImageView graphic = 
        graphic.setRotate(calculateRotation(model.getActivePlayer().getPosition()));

        if (location instanceof BaseOwnableLocation) {
            if ( !((BaseOwnableLocation) location).getIsOwned() ) {
                unownedOwnableLocation();
            }
        } else if (location instanceof ChanceLocation || location instanceof CommunityChestLocation) {
            DialogContent.cardLocation(
                manager.getGameDialog(GameDialogs2.BLANK),
                graphic,
                model.getActiveCard().getDescription(),
                model::processCardActions
            );
        }
    }

    private void unownedOwnableLocation(Image image) {
        DialogContent content = new DialogContent();
        Dialog dialog = content.unownedOwnableLocation(manager.getGameDialog(GameDialogs2.BLANK));
        HBox box = content.retrieveNodeByName(NodeReference.BOARD_HBOX.name());
        ImageView graphic = content.retrieveNodeByName(NodeReference.BOARD_LOCATION_IMAGE.name());
        graphic.setImage(image);
        box.setMinHeight(graphic.getFitWidth() + 20);

        ButtonType buy = new ButtonType("Buy", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buy);
        dialog.getDialogPane().lookupButton(buy).addEventFilter(ActionEvent.ACTION, e -> model.activePlayerBuyLocation());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, e -> {
            if (!model.locationIsOwned(model.retrieveLocation(model.getActivePlayer().getPosition()))) {
                model.startBid();
                model.getActiveBid().setLocation(model.retrieveLocation(model.getActivePlayer().getPosition()));
                bidResolution();
            }
        });
    }

    private void cardLocationDialog() {
        DialogContent content = new DialogContent();
        Dialog dialog = content.cardLocation(manager.getGameDialog(GameDialogs2.BLANK));

    }

    private void tradeOfferDialog() {
        model.startTrade(model.getUser());

        DialogContent content = new DialogContent();
        Dialog dialog = content.tradeOfferDialog(manager.getGameDialog(GameDialogs2.BLANK));

        Slider playerSlider = content.retrieveNodeByName(NodeReference.TRADE_CASH_SLIDER_PLAYER.name());
        playerSlider.setMax(model.getUser().getCash());

        ComboBox combo = content.retrieveNodeByName(NodeReference.OPPONENTS_LIST.name());
        model.getPlayerList().forEach(player -> {
            if (player != model.getUser()) combo.getItems().add(player.getName());
        });
        combo.getSelectionModel().selectedItemProperty().addListener(tradeOpponentListListener(content));

        VBox offer = content.retrieveNodeByName(NodeReference.TRADE_OPPONENT_SIDE.name());
        offer.getChildren().add(0, combo);
        tradeButtonActions(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, e -> tradeConfirmListener(content, combo));
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, e -> model.cancelActiveTrade());
        dialog.showAndWait();
    }

    private void tradeReceivedDialog() {
        model.startTrade(model.getUser());

        DialogContent content = new DialogContent();
        Dialog dialog = content.tradeReceivedDialog(manager.getGameDialog(GameDialogs2.BLANK));

        Slider playerSlider = content.retrieveNodeByName(NodeReference.TRADE_CASH_SLIDER_PLAYER.name());
        playerSlider.setMax(model.getUser().getCash());

        tradeButtonActions(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, e -> model.resolveActiveTrade());
        dialog.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, e -> model.cancelActiveTrade());
        dialog.showAndWait();
    }

    private void tradeConfirmListener(DialogContent content, ComboBox combo) {
        if(combo.getSelectionModel().getSelectedItem() != null) {
            ListView<String> list = content.retrieveNodeByName(NodeReference.TRADE_LIST_PLAYER.name());
            for(Location location : model.getActiveTrade().getPlayerFrom().getOwnedLocations()) {
                for(String string : list.getSelectionModel().getSelectedItems()) {
                    if(string.equals(location.getName()))
                        model.getActiveTrade().getOfferList().add(location);
                }
            }
            ListView<String> forList = content.retrieveNodeByName(NodeReference.TRADE_LIST_OPPONENT.name());
            for(Location location : model.getActiveTrade().getPlayerTo().getOwnedLocations()) {
                for(String string : forList.getSelectionModel().getSelectedItems()) {
                    if(string.equals(location.getName()))
                        model.getActiveTrade().getForList().add(location);
                }
            }

            Slider playerCash = content.retrieveNodeByName(NodeReference.TRADE_CASH_SLIDER_PLAYER.name());
            Slider opponentCash = content.retrieveNodeByName(NodeReference.TRADE_CASH_SLIDER_OPPONENT.name());

            if(playerCash.getValue() > 0) {
                model.getActiveTrade().addCashTo((int) playerCash.getValue());
            }
            if(opponentCash.getValue() > 0) {
                model.getActiveTrade().addCashFrom((int) opponentCash.getValue());
            }

            tradeResolution();
        } else {
            model.cancelActiveTrade();
        }

    }

    private void setUpTradeContentListeners(Player player, DialogContent content) {
        ListView<String> list = content.retrieveNodeByName(
            player instanceof UserPlayer ? NodeReference.TRADE_LIST_PLAYER.name() : NodeReference.TRADE_LIST_OPPONENT.name()
        );

        ObservableList<String> locations = FXCollections.observableArrayList();
        for(Location location : player.getOwnedLocations()) {
            locations.add(location.getName());
        }
        list.setItems(locations);
        list.setCellFactory(e -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            Location location = model.getUser().getOwnedLocations().get(locations.indexOf(cell.textProperty().getValue()) + 1);
            if (((BaseOwnableLocation) location).getMortgaged())
                cell.getStyleClass().add("mortgaged-cell");

            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                list.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (list.getSelectionModel().getSelectedIndices().contains(index)) {
                        list.getSelectionModel().clearSelection(index);
                    } else {
                        list.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });

            return cell;
        });
    }

    private void tradeButtonActions(DialogContent content) {
        content.retrieveNodeByName(NodeReference.GOJF_PLAYER_ADD.name()).addEventFilter(ActionEvent.ACTION, e -> {
            if(model.getUser().hasGOJFCard())
                model.getActiveTrade().getGOJFListTo().add(model.getUser().getGOJFCard());
        });
        content.retrieveNodeByName(NodeReference.GOJF_PLAYER_REMOVE.name()).addEventFilter(ActionEvent.ACTION, e -> {
            if(!model.getActiveTrade().getGOJFListTo().isEmpty())
                model.getActiveTrade().getGOJFListTo().remove(0);
        });
        content.retrieveNodeByName(NodeReference.GOJF_OPPONENT_ADD.name()).addEventFilter(ActionEvent.ACTION, e -> {
            if(model.getActiveTrade().getPlayerTo() != null && model.getActiveTrade().getPlayerTo().hasGOJFCard())
                model.getActiveTrade().getGOJFListFrom().add(model.getUser().getGOJFCard());
        });
        content.retrieveNodeByName(NodeReference.GOJF_OPPONENT_REMOVE.name()).addEventFilter(ActionEvent.ACTION, e -> {
            if(!model.getActiveTrade().getGOJFListFrom().isEmpty())
                model.getActiveTrade().getGOJFListFrom().remove(0);
        });
    }

    private ChangeListener tradeOpponentListListener(DialogContent content) {
        return (observable, oldValue, newValue) -> {
            ObservableList<String> listFor = FXCollections.observableArrayList();
            ListView<String> forList = content.retrieveNodeByName(NodeReference.TRADE_LIST_OPPONENT.toString());
            for(Player player : model.getPlayerList()) {
                if(player.getName().equals(newValue)) {
                    model.getActiveTrade().setPlayerTo(player);
                    for(Location location : player.getOwnedLocations()) {
                        listFor.add(location.getName());
                    }
                    forList.setItems(listFor);
                    forList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                    forList.setCellFactory(e -> {
                        ListCell<String> cell = new ListCell<>();
                        cell.textProperty().bind(cell.itemProperty());
                        Location location = model.getActiveTrade().getPlayerTo().getOwnedLocations().get(listFor.indexOf(cell.textProperty().getValue()) + 1);
                        if (((BaseOwnableLocation) location).getMortgaged())
                            cell.getStyleClass().add("mortgaged-cell");

                        cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                            forList.requestFocus();
                            if (! cell.isEmpty()) {
                                int index = cell.getIndex();
                                if (forList.getSelectionModel().getSelectedIndices().contains(index)) {
                                    forList.getSelectionModel().clearSelection(index);
                                }
                                else {
                                    forList.getSelectionModel().select(index);
                                }
                                event.consume();
                            }
                        });
                        return cell;
                    });
                    ((Slider) content.retrieveNodeByName(NodeReference.TRADE_CASH_SLIDER_OPPONENT.toString())).setMax(player.getCash());
                    model.getActiveTrade().getGOJFListFrom().removeAll(model.getActiveTrade().getGOJFListFrom());
                    break;
                }
            }
        };
    }
}
