/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.game.GameModel;
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
public final class ProcessTrade {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private ProcessTrade() {}
    
    /**
     * Fully resolves the active trade instance between two players.
     * @param transfer The trade to transfer.
     * @param model The model to utilise.
     */
    public static void resolveTrade(TradeOffer transfer, GameModel model) {

            transferTradeTo(transfer, model);
            transferTradeFrom(transfer, model);
    }
    
    /**
     * Transfers all items specified to the trade offer recipient.
     */
    private static void transferTradeTo(TradeOffer transfer, GameModel model) {
        
        if(transfer.containsOfferLocations()) {
            
            transfer.getOfferList().forEach(e -> {
                if(e instanceof PropertyLocation) {
                    if(((PropertyLocation) e).getIsHotel() || ((PropertyLocation) e).getNumberOfHouses() > 0) {
                        ((PropertyLocation) e).getOwner().addCash(model.removeAllDevelopmentFromGroup((PropertyLocation) e));
                    }
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
    private static void transferTradeFrom(TradeOffer transfer, GameModel model) {
        
        if(transfer.containsForLocations()) {
            
            transfer.getForList().forEach(e -> {
                if(e instanceof PropertyLocation) {
                    if(((PropertyLocation) e).getIsHotel() || ((PropertyLocation) e).getNumberOfHouses() > 0) {
                        ((PropertyLocation) e).getOwner().addCash(model.removeAllDevelopmentFromGroup((PropertyLocation) e));
                    }
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
