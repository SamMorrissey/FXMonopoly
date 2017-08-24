/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.bid;

import fxmonopoly.gamedata.board.locations.Location;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.gamedata.players.UserPlayer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines the Bid class.
 * <p>
 * Utilises a HashMap to reference each bid.
 * @author Sam P. Morrissey
 */
public class Bid {
    
    private final HashMap<Player, Integer> bidMap;
    private Location location;
    private GOJFCard card;
    private boolean resolvingBankruptcy;
    
    public Bid() {
        bidMap = new HashMap<>();
    }
    
    /**
     * Retrieves the boolean regarding whether any bids exist or not.
     * @return True if a bid has been made, false otherwise.
     */
    public boolean containsBid() {
        return !bidMap.isEmpty();
    }
    
    /**
     * Adds the specified bid to the HashMap if the bid is a positive value above
     * 0. Otherwise the bid is not added.
     * @param player The player making the bid.
     * @param bid    The value of the bid.
     */
    public void addBid(Player player, int bid) {
        if(bid > 0) {
           bidMap.put(player, bid);
        }
    }
    
    /**
     * Retrieves the highest value bid.
     * @return The highest value bid.
     */
    public Integer getHighestBid() {
        Integer i = 0;
        
        for(Integer integer : bidMap.values()) {
            if(integer > i) {
                i = integer;
            }
        }
        
        return i;
    }
    
    /**
     * Retrieves the player that made the highest bid.
     * @return The highest bidding player.
     */
    public ArrayList<Player> getHighestBidder() {
        
        ArrayList<Player> highestBidders = new ArrayList<>();
        
        for(Player player : bidMap.keySet()) {
            if(bidMap.get(player).equals(getHighestBid())) {
                if(player instanceof UserPlayer) {
                    highestBidders.add(0, player);
                }
                else {
                    highestBidders.add(player);
                }
            }
        }

        return highestBidders;
    }
    
    /**
     * Retrieves the second highest bid, as long as there is a single highest bidder.
     * @return The second highest bid.
     */
    public int getSecondHighestBid() {
        int i = 0;
        
        if(getHighestBidder().size() == 1) {
            
            for(Player player : bidMap.keySet()) {
                if(bidMap.get(player) > i && !bidMap.get(player).equals(bidMap.get(getHighestBidder().get(0)))) {
                    i = bidMap.get(player);
                }
            }
        }
        else {
            i = getHighestBid();
        }
        
        return i;
    }
    
    /**
     * Sets the GOJFCard being bid on.
     * @param card The GOJFCard the bid concerns.
     */
    public void setGOJFCard(GOJFCard card) {
        this.card = card;
        if(card != null) {
            location = null;
        }
    }
    
    /**
     * Retrieves the GOJFCard being bid on.
     * @return The GOJFCard being bid on.
     */
    public GOJFCard getGOJFCard() {
        return card;
    }
    
    /**
     * Determines whether the bid concerns a GOJFCard.
     * @return True if there is a GOJFCard, false otherwise.
     */
    public boolean containsGOJFCard() {
        return card != null;
    }
    
    /**
     * Sets the Location being bid on.
     * @param location The Location the bid concerns.
     */
    public void setLocation(Location location) {
        this.location = location;
        if(location != null) {
            card = null;
        }
    }
    
    /**
     * Retrieves the Location being bid on.
     * @return The location being bid on.
     */
    public Location getLocation() {
        return location;
    }
    
    /**
     * Determines whether the bid concerns a Location.
     * @return True if there is a Location, false otherwise.
     */
    public boolean containsLocation() {
        return location != null;
    }
}
