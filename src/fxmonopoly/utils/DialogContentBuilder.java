package fxmonopoly.utils;

import fxmonopoly.utils.interfacing.NodeReference;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class DialogContentBuilder {

    private Node content;
    private Dialog dialog;
    private List<String> tradeLocationsList;

    private Map<NodeReference, Node> nodeMap = new LinkedHashMap<>();

    public DialogContentBuilder(Dialog dialog) { this.dialog = dialog; }

    public void activate() { dialog.showAndWait(); }

    public Dialog eject() { return dialog; }

    private <T extends Node> T createNodeAndRegister(NodeReference reference) {
        nodeMap.put(reference, reference.getNode());
        return (T) reference.getNode();
    }

    public <T extends Node> T getNodeByReference(NodeReference reference) {
        return (T) nodeMap.get(reference);
    }

    public <T extends Node> T getPlayerOrOpponentNode(NodeReference reference, boolean isPlayer) {
        if ((reference.name().contains("PLAYER")))
            return isPlayer ?
                (T) reference.getNode() :
                (T) getByName(reference.name().replace("PLAYER", "OPPONENT")).getNode();
        else if ((reference.name().contains("OPPONENT")))
            return  isPlayer ?
                (T) getByName(reference.name().replace("OPPONENT", "PLAYER")).getNode() :
                (T) reference.getNode();
        else
            return null;
    }

    private NodeReference getByName(String name) {
        return NodeReference.valueOf(name);
    }

    public Map<NodeReference, Node> getNodeMap() { return nodeMap; }

    private Label setUpText(String text) {
        Label label = new Label(text);
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        label.wrapTextProperty().setValue(Boolean.TRUE);
    }

    public DialogContentBuilder generateBaseDiceRollContent(int[] dieRolls) {
        HBox dice = createNodeAndRegister(NodeReference.ROLL_HBOX);

        ImageView die1 = createNodeAndRegister(NodeReference.DIE_1);
        ImageView die2 = createNodeAndRegister(NodeReference.DIE_2);
        Button rollAction = createNodeAndRegister(NodeReference.ROLL_BUTTON);
        rollAction.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        dice.getChildren().addAll(die1, die2, rollAction);
        dice.setAlignment(Pos.CENTER);

        dialog.getDialogPane().setContent(dice);

        return this;
    }

    public DialogContentBuilder generateBaseBoardLocationContent() {
        HBox box = createNodeAndRegister(NodeReference.BOARD_HBOX);
        ImageView graphic = createNodeAndRegister(NodeReference.BOARD_LOCATION_IMAGE);
        Label label = createNodeAndRegister(NodeReference.BOARD_LOCATION_TEXT);
        label.wrapTextProperty().setValue(Boolean.TRUE);
        box.getChildren().addAll(graphic, label);
        box.setAlignment(Pos.CENTER);
        box.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);

        dialog.getDialogPane().setContent(box);

        return this;
    }

    public DialogContentBuilder generateBaseBidContent(ImageView view, List<Node> intoContent, Map<ButtonType, Runnable> buttonActionMap) {
        VBox box = createNodeAndRegister(NodeReference.BID_VBOX_PARENT);
        HBox graph = createNodeAndRegister(NodeReference.BID_HBOX);
        box.setAlignment(Pos.CENTER);
        graph.getChildren().addAll(view);
        graph.setMinHeight(view.getFitWidth() + 20);
        graph.setAlignment(Pos.CENTER);
        box.getChildren().addAll(graph);
        box.getChildren().addAll(intoContent.toArray(new Node[0]));
        dialog.getDialogPane().setContent(box);
        applyButtonTypes(buttonActionMap);

        return this;
    }

    public DialogContentBuilder generateEndGameContent(List<Node> intoContent, Map<ButtonType, Runnable> buttonActionMap) {
        VBox box = createNodeAndRegister(NodeReference.END_GAME_VBOX);
        box.getChildren().addAll(intoContent);
        dialog.getDialogPane().setContent(box);
        applyButtonTypes(buttonActionMap);

        return this;
    }

    public VBox generateBaseTradeSideContent(boolean player, boolean tradeReceived) {
        VBox offer = getPlayerOrOpponentNode(NodeReference.TRADE_PLAYER_SIDE, player);
        offer.setAlignment(Pos.CENTER);

        ListView<String> list = getPlayerOrOpponentNode(NodeReference.TRADE_LIST_PLAYER, player);
        list.setMaxHeight(100);
        list.setMaxWidth(150);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Label cashValue = getPlayerOrOpponentNode(NodeReference.TRADE_CASH_LABEL_PLAYER, player);
        cashValue.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        cashValue.wrapTextProperty().setValue(Boolean.TRUE);
        Slider cashSlider = getPlayerOrOpponentNode(NodeReference.TRADE_CASH_SLIDER_PLAYER, player);
        cashSlider.setMaxWidth(120);
        cashSlider.setMin(0);
        cashSlider.valueProperty().addListener((observable, oldValue, newValue) ->
            cashValue.textProperty().setValue(String.valueOf("£" + (int) cashSlider.getValue()))
        );

        Label gojf = getPlayerOrOpponentNode(NodeReference.GOJF_LABEL_PLAYER, player);
        gojf.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        gojf.wrapTextProperty().setValue(Boolean.TRUE);

        if (!tradeReceived) {
            Button add = getPlayerOrOpponentNode(NodeReference.GOJF_PLAYER_ADD, player);
            Button remove = getPlayerOrOpponentNode(NodeReference.GOJF_PLAYER_REMOVE, player);
            HBox gojfBox = getPlayerOrOpponentNode(NodeReference.GOJF_HBOX_PLAYER, player);
            gojfBox.setAlignment(Pos.CENTER);
            gojfBox.getChildren().addAll(add, remove);
            offer.getChildren().addAll(list, cashValue, gojf, gojfBox);
        } else {
            offer.getChildren().addAll(list, cashValue, gojf);
        }

        return offer;
    }

    private Node retrieveComponentByPlayer()

    private void applyButtonTypes(Map<ButtonType, Runnable> buttonActionMap) {
        buttonActionMap.entrySet().forEach( entry -> {
            dialog.getDialogPane().getButtonTypes().add(entry.getKey());
            if (entry.getValue() != null)
                lookupButtonAndApplyOnEvent(entry.getKey(), entry.getValue());
        });
    }

    private void lookupButtonAndApplyOnEvent(ButtonType button, Runnable event) {
        if (lookupButton(button) != null)
            lookupButton(button).addEventFilter(ActionEvent.ACTION, e -> event.run());
    }

    private Node lookupButton(ButtonType button) { return dialog.getDialogPane().lookupButton(button); }

    public DialogContentBuilder setDisabledStateOfButton(ButtonType button, boolean disable) {
        if (lookupButton(button) != null)
            lookupButton(button).setDisable(disable);
        return this;
    }

    public DialogContentBuilder setContentText(String text) { dialog.setContentText(text); return this; }

    public DialogContentBuilder setXAndYPosition(double x, double y) {
        dialog.setX(x);
        dialog.setY(y);
        return this;
    }

    public DialogContentBuilder sizeToScene() {
        dialog.getDialogPane().getScene().getWindow().sizeToScene();
        dialog.getDialogPane().layout();
        return this;
    }

    public DialogContentBuilder setPaneMaxWidth(int width) {
        dialog.getDialogPane().setMaxWidth(width);
        return this;
    }

    public DialogContentBuilder setContentMinHeight(int height) {
        dialog.getDialogPane().getContent().minHeight(height);
        return this;
    }
}
