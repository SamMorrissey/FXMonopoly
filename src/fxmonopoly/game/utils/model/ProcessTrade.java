/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.trade.TradeOffer;

/**
 * Processes the trade object specified in the resolveTrade method.
 * <p>
 * All methods are static since this class is a utility function, not an active
 * participant in game state.
 * @author Sam P. Morrissey
 */
public class ProcessTrade {
    
    
    /**
     * Fully resolves the active trade instance between two players.
     * @param transfer The trade to transfer.
     */
    public static void resolveTrade(TradeOffer transfer) {

            transferTradeTo(transfer);
            transferTradeFrom(transfer);
    }
    
    /**
     * Transfers all items specified to the trade offer recipient.
     */
    private static void transferTradeTo(TradeOffer transfer) {
        
        if(transfer.containsOfferLocations()) {
            
            transfer.getOfferList().forEach(e -> {
                if(e instanceof PropertyLocation) {
                    ((PropertyLocation) e).transferOwnership(transfer.getPlayerTo());
                    transfer.getPlayerTo().addLocation(e);
                    transfer.getPlayerFrom().removeLocation(e);
                }
                else if(e instanceof UtilityLocation) {
                    ((UtilityLocation) e).setOwner(transfer.getPlayerTo());
                    transfer.getPlayerTo().addLocation(e);
                    transfer.getPlayerFrom().removeLocation(e);
                }
                else if(e instanceof RailwayLocation) {
                    ((RailwayLocation) e).setOwner(transfer.getPlayerTo());
                    transfer.getPlayerTo().addLocation(e);
                    transfer.getPlayerFrom().removeLocation(e);
                }
            });
        }
        
        if(transfer.containsGOJFCard()) {
            
            transfer.getGOJFListTo().forEach(e -> {
                transfer.getPlayerTo().addGOJFCard(e);
                transfer.getPlayerFrom().removeGOJFCard();
            });
        }
        
        if(transfer.containsCash()) {
            transfer.getPlayerTo().addCash(transfer.getCashTo());
        }
        
    }
    
    /**
     * Transfers all items specified to the trade offer initiator.
     */
    private static void transferTradeFrom(TradeOffer transfer) {
        
        if(transfer.containsForLocations()) {
            
            transfer.getForList().forEach(e -> {
                if(e instanceof PropertyLocation) {
                    ((PropertyLocation) e).transferOwnership(transfer.getPlayerFrom());
                    transfer.getPlayerFrom().addLocation(e);
                    transfer.getPlayerTo().removeLocation(e);
                }
                else if(e instanceof UtilityLocation) {
                    ((UtilityLocation) e).setOwner(transfer.getPlayerFrom());
                    transfer.getPlayerFrom().addLocation(e);
                    transfer.getPlayerTo().removeLocation(e);
                }
                else if(e instanceof RailwayLocation) {
                    ((RailwayLocation) e).setOwner(transfer.getPlayerFrom());
                    transfer.getPlayerFrom().addLocation(e);
                    transfer.getPlayerTo().removeLocation(e);
                }
            });
        }
        
        if(transfer.containsGOJFCard()) {
            
            transfer.getGOJFListFrom().forEach(e -> {
                transfer.getPlayerFrom().addGOJFCard(e);
                transfer.getPlayerTo().removeGOJFCard();
            });
        }
        
        if(transfer.containsCash()) {
            transfer.getPlayerFrom().addCash(transfer.getCashFrom());
        }
    }
}
