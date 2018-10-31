/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.utils.DialogContentBuilder;

import java.util.*;
import java.util.function.Consumer;

import fxmonopoly.utils.interfacing.NodeReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Provides the content necessary for all dynamic dialogs.
 * <p>
 * Exclusively contains static methods since this utility has no need to represent
 * any state, it merely manipulates existing states.
 * @author Sam P. Morrissey.
 */
public class DialogContent {

    public static int CENTRE_DIALOG_X;
    public static int CENTRE_DIALOG_Y;

    private Map<String, Node> nodeMap = new LinkedHashMap<>();
    private DialogContentBuilder builder;

    public <T extends Node> T registerNodeByName(String name, T node) {
        nodeMap.put(name, node);
        return node;
    }

    public <T extends Node> T retrieveNodeByName(String name) { return (T) nodeMap.get(name); }

    private void pullBuilderNodesIntoMap() { builder.getNodeMap().forEach((key, value) -> nodeMap.put(key.name(), value)); }

    /**
     * Creates a Dice roll pane to do the initial order.
     * @param diceRoll The alert to set the content of.
     * @param model The model to utilise.
     */
    public Dialog diceRollPane(Dialog diceRoll, int[] dieRolls) {
        DialogContentBuilder builder = new DialogContentBuilder(diceRoll)
            .generateBaseDiceRollContent(dieRolls)
            .setDisabledStateOfButton(ButtonType.OK, true);
        return builder.eject();
    }
    
    /**
     * Creates the Dice roll pane that moves the active player.
     * @param diceRoll The alert to set the content of.
     * @param newPositionAlert The dialog to pass on.
     * @param controller The controller to utilise.
     * @param model The model to utilise.
     * @param board The board to determine dialog positioning from.
     */
    public Dialog diceRollAndMovePane(Dialog diceRoll, int[] dieRolls) {
        DialogContentBuilder builder = new DialogContentBuilder(diceRoll)
            .generateBaseDiceRollContent(dieRolls)
            .setDisabledStateOfButton(ButtonType.CLOSE, true);
        return builder.eject();
    }
    
    /**
     * Displays the Dialog for when an ownable location is reached but has no current
     * owner.
     * @param manager The manager to retrieve the dialog from.
     * @param controller The controller to utilise for print outs.
     * @param model The model to operate on.
     * @param board The board position to grab the image from.
     */
    public Dialog unownedOwnableLocation(Dialog dialog) {
        DialogContentBuilder builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return builder.eject();
    }
    
    /**
     * Displays the Dialog utilised when a Card location is landed on.
     * @param dialog The Dialog to set the content of.
     * @param model The model to operate on.
     * @param board The board position to grab the image from.
     */
    public Dialog cardLocation(Dialog dialog, ImageView graphic, String text, Runnable okAction) {
        map.put(ButtonType.OK, okAction);
        DialogContentBuilder builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return builder.eject();
    }
    
    /**
     * Retrieves the initial bid Dialog window.
     * @param bid The dialog to fill the content of.
     * @param model The model to operate on.
     * @param board The board to grab images from.
     */
    public static void bidDialog(Dialog bid, ImageView graphic, String text, Runnable resolveActiveBid, Consumer<Integer> okAddBid) {
        Label enterBid = new Label(text);
        TextField numeric = new TextField();
        numeric.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numeric.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(new ButtonType("Bid", ButtonData.OK_DONE), (e) -> {
            if(numeric.getText().isEmpty() || numeric.getText() == null)
                resolveActiveBid.run();
            else
                okAddBid.accept(Integer.parseInt(numeric.getText()));
        });
        map.put(ButtonType.CANCEL, resolveActiveBid);

        DialogContentBuilder builder = new DialogContentBuilder(bid)
            .generateBaseBidContent(graphic, Arrays.asList(enterBid, numeric), map)
            .sizeToScene()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y);
        builder.activate();
    }

    /**
     * Retrieves the Dialog for when a board button is clicked on, to provide
     * information on generic areas i.e. non-ownable locations.
     * @param position The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     * @param indexOf The index of the button to be utilised (i.e. board position).
     */
    public static void genericPositionDialog(Dialog position, ImageView graphic, String text, int width) {
        Label label = new Label(text);
        label.setMinWidth(80);
        label.wrapTextProperty().setValue(Boolean.TRUE);

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(ButtonType.OK, () -> {});
        DialogContentBuilder builder = new DialogContentBuilder(position)
            .generateBaseBoardLocationContent(Arrays.asList(graphic,label), map)
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene()
            .setPaneMaxWidth(width);
        builder.activate();
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param position The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     * @param indexOf The index of the button to be utilised (i.e. board position).
     */
    public static void ownedOwnableLocationDialog(Dialog position, ImageView graphic, String text, Runnable developAction, Runnable undevelopAction, Runnable mortgageAction) {

        HBox box = new HBox(20);
        HBox graph = new HBox(10);

        Label text = setUpText(text);
        List<Button> buttons = ownedPropertyButtons(developAction, undevelopAction, mortgageAction);
        buttons.get(2).setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        int height = 0;
        for (Button button : buttons) height += button.getHeight();

        graph.getChildren().addAll(graphic);
        graph.setMinHeight(graphic.getFitWidth() + 20);
        graph.setAlignment(Pos.CENTER);
                
        VBox box2 = new VBox(20);
        box2.setAlignment(Pos.CENTER);
        box2.getChildren().add(graph);
        box2.getChildren().addAll(buttons);
        box.setMinHeight(graphic.getFitWidth() + height + 20);
                
        box.getChildren().addAll(box2, text);

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(ButtonType.OK, () -> {});
        DialogContentBuilder builder = new DialogContentBuilder(position)
            .generateBaseBoardLocationContent(box, map)
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .setPaneMaxWidth(170)
            .sizeToScene();
        builder.activate();
    }

    private static Label setUpText(String text) {
        Label label = new Label(text);
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        label.wrapTextProperty().setValue(Boolean.TRUE);
    }

    private static void applyButtonTypes(Map<Button, Runnable> buttonActionMap) {
        buttonActionMap.entrySet().forEach( entry ->
            entry.getKey().setOnAction(e -> entry.getValue().run())
        );
    }

    private static List<Button> ownedPropertyButtons(Runnable developOnAction, Runnable undevelopOnAction, Runnable mortgageOnAction) {
        return Arrays
            .asList(developButton(developOnAction), undevelopButton(undevelopOnAction), mortgageButton(mortgageOnAction));
    }

    private Button developButton(Runnable onAction) {
        return createAndRegisterCustomButton("+", onAction);
    }

    private Button undevelopButton(Runnable onAction) {
        return createAndRegisterCustomButton("-", onAction);
    }

    private Button mortgageButton(Runnable onAction) {
        return createAndRegisterCustomButton("(De)Mortgage", onAction);
    }

    private Button createAndRegisterCustomButton(String label, Runnable onAction, String name) {
        Button button = createCustomButton(label, onAction);
        nodeMap.put(name, button);
        return button;
    }

    private static Button createCustomButton(String label, Runnable onAction) {
        Button button = new Button(label);
        button.setOnAction(e -> onAction.run());
        button.setStyle("-fx-font-size: 20;");
        return button;
    }

    public static void genericOwnedPropertySetUp(Dialog dialog, ImageView graphic, String text, Runnable mortgageAction) {
        HBox box = new HBox(20);
        Label label = setUpText(text);

        Button mortgage = mortgageButton(mortgageAction);
        mortgage.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        if (graphic != null)
            box.getChildren().addAll(graphic, label, mortgage);
        else
            box.getChildren().addAll(label, mortgage);
        box.setMinHeight(graphic.getFitWidth() + 20);

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(ButtonType.OK, () -> {});
        DialogContentBuilder builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent(box, map)
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        builder.activate();
    }

    public static void genericUnownedPropertyDialog(Dialog dialog, ImageView graphic, String text) {
        HBox box = new HBox(20);
        HBox graph = new HBox(10);
        Label label = setUpText(text);

        graph.getChildren().add(graphic);
        graph.setAlignment(Pos.CENTER);
        graph.setMinHeight(graphic.getFitWidth() + 20);

        box.getChildren().addAll(graph, label);
        box.setMinHeight(graphic.getFitWidth() + 20);

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(ButtonType.OK, () -> {});
        DialogContentBuilder builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent(box, map)
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        builder.activate();
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param controller The controller for text print outs.
     * @param trade The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     */
    public Dialog tradeOfferDialog(Dialog trade){
        DialogContentBuilder builder = new DialogContentBuilder(trade);
        HBox sides = registerNodeByName(NodeReference.TRADE_PANE.name(), NodeReference.TRADE_PANE.getNode());
        sides.setAlignment(Pos.CENTER);

        VBox offer = builder.generateBaseTradeSideContent(true, false);
        offer.setAlignment(Pos.CENTER);
        offer.getChildren().add(0, setUpText("Your offer: "));

        VBox receive = builder.generateBaseTradeSideContent(false, false);
        receive.setAlignment(Pos.CENTER);
        receive.getChildren().add(0, registerNodeByName(NodeReference.OPPONENTS_LIST.name(), NodeReference.OPPONENTS_LIST.getNode()));
        
        sides.getChildren().addAll(offer, receive);
        sides.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
        
        trade.getDialogPane().setContent(sides);
        trade.getDialogPane().getScene().getWindow().sizeToScene();

        return trade;
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param trade The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     */
    public Dialog tradeReceivedDialog(Dialog trade) {
        DialogContentBuilder builder = new DialogContentBuilder(trade);
        HBox sides = registerNodeByName(NodeReference.TRADE_PANE.name(), NodeReference.TRADE_PANE.getNode());
        sides.setAlignment(Pos.CENTER);

        VBox offer = builder.generateBaseTradeSideContent(false, true);
        offer.setAlignment(Pos.CENTER);
        offer.getChildren().add(0, registerNodeByName(NodeReference.TRADE_RECEIVED_FROM_LABEL.name(), NodeReference.TRADE_RECEIVED_FROM_LABEL.getNode()));

        VBox receive = builder.generateBaseTradeSideContent(true, true);
        receive.setAlignment(Pos.CENTER);
        receive.getChildren().add(0, registerNodeByName(NodeReference.TRADE_RECEIVED_TO_LABEL.name(), NodeReference.TRADE_RECEIVED_TO_LABEL.getNode());

        sides.getChildren().addAll(offer, receive);
        sides.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);

        trade.getDialogPane().setContent(sides);
        trade.getDialogPane().getScene().getWindow().sizeToScene();

        return trade;

    }
    
    /**
     * Generates and displays the stats dialog.
     * @param model The model to get the player object from.
     * @param dialog The dialog to manipulate.
     * @param colours The colours to utilise.
     * @param sprites The sprites to utilise.
     * @param board The board for generating the dialog position.
     */
    public static void statsDialog(GameModel model, Dialog dialog, HashMap<Player, Color> colours, HashMap<Player, ImageView> sprites, ArrayList<BoardButton> board) {
        
        HBox box = new HBox(40);
        
        for(Player player : model.getPlayerList()) {
            VBox info = new VBox(10);
            
            Label label = new Label(player.getName());
            label.setStyle("-fx-text-fill :" + "#" + String.valueOf(colours.get(player)).substring(2) + ";");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

            ImageView image = new ImageView(sprites.get(player).getImage());
            
            Label cash = new Label("£" + player.getCash());
            cash.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            
            info.getChildren().addAll(label, image, cash);
            box.getChildren().add(info);
        }
        
        box.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
        dialog.getDialogPane().setContent(box);
        
        if(dialog.getDialogPane().getButtonTypes().isEmpty()) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - dialog.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - dialog.getHeight() / 2;
        
            dialog.setX(x);
            dialog.setY(y);
        }
        
        dialog.showAndWait();
    }
    
    /**
     * Provides the dialog for bankruptcy resolution.
     * @param model The model to utilise.
     * @param dialog The dialog to populate.
     * @param board The board for positioning the dialog.
     */
    public static void bankruptcyResolutionDialog(GameModel model, Dialog dialog, ArrayList<BoardButton> board) {
        VBox box = new VBox(20);
        
        ListView<String> list = new ListView();
        list.setMaxHeight(100);
        list.setMaxWidth(150);
        
        ObservableList<String> locations = FXCollections.observableArrayList();
        for(Location location : model.getUser().getOwnedLocations()) {
            locations.add(location.getName());
        }
        
        Button undevelop = new Button("Downgrade");
        undevelop.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        
        list.setItems(locations);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        list.setCellFactory(e -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            
            Location location = model.getUser().getOwnedLocations().get(locations.indexOf(cell.textProperty().getValue()) + 1);
            if(location instanceof BaseOwnableLocation) {
                if (((BaseOwnableLocation) location).getMortgaged())
                    cell.getStyleClass().add("mortgaged-cell");
            }
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                list.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (location instanceof PropertyLocation && (((PropertyLocation) location).getIsHotel() || ((PropertyLocation) location).getNumberOfHouses() > 0)) {
                        undevelop.disableProperty().setValue(false);
                    } 
                    else {
                        undevelop.disableProperty().setValue(true);
                    }
                }
            });
            return cell;
        });
        
        Button sellGOJFCards = new Button("Sell Get Out of Jail Free Card");
        if(model.getUser().hasGOJFCard())
            sellGOJFCards.disableProperty().setValue(false);
        else
            sellGOJFCards.disableProperty().setValue(true);
        
        sellGOJFCards.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        
        sellGOJFCards.setOnAction(e -> {
            
        });
        
        box.getChildren().addAll(list, undevelop, sellGOJFCards);
        box.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
        
        dialog.getDialogPane().setContent(box);
        
        if(dialog.getDialogPane().getButtonTypes().isEmpty()) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            
            dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
                
            });
            
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - dialog.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - dialog.getHeight() / 2;
        
            dialog.setX(x);
            dialog.setY(y);
        }
        
        dialog.showAndWait();
    }
    
    /**
     * Provides the end game dialog.
     * @param manager The manager to utilise.
     * @param model The model to utilise.
     * @param dialog The dialog to fill the content of.
     * @param colours The colour to utilise.
     * @param sprites The sprite to utilise.
     */
    public static void endGameDialog(Dialog dialog, ImageView image, String playerName, String playerCash, String colour, Runnable onAction) {
        Label name = new Label(playerName);
        name.setStyle(colour);
        Label cash = new Label(playerCash);
        List<Node> elements = Arrays.asList(name, image, cash);

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(ButtonType.OK, onAction);
        DialogContentBuilder builder = new DialogContentBuilder(dialog)
            .generateEndGameContent(elements, map);
        builder.activate();
    }
}
