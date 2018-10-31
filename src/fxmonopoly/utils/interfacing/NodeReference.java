package fxmonopoly.utils.interfacing;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public enum NodeReference {

    DIE_1(new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"))),
    DIE_2(new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"))),
    ROLL_BUTTON(new Button("Roll")),
    ROLL_HBOX(new HBox(20)),
    BOARD_HBOX(new HBox(20)),
    BOARD_LOCATION_IMAGE(new ImageView()),
    BOARD_LOCATION_TEXT(new Label()),
    BID_HBOX(new HBox(20)),
    BID_VBOX_PARENT(new VBox(10)),
    OPPONENTS_LIST(new ComboBox()),
    TRADE_LIST_PLAYER(new ListView()),
    TRADE_LIST_OPPONENT(new ListView()),
    TRADE_CASH_LABEL_PLAYER(new Label()),
    TRADE_CASH_LABEL_OPPONENT(new Label()),
    TRADE_CASH_SLIDER_PLAYER(new Slider()),
    TRADE_CASH_SLIDER_OPPONENT(new Slider()),
    TRADE_OPPONENT_SIDE(new VBox(10)),
    TRADE_PANE(new HBox(30)),
    TRADE_PLAYER_SIDE(new VBox(10)),
    TRADE_RECEIVED_FROM_LABEL(new Label()),
    TRADE_RECEIVED_TO_LABEL(new Label("In exchange for")),
    GOJF_OPPONENT_ADD(new Button("+")),
    GOJF_OPPONENT_REMOVE(new Button("-")),
    GOJF_PLAYER_ADD(new Button("+")),
    GOJF_PLAYER_REMOVE(new Button("-")),
    GOJF_HBOX_PLAYER(new HBox(10)),
    GOJF_HBOX_OPPONENT(new HBox(10)),
    GOJF_LABEL_PLAYER(new Label("Get Out of Jail Free Cards:")),
    GOJF_LABEL_OPPONENT(new Label("Get Out of Jail Free Cards:")),
    END_GAME_VBOX(new VBox(20))
    ;

    NodeReference(Node node) { this.node = node; }

    private Node node;

    public <T extends Node> T getNode() { return (T) node; }

}
