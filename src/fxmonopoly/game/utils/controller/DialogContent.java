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

    @SuppressWarnings("unchecked")
    public <T extends Node> T retrieveNodeByName(String name) { return (T) nodeMap.get(name); }

    private void pullBuilderNodesIntoMap() { builder.getNodeMap().forEach((key, value) -> nodeMap.put(key.name(), value)); }

    public Dialog diceRollPane(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseDiceRollContent()
            .setDisabledStateOfButton(ButtonType.OK, true);
        return builder.eject();
    }

    public Dialog diceRollAndMovePane(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseDiceRollContent()
            .setDisabledStateOfButton(ButtonType.CLOSE, true);
        return dialog;
    }

    public Dialog unownedOwnableLocation(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return dialog;
    }

    public Dialog cardLocation(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return dialog;
    }

    public Dialog bidDialog(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseBidContent()
            .sizeToScene()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y);
        return builder.eject();
    }

    public static void genericPositionDialog(Dialog position, ImageView graphic, String text, int width) {
        Label label = new Label(text);
        label.setMinWidth(80);
        label.wrapTextProperty().setValue(Boolean.TRUE);

        Map<ButtonType, Runnable> map = new LinkedHashMap<>();
        map.put(ButtonType.OK, () -> {});
        DialogContentBuilder builder = new DialogContentBuilder(position)
            .generateBaseBoardLocationContent()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene()
            .setPaneMaxWidth(width);
        builder.activate();
    }

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
            .generateBaseBoardLocationContent()
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
            .generateBaseBoardLocationContent()
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
            .generateBaseBoardLocationContent()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        builder.activate();
    }

    public Dialog tradeOfferDialog(Dialog trade){
        builder = new DialogContentBuilder(trade);
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

    public Dialog tradeReceivedDialog(Dialog trade) {
        builder = new DialogContentBuilder(trade);
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

    public Dialog statsDialog(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateStatContent(dialog)
            .sizeToScene()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y);
        return dialog;
    }

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
