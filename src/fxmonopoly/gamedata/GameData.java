/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata;

import fxmonopoly.gamedata.bid.Bid;
import fxmonopoly.gamedata.board.Board;
import fxmonopoly.gamedata.decks.*;
import fxmonopoly.gamedata.decks.cards.*;
import fxmonopoly.gamedata.die.Die;
import fxmonopoly.gamedata.players.*;
import fxmonopoly.gamedata.trade.TradeOffer;
import java.util.ArrayList;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Specifies the data necessary for a game. Provides some utility methods to 
 * perform specific actions, and many getter methods to allow for higher level
 * manipulations.
 * @author Sam P. Morrissey
 */
public class GameData {
    
    private final Die die;
    private final Board board;
    private final ChanceDeck chance;
    private final CommunityChestDeck community;
    
    private ArrayList<Player> playerList;
    
    private final SimpleObjectProperty<Player> activePlayers;
    
    private TradeOffer activeTrade;
    private Bid activeBid;
    private Card activeCard;
    
    private UserPlayer user;
    
    private int doublesInARow;
    
    /**
     * Creates a GameData instance utilising the specified player list.
     */
    public GameData() {
        
        die = new Die();
        board = new Board();
        chance = new ChanceDeck();
        community = new CommunityChestDeck();
        
        activePlayers = new SimpleObjectProperty(this, "activePlayer", null);
    }
    
    /**
     * Retrieves the list of players passed to the constructor.
     * @return The list of players in the game.
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    
    /**
     * Sets the player list of this GameData instance.
     * @param array The final order player list.
     */
    public void setPlayerList(ArrayList<Player> array) {
        playerList = array;
        setActivePlayer();
    }
    
    
    /**
     * Adds a player to the player list, provided that the player is not already 
     * in the list.
     * @param player The player to be added.
     
    public void addPlayer(Player player) {
        if(!playerList.contains(player)) {
            playerList.add(player);
        }
    } */
    
    
    /**
     * Removes the specified player from the player list. If the player is also 
     * the active player, the nextPlayer method is called provided that more than
     * one player is still in the list (one player left is the end game).
     * @param player The player to be removed.
     */
    public void removePlayer(Player player) {
        if(playerList.contains(player)) {
            playerList.remove(player);
    
            if(playerList.size() > 1) {
                nextPlayer();
            }
        }
    }
    
    /**
     * Retrieves the current active player of the game.
     * @return The active player.
     */
    public Player getActivePlayer() {
        return activePlayers.getValue();
    }
    
    public SimpleObjectProperty<Player> getActivePlayerProperty() {
        return activePlayers;
    }
    
    /**
     * Sets the active player to the first entry of the player list, provided the
     * player list is not null.
     */
    private void setActivePlayer() {
        if(playerList != null) {
            activePlayers.setValue(playerList.get(0));
            //activePlayer = playerList.get(0);
            activePlayers.getValue().setCanRoll(true);
        }
    }
    
    /**
     * Retrieves the user player of the game.
     * @return The user player.
     */
    public UserPlayer getUserPlayer() {
        return user;
    }
    
    /**
     * Sets the user player to the specified UserPlayer instance.
     * @param user The user player.
     */
    public void setUserPlayer(UserPlayer user) {
        this.user = user;
    }
    
    /**
     * Switches the active player to the next player in the list, or returning
     * to the first player in the list from the last player in the list.
     */
    public void nextPlayer() {
        if(playerList.indexOf(activePlayers.getValue()) + 1 < playerList.size()) {
            activePlayers.setValue(playerList.get(playerList.indexOf(activePlayers.getValue()) + 1));
            doublesInARow = 0;
            activePlayers.getValue().setCanRoll(true);
        }
        else if(playerList.indexOf(activePlayers.getValue()) + 1 == playerList.size()) {
            activePlayers.setValue(playerList.get(0));
            doublesInARow = 0;
            activePlayers.getValue().setCanRoll(true);
        }
    }
    
    public int getDoublesInARow() {
        return doublesInARow;
    }
    
    public void incrementDoublesInARow() {
        doublesInARow++;
    }
    
    /**
     * Retrieves the board instance.
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Retrieves the next card from the Chance deck.
     */
    public void drawNextChanceCard() {
        activeCard = chance.getNextCard();
    }
    
    /**
     * Returns the specified chance card back to the bottom of the deck.
     * @param card The card to return.
     */
    public void returnChanceCard(Card card) {
        if(card.getFromChanceDeck()) {
            chance.returnCard(card);
            activeCard = null;
        }
    }
    
    /**
     * Retrieves the next card from the Community Chest deck.
     */
    public void drawNextCommunityChestCard() {
        activeCard = community.getNextCard();
    }
    
    /**
     * Returns the specified community chest card back to the bottom of the deck.
     * If a GOJFCard is presented, the card will only be inserted if the deck 
     * does not currently contain one. Otherwise it is returned to the other deck.
     * @param card The card to return.
     */
    public void returnCommunityChestCard(Card card) {
        if(!card.getFromChanceDeck()) {
            community.returnCard(card);
            activeCard = null;
        }
    }
    
    /**
     * Retrieves the currently active Card. 
     * @return 
     */
    public Card getActiveCard() {
        return activeCard;
    }
    
    /**
     * Creates a new active trade from the specified player.
     * @param playerFrom The player initiating a trade offer.
     */
    public void newTrade(Player playerFrom) {
        activeTrade = new TradeOffer(playerFrom);
    }
    
    /**
     * Retrieves the active trade instance.
     * @return The active trade instance.
     */
    public TradeOffer getActiveTrade() {
        return activeTrade;
    }
    
    /**
     * Sets the active trade to a null value.
     */
    public void clearActiveTrade() {
        activeTrade = null;
    }
    
    /**
     * Creates a new active bid instance.
     */
    public void newBid() {
        activeBid = new Bid();
    }
    
    /**
     * Retrieves the active bid instance.
     * @return The active bid instance.
     */
    public Bid getActiveBid() {
        return activeBid;
    }
    
    /**
     * Sets the active bid to a null value.
     */
    public void clearActiveBid() {
        activeBid = null;
    }
    
    /**
     * Retrieves the Die object.
     * @return The die to be used.
     */
    public Die getDie() {
        return die;
    }
    
}
