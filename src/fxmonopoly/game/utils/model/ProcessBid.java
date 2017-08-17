/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.gamedata.bid.Bid;
import fxmonopoly.gamedata.board.locations.Location;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.gamedata.players.UserPlayer;
import java.util.ArrayList;

/**
 * Processes the bid object specified in the resolveBid method.
 * <p>
 * All methods are static since this class is a utility function, not an active
 * participant in game state.
 * @author Sam P. Morrissey
 */
public final class ProcessBid {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private ProcessBid() {}
    
    /**
     * Determines the winning bidder and then calls processBidTransfer
     * @param bid        The bid to operate on.
     * @param playerList The player list to pass when processing transfer.
     */
    public static void resolveBid(Bid bid, ArrayList<Player> playerList) {
        
        
        if(bid.getHighestBidder().size() == 1) {
            bid.getHighestBidder().get(0).addCash(-(bid.getSecondHighestBid() + 1));
            
            processBidTransfer(bid, playerList, bid.getHighestBidder().get(0));
        }
        else if(bid.getHighestBidder().size() > 1) {
            
            
            Player highestBidder = null;
            
            for(Player player : bid.getHighestBidder()) {
                if(player instanceof UserPlayer) {
                    highestBidder = player;
                    break;
                }
            }
            
            if(highestBidder == null) {
                highestBidder = bid.getHighestBidder().get(0);
            }
            
     
            highestBidder.addCash(-bid.getHighestBid());
            
            processBidTransfer(bid, playerList, highestBidder);
        }    
        
    }
    
    /**
     * Transfers the item being bid on.
     * @param bid        The bid to process.
     * @param playerList The player list to check against.
     * @param player     The winning player.
     */
    private static void processBidTransfer(Bid bid, ArrayList<Player> playerList, Player player) {
        
        if(bid.containsGOJFCard()) {
            for(Player temp : playerList) {
                if(temp.getGOJFCard() == bid.getGOJFCard()) {
                    temp.removeGOJFCard();
                }
            }
            
            player.addGOJFCard(bid.getGOJFCard());
        }
        else if(bid.containsLocation()) {
            Location location = bid.getLocation();
            
            if(location instanceof PropertyLocation) {
                PropertyLocation temp = ((PropertyLocation) location);
                
                if(temp.getOwner() != null)
                    temp.getOwner().removeLocation(location);
                
                temp.transferOwnership(player);
                player.addLocation(location);
            }
            else if(location instanceof UtilityLocation) {
                UtilityLocation temp = ((UtilityLocation) location);
                
                if(temp.getOwner() != null) 
                    temp.getOwner().removeLocation(location);
                
                
                temp.getOwner().removeLocation(location);
                temp.setOwner(player);
                player.addLocation(location);
            }
            else if(location instanceof RailwayLocation) {
                RailwayLocation temp = ((RailwayLocation) location);
                
                if(temp.getOwner() != null)
                    temp.getOwner().removeLocation(location);
                
                temp.getOwner().removeLocation(location);
                ((RailwayLocation) location).setOwner(player);
                player.addLocation(location);
            }
        }
    }
}
