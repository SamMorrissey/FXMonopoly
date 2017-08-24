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
    
    private final ArrayList<GOJFCard> cardListFrom;
    private final ArrayList<GOJFCard> cardListTo;
    
    private int cashFrom;
    private int cashTo;
    
    /**
     * Creates a trade offer instance regarding the specified players. Adding
     * Locations must be done via the ArrayList getter methods. 
     * @param playerFrom The player initiating the trade offer.
     */
    public TradeOffer(Player playerFrom) {
        this.playerFrom = playerFrom;
        
        offerLocations = new ArrayList<>();
        forLocations = new ArrayList<>();
        cardListTo = new ArrayList<>();
        cardListFrom = new ArrayList<>();
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
     * @param playerTo The player the trade offer is aimed at.
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
     * Retrieves the list of GOJF cards being offered by the initiating player.
     * @return The list of GOJF card being offered by the initiating player.
     */
    public ArrayList<GOJFCard> getGOJFListTo() {
        return cardListTo;
    }
    
    public ArrayList<GOJFCard> getGOJFListFrom() {
        return cardListFrom;
    }
    
    /**
     * Retrieves the boolean representing whether there are any GOJF cards in the
     * offer.
     * @return True if unequal amounts of GOJFCards are being offered, false if both
     * lists are empty or there are equal quantities on offer.
     */
    public boolean containsGOJFCard() {
        if(cardListTo.isEmpty() && cardListFrom.isEmpty())
            return false;
        else
            return !(cardListTo.size() == cardListFrom.size());
    }
    
    /**
     * Adds the specified cash value to the offer. Bearing in mind that any cash
     * value of 0 or below will cause the containsCash method to return false.
     * @param cashTo The cash to be added.
     */
    public void addCashTo(int cashTo) {
        this.cashTo = cashTo;
    }
    
    /**
     * Retrieves the cashTo value associated with this trade.
     * @return The cash value from the initiating player;
     */
    public int getCashTo() {
        return cashTo;
    }
    
    /**
     * Adds the specified cash value to the offer. Any value of 0 or below will
     * cause the containsCash method to return false.
     * @param cashFrom The cash intended to be received.
     */
    public void addCashFrom(int cashFrom) {
        this.cashFrom = cashFrom;
    }
    
    /**
     * Retrieves the cashFrom value associated with this trade.
     * @return The cash value to the initiating player.
     */
    public int getCashFrom() {
        return cashFrom;
    }
    
    /**
     * Retrieves the boolean representing whether the cash values cancel each other
     * out or not. Since if the cash values are equal there is no point transferring
     * money across.
     * @return True if a cash value is being offered, false if both cashTo and cashFrom
     * are 0 or if both values are equal.
     */
    public boolean containsCash() {
        if(cashTo == 0 && cashFrom == 0) 
            return false;
        else 
            return !(cashTo == cashFrom);
    }
    
}
