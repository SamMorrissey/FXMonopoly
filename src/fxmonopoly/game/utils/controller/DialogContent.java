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
        return dialog;
    }

    public Dialog diceRollAndMovePane(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseDiceRollContent()
            .setDisabledStateOfButton(ButtonType.CLOSE, true);
        return dialog;
    }

    public Dialog unownedOwnableLocation(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent(true)
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return dialog;
    }

    public Dialog cardLocation(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseBoardLocationContent(true)
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return dialog;
    }

    public Dialog bidDialog(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateBaseBidContent()
            .sizeToScene()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y);
        return dialog;
    }

    public Dialog genericPositionDialog(Dialog dialog, int width) {
        builder = new DialogContentBuilder(dialog)
            .genericBoardPositionDialog()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene()
            .setPaneMaxWidth(width);
        return dialog;
    }

    public Dialog ownedOwnableLocationDialog(Dialog dialog, int width) {
        builder = new DialogContentBuilder(dialog)
            .ownedOwnableLocationDialog()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .setPaneMaxWidth(width)
            .sizeToScene();
        return dialog;
    }

    public Dialog genericOwnedPropertySetUp(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .genericOwnedPropertyDialog()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return dialog;
    }

    public Dialog genericUnownedPropertyDialog(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .genericUnownedProperty()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y)
            .sizeToScene();
        return dialog;
    }

    public Dialog tradeOfferDialog(Dialog trade){
        builder = new DialogContentBuilder(trade);
        HBox sides = registerNodeByName(NodeReference.TRADE_PANE.name(), NodeReference.TRADE_PANE.getNode());
        sides.setAlignment(Pos.CENTER);

        VBox offer = builder.generateBaseTradeSideContent(true, false);
        offer.setAlignment(Pos.CENTER);
        offer.getChildren().add(0, new Label("Your offer: "));

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
        receive.getChildren().add(0, registerNodeByName(NodeReference.TRADE_RECEIVED_TO_LABEL.name(), NodeReference.TRADE_RECEIVED_TO_LABEL.getNode()));

        sides.getChildren().addAll(offer, receive);
        sides.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);

        trade.getDialogPane().setContent(sides);
        trade.getDialogPane().getScene().getWindow().sizeToScene();

        return trade;

    }

    public Dialog statsDialog(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .generateStatContent()
            .sizeToScene()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y);
        return dialog;
    }

    public Dialog bankruptcyResolutionDialog(Dialog dialog) {
        builder = new DialogContentBuilder(dialog)
            .bankruptcyResolutionDialog()
            .sizeToScene()
            .setXAndYPosition(CENTRE_DIALOG_X, CENTRE_DIALOG_Y);
        return dialog;
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
