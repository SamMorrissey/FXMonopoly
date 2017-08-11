/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata;

import fxmonopoly.gamedata.bid.Bid;
import fxmonopoly.gamedata.board.Board;
import fxmonopoly.gamedata.board.locations.*;
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
    private final ArrayList<Player> playerList;
    
    private Player activePlayer;
    private TradeOffer activeTrade;
    private Bid activeBid;
    
    private UserPlayer user;
    
    /**
     * Creates a GameData instance utilising the specified player list.
     * @param playerList The ordered list of players.
     */
    public GameData(ArrayList<Player> playerList) {
        
        die = new Die();
        board = new Board();
        chance = new ChanceDeck();
        community = new CommunityChestDeck();
        this.playerList = playerList;
        
        activePlayer = playerList.get(0);
        
        for(Player player : playerList) {
            if(player instanceof UserPlayer && user == null) {
                user = (UserPlayer) player;
                break;
            }
        }
        
    }
    
    /**
     * Retrieves the list of players passed to the constructor.
     * @return The list of players in the game.
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
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
     * Sets the active player to the specified input parameter.
     * @param player The new active player.
     */
    private void setActivePlayer(Player player) {
        if(player != null) {
            activePlayer = player;
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
     * Retrieves the Location object associated with the player's board position
     * int value.
     * @return The active player's current location.
     */
    public Location getActivePlayerLocation() {
        return board.getLocation(activePlayer.getPosition());
    }
    
    /**
     * Retrieves the current board position of the active player.
     * @return The active player's board position.
     */
    public int getActivePlayerPosition() {
        return activePlayer.getPosition();
    }
    
    /**
     * Retrieves the value of the active player's cash.
     * @return The active player's cash.
     */
    public int getActivePlayerCash() {
        return activePlayer.getCash();
    }
    
    /**
     * Retrieves the active player's name.
     * @return The active player's name.
     */
    public String getActivePlayerName() {
        return activePlayer.getName();
    }
    
    /**
     * Retrieves the active player's jail status.
     * @return True if in jail, false otherwise.
     */
    public boolean getActivePlayerJailStatus() {
        return activePlayer.isInJail();
    }
    
    /**
     * Changes the active player jail status to true.
     */
    public void activePlayerEnterJail() {
        activePlayer.enterJail();
    }
    
    /**
     * Changes the active player jail status to false.
     */
    public void activePlayerExitJail() {
        activePlayer.exitJail();
    }
    
    /**
     * Retrieves the active player's can roll die status. 
     * @return True if can roll, false otherwise.
     */
    public boolean getActivePlayerDieRollStatus() {
        return activePlayer.getCanRoll();
    }
    
    /**
     * Sets the active player's can roll die status to the specified input.
     * @param status The value to set the status to.
     */
    public void setActivePlayerDieRollStatus(boolean status) {
        activePlayer.setCanRoll(status);
    }
    
    /**
     * Moves the active player's board position by the specified distance.
     * @param distance The distance to move by.
     */
    public void moveActivePlayerBy(int distance) {
        activePlayer.moveBy(distance);
    }
    
    /**
     * Moves the active player to the specified board position.
     * @param position The position to move to.
     */
    public void moveActivePlayerTo(int position) {
        activePlayer.moveTo(position);
    }
   
    /**
     * Sets the use player to the specified user player instance.
     * @param player The player to be set.
     */
    public void setUserPlayer(UserPlayer player) {
        user = player;
    }
    
    /**
     * Retrieves the user player instance.
     * @return The user player.
     */
    public UserPlayer getUserPlayer() {
        return user;
    }
    
    /**
     * Retrieves the cash of the player instance set as the user player.
     * @return 
     */
    public int getUserPlayerCash() {
        return user.getCash();
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
     * @return The next chance card.
     */
    public Card getNextChanceCard() {
        return chance.getNextCard();
    }
    
    /**
     * Returns the specified chance card back to the bottom of the deck.
     * @param card The card to return.
     */
    public void returnChanceCard(Card card) {
        chance.returnCard(card);
    }
    
    /**
     * Retrieves the next card from the Community Chest deck.
     * @return The next community chest card.
     */
    public Card getNextCommunityChestCard() {
        return community.getNextCard();
    }
    
    /**
     * Returns the specified community chest card back to the bottom of the deck.
     * @param card The card to return.
     */
    public void returnCommunityChestCard(Card card) {
        community.returnCard(card);
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
