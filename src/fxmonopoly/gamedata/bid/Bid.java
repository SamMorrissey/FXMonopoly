/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.bid;

import fxmonopoly.gamedata.players.Player;
import java.util.HashMap;

/**
 * Defines the Bid class.
 * <p>
 * Utilises a HashMap to reference each bid.
 * @author Sam P. Morrissey
 */
public class Bid {
    
    private final HashMap<Player, Integer> bidMap;
    
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
    public Player getHighestBidder() {
        Player bidder = null;
        
        for(Player player : bidMap.keySet()) {
            if(bidMap.get(player).equals(getHighestBid())) {
                bidder = player;
            }
        }
        return bidder;
    }
}
