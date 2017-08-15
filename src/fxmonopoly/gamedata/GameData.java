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
    
    private Player activePlayer;
    private TradeOffer activeTrade;
    private Bid activeBid;
    private Card activeCard;
    
    /**
     * Creates a GameData instance utilising the specified player list.
     * @param playerList The ordered list of players.
     */
    public GameData() {
        
        die = new Die();
        board = new Board();
        chance = new ChanceDeck();
        community = new CommunityChestDeck();
        
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
        activePlayer.setCanRoll(true);
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
            if(activePlayer == player && playerList.size() != 1) {
                nextPlayer();
            }
        }
    }
    
    /**
     * Retrieves the current active player of the game.
     * @return The active player.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }
    
    /**
     * Sets the active player to the first entry of the player list, provided the
     * player list is not null.
     */
    private void setActivePlayer() {
        if(playerList != null) {
            activePlayer = playerList.get(0);
        }
    }
    
    /**
     * Switches the active player to the next player in the list, or returning
     * to the first player in the list from the last player in the list.
     */
    public void nextPlayer() {
        if(playerList.indexOf(activePlayer) + 1 < playerList.size()) {
            activePlayer = playerList.get(playerList.indexOf(activePlayer) + 1);
        }
        else if(playerList.indexOf(activePlayer) + 1 == playerList.size()) {
            activePlayer = playerList.get(0);
        }
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
     * Returns the specified chance card back to the bottom of the deck. If a
     * GOJFCard is presented, the card will only be inserted if the deck does not
     * currently contain one. Otherwise it is returned to the other deck.
     * @param card The card to return.
     */
    public void returnChanceCard(Card card) {
        if(card instanceof GOJFCard && chance.containsGOJFCard())
            community.returnCard(card);
        else
            chance.returnCard(card);
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
        if(card instanceof GOJFCard && community.containsGOJFCard())
            chance.returnCard(card);
        else 
            community.returnCard(card);
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
