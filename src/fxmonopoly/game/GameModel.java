/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.bid.Bid;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.decks.cards.*;
import fxmonopoly.gamedata.players.*;
import fxmonopoly.gamedata.trade.TradeOffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Defines the GameModel class, which provides all methods necessary for the 
 * GameController to define the game flow.
 * <p>
 * This includes getters for elements necessary to be displayed in UI, or that have
 * potential to define some UI interactions. Alongside many high level manipulations.
 * @author Sam P. Morrissey
 */
public class GameModel {
    
    private final ArrayList<Player> initialList;
    private final ArrayList<Player> reorderedList;
    
    private HashMap<Player, Integer> dieRolls;
    
    private final GameData data;
    
    private int doublesInARow;
    
    private UserPlayer user;
    
    /**
     * Provides the necessary methods for the GameController to operate on the
     * game. 
     */
    public GameModel() {
        initialList = new ArrayList<>();
        reorderedList = new ArrayList<>();
        data = new GameData();
    }
    
    /**
     * Creates and adds a new UserPlayer instance to the initial list.
     * @param name The name of the player.
     */
    public void createAndAddUser(String name) {
        initialList.add(new UserPlayer(name));
        if(user == null) {
            user = (UserPlayer) initialList.get(initialList.size() - 1);
        }
    }
    
    /**
     * Creates and adds a new CPUPlayer instance to the initial list.
     */
    public void createAndAddCPU() {
        initialList.add(new CPUPlayer("CPU" + getCPUNameStringNumber()));
    }
    
    /**
     * Retrieves a boolean concerning whether the initial list currently contains
     * a User player.
     * @return True if the initial list contains a UserPlayer instance, false otherwise.
     */
    public boolean containsUser() {
        
        boolean user = false;
        for(Player player : initialList) {
            if(player instanceof UserPlayer) {
                user = true;
            }
        }
        
        return user;
    }
    
    /**
     * Retrieves a boolean concerning whether the initial list currently contains
     * a CPU player.
     * @return True if the initial list contains a CPUPlayer instance, false otherwise.
     */
    public boolean containsCPU() {
        
        boolean cpu = false;
        for(Player player : initialList) {
            if(player instanceof CPUPlayer) {
                cpu = true;
            }
        }
        
        return cpu;
    }
    
    /**
     * Retrieves the initial list of players.
     * @return The initial list.
     */
    public ArrayList<Player> getInitialList() {
        return initialList;
    }
    
    /**
     * Retrieves the reordered list of players.
     * @return The reordered list.
     */
    public ArrayList<Player> getReorderedList() {
        return reorderedList;
    }
    
    /**
     * Increments the CPUPlayer name string so that no two have the same name String.
     * @return The next int.
     */
    private int getCPUNameStringNumber() {
        int i = 1;
        
        for(Player player : initialList) {
            if(player instanceof CPUPlayer)
                i++;
        }
        
        return i;
    }
    
    /**
     * Automatically rolls the die for all CPUPlayers in the player list, and maps
     * them alongside the user and user roll value.
     * @param userRoll The user die roll value.
     */
    public void reorderingList(int userRoll) {
        
        dieRolls = new HashMap<>();
                
        initialList.forEach((player) -> {
            if(player instanceof UserPlayer) {
                dieRolls.put(player, userRoll);
            }
            else if(player instanceof CPUPlayer) {
                dieRolls.put(player, data.getDie().rollFirstDie() + data.getDie().rollSecondDie());
            }
        });
        
        playerOrderDecision(initialList);
        data.setPlayerList(reorderedList);
    }
    
    /**
     * Determines the player order, by checking the first die rolls, then calling
     * recursiveDecision until the order is fully resolved.
     * @param array The array to be operated on.
     */
    private void playerOrderDecision(ArrayList<Player> array) {
        
        ArrayList<ArrayList<Player>> collections = new ArrayList<>();
        
        while(!array.isEmpty()) {
            
            int i = Collections.max(dieRolls.values());
            
            ArrayList<Player> temp = new ArrayList<>();
            Iterator<Player> it = array.iterator();
        
            while(it.hasNext()) {
                
                Player player = it.next();
                
                if(dieRolls.get(player) == i) {
                    temp.add(player);
                    dieRolls.remove(player);
                    it.remove();
                }
                
            }
            collections.add(temp);
        }
        
        
        for(ArrayList<Player> secondary : collections) {
            
            if(secondary.size() == 1) 
                reorderedList.add(secondary.get(0));
            else if(secondary.size() > 1) 
                reorderedList.addAll(recursiveDecision(secondary));
            else 
                break;
                
        } 
    }
    
    /**
     * Recursively calls itself until a player tie has been fully resolved, then
     * returns, causing a cascade effect.
     * @param array The list to perform the operation on.
     * @return      The sorted list.
     */
    private ArrayList<Player> recursiveDecision(ArrayList<Player> array) {
        
        HashMap<Player, Integer> map = new HashMap<>();
        ArrayList<ArrayList<Player>> collections = new ArrayList<>();
        
        for(Player player : array) {
            map.put(player, data.getDie().rollFirstDie() + data.getDie().rollSecondDie());
        }
        
        while(!array.isEmpty()) {
            
            int i = Collections.max(map.values());
            
            ArrayList<Player> temp = new ArrayList<>();
            Iterator<Player> it = array.iterator();
        
            while(it.hasNext()) {
                Player player = it.next();
                
                if(map.get(player) == i) {
                    temp.add(player);
                    map.remove(player);
                    it.remove();
                }
                
            }
            collections.add(temp);
        }
        
        ArrayList<Player> order = new ArrayList<>();
        
        for(ArrayList<Player> secondary : collections) {
            
            if(secondary.size() == 1)
                order.add(secondary.get(0));
            else if(secondary.size() > 1) 
                order.addAll(recursiveDecision(secondary));
            else 
                break;
            
        }
        return order;
    }
    
    /**
     * Shifts to the next player, and resets the doubles in a row value.
     */
    public void nextPlayer() {
        data.nextPlayer();
        doublesInARow = 0;
        
        data.getActivePlayer().setCanRoll(true);
    }
    
    /**
     * Retrieves the Location object associated with the player's board position
     * int value.
     * @return The active player's current location.
     */
    public Location getActivePlayerLocation() {
        return data.getBoard().getLocation(data.getActivePlayer().getPosition());
    }
    
    /**
     * Retrieves the current board position of the active player.
     * @return The active player's board position.
     */
    public int getActivePlayerPosition() {
        return data.getActivePlayer().getPosition();
    }
    
    /**
     * Retrieves the value of the active player's cash.
     * @return The active player's cash.
     */
    public int getActivePlayerCash() {
        return data.getActivePlayer().getCash();
    }
    
    /**
     * Retrieves the active player's name.
     * @return The active player's name.
     */
    public String getActivePlayerName() {
        return data.getActivePlayer().getName();
    }
    
    /**
     * Retrieves the active player's jail status.
     * @return True if in jail, false otherwise.
     */
    public boolean getActivePlayerJailStatus() {
        return data.getActivePlayer().isInJail();
    }
    
    /**
     * Removes £50 from the active player, and changes their in Jail status to
     * false.
     */
    public void activePlayerPayToExitJail() {
        data.getActivePlayer().addCash(-50);
        data.getActivePlayer().exitJail();
    }
    
    /**
     * Retrieves whether the owner has a GOJFCard or not.
     * @return 
     */
    public boolean getActivePlayerHasGOJFCard() {
        return data.getActivePlayer().hasGOJFCard();
    }
    
    /**
     * Retrieves the first card in the active player GOJFCard list, provided that
     * the list actually has a card in.
     */
    public void useActivePlayerGOJFCard() {
        if(data.getActivePlayer().hasGOJFCard() && data.getActivePlayer().isInJail()) {
            data.getActivePlayer().exitJail();
            // Does not matter which deck it is returned to, if the specified
            // one already has a GOJFCard, it is returned to the other instead.
            data.returnChanceCard(data.getActivePlayer().removeGOJFCard());
        } 
    }
    
    /**
     * Retrieves the active player's can roll die status. 
     * @return True if can roll, false otherwise.
     */
    public boolean getActivePlayerDieRollStatus() {
        return data.getActivePlayer().getCanRoll();
    }
    
    public int[] rollDieAndMove() {
        int[] i = new int[2];
        
        if(data.getActivePlayer().getCanRoll()) {
            i[0] = data.getDie().rollFirstDie();
            i[1] = data.getDie().rollSecondDie();
        
        
            if(i[0] == i[1] && doublesInARow == 2) {
                data.getActivePlayer().moveTo(10);
                data.getActivePlayer().enterJail();
                data.getActivePlayer().setCanRoll(false);
            }
            else if(i[0] == i[1] && !data.getActivePlayer().isInJail()) {
                doublesInARow++;
                data.getActivePlayer().setCanRoll(true);
            }
            else if(i[0] == i[1] && data.getActivePlayer().isInJail()) {
                data.getActivePlayer().setCanRoll(false);
                data.getActivePlayer().exitJail();
                data.getActivePlayer().moveBy(i[0] + i[1]);
            }
            else if(i[0] != i[1] && !data.getActivePlayer().isInJail()) {
                data.getActivePlayer().setCanRoll(false);
                data.getActivePlayer().moveBy(i[0] + i[1]);
            }
            else {
                data.getActivePlayer().setCanRoll(false);
            }
        }
        
        return i;
    }
    
    public int[] rollDie() {
        int[] i = new int[2];
        
        i[0] = data.getDie().rollFirstDie();
        i[1] = data.getDie().rollSecondDie();
        
        return i;
    }
    
    /**
     * Retrieves the cash of the player instance set as the user player.
     * @return 
     */
    public int getUserPlayerCash() {
        return user.getCash();
    }
    
    /**
     * Retrieves the name of the player instance set as the user player.
     * @return 
     */
    public String getUserPlayerName() {
        return user.getName();
    }
    
    /**
     * Retrieves the comparison regarding the User being the active player.
     * @return True if the user is the active player, false otherwise.
     */
    public boolean userIsActive() {
        if(user == null || data.getActivePlayer() == null) {
            return false;
        }
        else {
            return user == data.getActivePlayer();
        }
    }
    
    /**
     * Retrieves the location matching the specified board position.
     * @param position The position of the required Location.
     * @return The location at the specified position.
     */
    public Location getLocationByPosition(int position) {
        return data.getBoard().getLocation(position);
    }
    
    /**
     * Transfers the ownership of the current location to the active player, must
     * test for current ownership on the location before utilising this method. 
     * This is necessary at a higher level than the GameModel hence this method 
     * does not check.
     */
    public void activePlayerBuyLocation() {
        
        Location location = data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location instanceof PropertyLocation || location instanceof UtilityLocation ||
           location instanceof RailwayLocation) {
            
            if(location instanceof PropertyLocation) {
                if(data.getActivePlayer().getCash() > ((PropertyLocation) location).getPrice()) {
                    data.getActivePlayer().addCash(((PropertyLocation) location).getPrice());
                    ((PropertyLocation) location).transferOwnership(data.getActivePlayer());
                    data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup((PropertyLocation) location));
                    data.getActivePlayer().addLocation(location);
                }
            }
            else if(location instanceof UtilityLocation) {
                if(data.getActivePlayer().getCash() > ((UtilityLocation) location).getPrice()) {
                    data.getActivePlayer().addCash(((UtilityLocation) location).getPrice());
                    ((UtilityLocation) location).setOwner(data.getActivePlayer());
                    data.getActivePlayer().addLocation(location);
                }
            }
            else if(location instanceof RailwayLocation) {
                if(data.getActivePlayer().getCash() > ((RailwayLocation) location).getPrice()) {
                    data.getActivePlayer().addCash(((RailwayLocation) location).getPrice());
                    ((RailwayLocation) location).setOwner(data.getActivePlayer());
                    data.getActivePlayer().addLocation(location);
                }
            }
        } 
    }
    
    /**
     * Develops the specified property provided that the user is the owner. If
     * either the user doesn't own the property or the property/none of its colour
     * group can be developed then this will return 0 and have no effect on 
     * development.
     * @param property The property to develop
     * @return The cost of the development.
     */
    public int userDevelopProperty(PropertyLocation property) {
        
        if(property.getOwner() == user) {
            int cost = data.getBoard().evenlyDevelop(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            user.addCash(-cost);
            return cost;
        }
        
        return 0;
    }
    
    /**
     * Regresses the specified property provided that the user is the owner. If
     * either the user doesn't own the property or the property/none of its colour
     * group can be regressed then this will return 0 and have no effect on 
     * development.
     * @param property The property to regress.
     * @return The value of the reimbursement.
     */
    public int userRegressProperty(PropertyLocation property) {
        
        if(property.getOwner() == user) {
            int reimbursement = data.getBoard().evenlyReduce(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            user.addCash(reimbursement);
            return reimbursement;
        }
        
        return 0;
    }
    
    /**
     * Develops the specified property provided that the active player is the owner.
     * If either the active player doesn't own this property or the property/none
     * of its colour group can be developed then this will return 0 and have no 
     * effect on development.
     * @param property The property to develop.
     * @return The cost of the development.
     */
    public int activePlayerDevelopProperty(PropertyLocation property) {
        if(property.getOwner() == data.getActivePlayer()) {
            int cost = data.getBoard().evenlyDevelop(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            data.getActivePlayer().addCash(-cost);
            return cost;
        }
        
        return 0;
    }
    
    /**
     * Regresses the specified property provided that the active player is the owner.
     * If either the active player doesn't own this property of the property/none
     * of its colour group can be regressed then this will return 0 and have no
     * effect on development.
     * @param property The property to regress.
     * @return The value of the reimbursement.
     */
    public int activePlayerRegressProperty(PropertyLocation property) {
        if(property.getOwner() == data.getActivePlayer()) {
            int reimburse = data.getBoard().evenlyReduce(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            data.getActivePlayer().addCash(reimburse);
            return reimburse;
        }
        
        return 0;
    }
    
    /**
     * Retrieves whether the user owns a specified location.
     * @param location The location to check.
     * @return True if the user does own the location, false otherwise.
     */
    public boolean userOwnsSpecificLocation(Location location) {
        if(location instanceof PropertyLocation) {
            return ((PropertyLocation) location).getOwner() == user;
        }
        else if(location instanceof RailwayLocation) {
            return ((RailwayLocation) location).getOwner() == user;
        }
        else if(location instanceof UtilityLocation) {
            return ((UtilityLocation) location).getOwner() == user;
        }
        else {
            return false;
        }
    }
    
    /**
     * Retrieves the location at the specified position.
     * @param position The position of the location.
     * @return The location at the specified position, or null if out of bounds.
     */
    public Location retrieveLocation(int position) {
        return data.getBoard().getLocation(position);
    }
    
    /**
     * Retrieves the current board position of the user.
     * @return The position of the user.
     */
    public int getUserPosition() {
        return user.getPosition();
    }
    
    /**
     * Retrieves the colour group containing the specified property.
     * @param property The property in the group.
     * @return The group of properties containing the property.
     */
    public ArrayList<PropertyLocation> getColourGroup(PropertyLocation property) {
        return data.getBoard().getGroup(property);
    }
    
    /**
     * Retrieves the user player associated with this game.
     * @return The user player instance.
     */
    public UserPlayer getUser() {
        return user;
    }
    
    /**
     * Retrieves the currently active player instance.
     * @return The active player instance.
     */
    public Player getActivePlayer() {
        return data.getActivePlayer();
    }
    
    /**
     * Retrieves the active player bankruptcy status.
     * @return True if the player is certified bankrupt, false otherwise.
     */
    public boolean isActivePlayerBankrupt() {
        return data.getActivePlayer().getOwnedLocations().isEmpty() &&
               data.getActivePlayer().getCash() < 0 &&
               !data.getActivePlayer().hasGOJFCard();
    }
    
    /**
     * Resolves unpayable rent amounts, when the active player has become bankrupt,
     * and sales of property/GOJFCards cannot cover the disparity. Only checks for
     * location 
     */
    public void resolveExtraBalance() {
        if(getLocationByPosition(data.getActivePlayer().getPosition()) instanceof PropertyLocation) {
           PropertyLocation property = (PropertyLocation) getLocationByPosition(data.getActivePlayer().getPosition());
           property.getOwner().addCash(data.getActivePlayer().getCash());
        }
        else if(getLocationByPosition(data.getActivePlayer().getPosition()) instanceof UtilityLocation) {
           UtilityLocation utility = (UtilityLocation) getLocationByPosition(data.getActivePlayer().getPosition());
           utility.getOwner().addCash(data.getActivePlayer().getCash());
        }
        else if(getLocationByPosition(data.getActivePlayer().getPosition()) instanceof RailwayLocation) {
            RailwayLocation railway = (RailwayLocation) getLocationByPosition(data.getActivePlayer().getPosition());
            railway.getOwner().addCash(data.getActivePlayer().getCash());
        }
    }
    
    /**
     * Removes the active player from the game, only if they are confirmed bankrupt,
     * otherwise this method does nothing. Also continues on to the next player, 
     * if the player list contains more than a single player.
     */
    public void removeActivePlayerFromGame() {
        if(isActivePlayerBankrupt()) {
            data.removePlayer(data.getActivePlayer());
            
            if(data.getPlayerList().size() > 1) {
                nextPlayer();
            } 
        }  
    }
    
    /**
     * Initialises an active trade instance.
     * @param playerfrom The player initiating the trade.
     */
    public void startTrade(Player playerfrom) {
        data.newTrade(playerfrom);
    }
    
    /**
     * Retrieves the active trade instance for modification.
     * @return The active trade instance, or null if no trade has been initiated.
     */
    public TradeOffer getActiveTrade() {
        return data.getActiveTrade();
    }
    
    /**
     * Fully resolves the active trade instance between two players.
     */
    public void resolveActiveTrade() {
        
        if(data.getActiveTrade().hasPlayerTo()) {
            
            transferTradeTo();
            transferTradeFrom();
        }
        
        data.clearActiveTrade();
    }
    
    /**
     * Transfers all items specified to the trade offer recipient.
     */
    private void transferTradeTo() {
        
        TradeOffer transfer = data.getActiveTrade();
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
    private void transferTradeFrom() {
        
        TradeOffer transfer = data.getActiveTrade();
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
    
    /**
     * Creates a new active bid instance.
     */
    public void startBid() {
        data.newBid();
    }
    
    /**
     * Retrieves the active bid instance.
     * @return 
     */
    public Bid getActiveBid() {
        return data.getActiveBid();
    }
    
    public void resolveBid() {
        
        
        if(data.getActiveBid().getHighestBidder().size() == 1) {
            data.getActiveBid().getHighestBidder().get(0).addCash(-data.getActiveBid().getHighestBid());
            
            processBidTransfer(data.getActiveBid().getHighestBidder().get(0));
        }
        else if(data.getActiveBid().getHighestBidder().size() > 1) {
            
            
            Player highestBidder = null;
            
            for(Player player : data.getActiveBid().getHighestBidder()) {
                if(player instanceof UserPlayer) {
                    highestBidder = player;
                    break;
                }
            }
            
            if(highestBidder == null) {
                highestBidder = data.getActiveBid().getHighestBidder().get(0);
            }
            
     
            highestBidder.addCash(-data.getActiveBid().getHighestBid());
            
            processBidTransfer(highestBidder);
        }    
        
    }
    
    private void processBidTransfer(Player player) {
        
        if(data.getActiveBid().containsGOJFCard()) {
            for(Player temp : data.getPlayerList()) {
                if(temp.getGOJFCard() == data.getActiveBid().getGOJFCard()) {
                    temp.removeGOJFCard();
                }
            }
            
            player.addGOJFCard(data.getActiveBid().getGOJFCard());
        }
        else if(data.getActiveBid().containsLocation()) {
            Location location = data.getActiveBid().getLocation();
            
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
    
    public void processCardActions() {
        
        Card card = data.getActiveCard();
        
        if(card instanceof DoublePayableCard) {
            int balance = 0;
            DoublePayableCard temp = ((DoublePayableCard) card);
            
            for(PropertyLocation location : data.getActivePlayer().getOwnedProperty()) {
                if(location.getIsHotel()) {
                    balance += temp.getSecondValue();
                }
                else {
                    balance += (temp.getFirstValue() * location.getNumberOfHouses());
                }
            }
            
            data.getActivePlayer().addCash(-balance);
        }
        else if(card instanceof GOJFCard) {
            data.getActivePlayer().addGOJFCard((GOJFCard)card);
        }
        else if(card instanceof MoveByCard) {
            data.getActivePlayer().moveBy(((MoveByCard) card).getDistance());
        }
        else if(card instanceof MoveToCard) {
            if(card.getDescription().contains("Go to Jail")) {
                data.getActivePlayer().moveTo(((MoveToCard) card).getMoveLocation());
                data.getActivePlayer().enterJail();
            }
            data.getActivePlayer().moveTo(((MoveToCard) card).getMoveLocation());
        }
        else if(card instanceof NearestRailwayCard) {
            while(!(data.getBoard().getLocation(data.getActivePlayer().getPosition()) instanceof RailwayLocation)) {
                data.getActivePlayer().moveBy(1);
            }
            
            
        }
        else if(card instanceof NearestUtilityCard) {
            while(!(data.getBoard().getLocation(data.getActivePlayer().getPosition()) instanceof UtilityLocation)) {
                data.getActivePlayer().moveBy(1);
            }
            
            
        }
        else if(card instanceof PayableCard) {
            PayableCard temp = (PayableCard) card;
            
            if(temp.getPerPlayer()) {
                
                data.getPlayerList().forEach(e -> {
                    if(!(e == data.getActivePlayer())) {
                        e.addCash(-temp.getValue());
                        data.getActivePlayer().addCash(temp.getValue());
                    }
                });
                
            }
            else {
                data.getActivePlayer().addCash(temp.getValue());
            }
        }
        
    }
    
    public void processRequiredPositionAction() {
        
        Location location = data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location instanceof ChanceLocation) {
            data.drawNextChanceCard();
        }
        else if(location instanceof CommunityChestLocation) {
            data.drawNextCommunityChestCard();
        }
        else if(location instanceof FreeParkingLocation) {
            
        }
        else if(location instanceof GoLocation) {
            data.getActivePlayer().addCash(200);
        }
        else if(location instanceof GoToJailLocation) {
            data.getActivePlayer().moveTo(((GoToJailLocation) location).getJailPosition());
            data.getActivePlayer().enterJail();
        }
        else if(location instanceof JailLocation) {
            
        }
        else if(location instanceof PropertyLocation) {
            
            PropertyLocation temp = (PropertyLocation) location;
            
            if(temp.getIsOwned() && !(temp.getMortgagedStatus()) && temp.getOwner() != data.getActivePlayer()) {
                data.getActivePlayer().addCash(-temp.getRent());
                temp.getOwner().addCash(temp.getRent());
            }
            
        }
        else if(location instanceof RailwayLocation) {
            
            RailwayLocation temp = (RailwayLocation) location;
            
            if(temp.getIsOwned() && !(temp.getIsMortgaged())) {
                if(data.getActiveCard() instanceof NearestRailwayCard) {
                    int rent = (temp.getRentMultiplier(temp.getOwner().getOwnedRailways().size())) * 2;
                    data.getActivePlayer().addCash(-rent);
                    temp.getOwner().addCash(rent);
                }
                else {
                    int rent = (temp.getRentMultiplier(temp.getOwner().getOwnedRailways().size()));
                    data.getActivePlayer().addCash(-rent);
                    temp.getOwner().addCash(rent);
                }
            }
        }
        else if(location instanceof TaxLocation) {
            data.getActivePlayer().addCash(-((TaxLocation) location).getValue());
        }
        else if(location instanceof UtilityLocation) {
            
            UtilityLocation temp = (UtilityLocation) location;
            
            int multiplier = 0;
            
            if(temp.getIsOwned() && !(temp.getIsMortgaged())) {
                if(temp.getOwner().getOwnedUtilities().size() == 1 && !(data.getActiveCard() instanceof NearestUtilityCard)) {
                    multiplier = temp.getSingleMultiplier();
                }
                else {
                    multiplier = temp.getDoubleMultiplier();
                }
            }
            
            int rent = (multiplier * data.getDie().dieRollTotal());
            data.getActivePlayer().addCash(-rent);
            temp.getOwner().addCash(rent);
        } 
    }
}
