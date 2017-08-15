/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.trade;

import fxmonopoly.gamedata.board.locations.Location;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import fxmonopoly.gamedata.players.Player;
import java.util.ArrayList;

/**
 * Defines the Trade Offer class. 
 * @author Sam P. Morrissey
 */
public class TradeOffer {
    
    private final Player playerFrom;
    private Player playerTo;
    
    private final ArrayList<Location> offerLocations;
    private final ArrayList<Location> forLocations;
    private final ArrayList<GOJFCard> cardList;
    
    private int cash;
    
    /**
     * Creates a trade offer instance regarding the specified players. Adding
     * Locations must be done via the ArrayList getter methods. 
     * @param playerFrom The player initiating the trade offer.
     */
    public TradeOffer(Player playerFrom) {
        this.playerFrom = playerFrom;
        
        offerLocations = new ArrayList<>();
        forLocations = new ArrayList<>();
        cardList = new ArrayList<>();
    }
    
    /**
     * Retrieves the player that made the offer.
     * @return The player that made the offer.
     */
    public Player getPlayerFrom() {
        return playerFrom;
    }
    
    /**
     * Sets the recipient of the trade offer to the specified input.
     * @param playerTo 
     */
    public void setPlayerTo(Player playerTo) {
        if(playerTo != null) 
            this.playerTo = playerTo;
    }
    
    
    public boolean hasPlayerTo() {
        return !(playerTo == null);
    }
    
    /**
     * Retrieves the player that the offer was made to.
     * @return The player the offer was made to.
     */
    public Player getPlayerTo() {
        return playerTo;
    }
    
    /**
     * Retrieves the list of locations pertaining to the offer, as in regarding the
     * player from.
     * @return The list of locations being offered.
     */
    public ArrayList<Location> getOfferList() {
        return offerLocations;
    }
    
    /**
     * Retrieves the boolean representing whether the trade offer contains any
     * locations being offered.
     * @return True if locations are being offered, false otherwise.
     */
    public boolean containsOfferLocations() {
        return !offerLocations.isEmpty();
    }
    
    /**
     * Retrieves the list of locations pertaining to the in return. 
     * @return The list of locations the offer is for.
     */
    public ArrayList<Location> getForList() {
        return forLocations;
    }
    
    /**
     * Retrieves the boolean representing whether the in return for list contains
     * any locations.
     * @return True if locations are wanted, false otherwise.
     */
    public boolean containsForLocations() {
        return !forLocations.isEmpty();
    }
    
    /**
     * Retrieves the list of GOJF cards being offered. 
     * @return The list of GOJF card being offered.
     */
    public ArrayList<GOJFCard> getGOJFList() {
        return cardList;
    }
    
    /**
     * Retrieves the boolean representing whether there are any GOJF cards in the
     * offer.
     * @return True if GOJFcard(s) are being offered, false otherwise.
     */
    public boolean containsGOJFCard() {
        return !cardList.isEmpty();
    }
    
    /**
     * Adds the specified cash value to the offer. Bearing in mind that any cash
 value of 0 or below will cause the containsCash method to return false.
     * @param cash The cash to be added.
     */
    public void addCash(int cash) {
        this.cash = cash;
    }
    
    /**
     * Retrieves the boolean representing whether there are any GOJF cards in the 
     * offer.
     * @return True if a cash value is being offered, false otherwise.
     */
    public boolean containsCash() {
        return cash > 0;
    }
    
}
