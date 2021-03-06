/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.game.utils.model.*;
import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.bid.Bid;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.board.utils.EvenDevelopment;
import fxmonopoly.gamedata.decks.cards.Card;
import fxmonopoly.gamedata.players.*;
import fxmonopoly.gamedata.trade.TradeOffer;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Defines the GameModel class, which provides all methods necessary for the 
 * GameController to define the game flow.
 * <p>
 * Provides the necessary methods for retrieving and manipulating the GameData,
 * whilst aiming to avoid overexposure of the internal representation.
 * @author Sam P. Morrissey
 */
public class GameModel {
    
    private final ArrayList<Player> initialList;
    private final ArrayList<Player> reorderedList;
    
    private final GameData data;
    
    private SimpleStringProperty activePlayerName;
    private SimpleIntegerProperty activePlayerCash;
    private SimpleStringProperty activePlayerLocationName;
    
    private SimpleBooleanProperty userIsActive;
    private SimpleIntegerProperty playerListSize;
    
    private GameController controller;
    
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
     * Sets the GameController of this GameModel
     * @param controller The controller to set.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    /**
     * Creates and adds a new UserPlayer instance to the initial list.
     * @param name The name of the player.
     */
    public void createAndAddUser(String name) {
        initialList.add(new UserPlayer(name));
        if(data.getUserPlayer() == null) {
            data.setUserPlayer((UserPlayer) initialList.get(initialList.size() - 1));
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
     * Retrieves the full list of players.
     * @return The list of players.
     */
    public ArrayList<Player> getPlayerList() {
        return data.getPlayerList();
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
     * them alongside the user and user roll value. Utilises the ReorderPlayers
     * util class.
     * @param userRoll The user die roll value.
     */
    public void reorderList(int userRoll) {
        
        ReorderPlayers.reorderList(this, userRoll);
        data.setPlayerList(reorderedList);
        
        activePlayerName = new SimpleStringProperty(this, "activePlayerName", data.getActivePlayer().getName());
        activePlayerCash = new SimpleIntegerProperty(this, "activePlayerCash", data.getActivePlayer().getCash());
        activePlayerLocationName = new SimpleStringProperty(this, "activePlayerLocation", retrieveLocation(getActivePlayer().getPosition()).getName());
        
        userIsActive = new SimpleBooleanProperty(this, "userIsActive", data.getUserPlayer() == data.getActivePlayer());
        playerListSize = new SimpleIntegerProperty(this, "playerListSize", data.getPlayerList().size());
        
        PlayerBindings.generateBindings(this);
    }
    
    /**
     * Shifts to the next player, and resets the doubles in a row value.
     */
    public void nextPlayer() {
        PlayerBindings.clearBindings(this);
        
        if(data.getActivePlayer().isInJail()) {
            data.getActivePlayer().anotherTurnInJail();
        }
        
        data.nextPlayer();
        PlayerBindings.generateBindings(this);
    }
    
    /**
     * Retrieves the user player associated with this game.
     * @return The user player instance.
     */
    public UserPlayer getUser() {
        return data.getUserPlayer();
    }
    
    /**
     * Retrieves the user player is active property.
     * @return The user player is active property.
     */
    public SimpleBooleanProperty getUserIsActiveProperty() {
        return userIsActive;
    }
    
    /**
     * Retrieves the currently active player instance.
     * @return The active player instance.
     */
    public Player getActivePlayer() {
        return data.getActivePlayer();
    }
    
    /**
     * Retrieves the active player Property.
     * @return The active player Property.
     */
    public SimpleObjectProperty<Player> getActivePlayerProperty() {
        return data.getActivePlayerProperty();
    }
    
    /**
     * Retrieves the active player cash Property.
     * @return The cash Property.
     */
    public SimpleIntegerProperty getActivePlayerCashProperty() {
        return activePlayerCash;
    }
    
    /**
     * Retrieves the active player name Property.
     * @return The name Property.
     */
    public SimpleStringProperty getActivePlayerNameProperty() {
        return activePlayerName;
    }
    
    /**
     * Retrieves the active player location name Property.
     * @return The location name Property.
     */
    public SimpleStringProperty getActivePlayerLocationNameProperty() {
        return activePlayerLocationName;
    }

    /**
     * Retrieves the player list size Property.
     * @return The player list size property.
     */
    public SimpleIntegerProperty getPlayerListSizeProperty() {
        return playerListSize;
    }
    
    /**
     * Retrieves the comparison regarding the User being the active player.
     * @return True if the user is the active player, false otherwise.
     */
    public boolean userIsActive() {
        if(data.getUserPlayer() == null || data.getActivePlayer() == null) {
            return false;
        }
        else {
            return data.getUserPlayer() == data.getActivePlayer();
        }
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
     * Rolls the die and retrieves the die values.
     * @return The int array of die values.
     */
    public int[] rollDie() {
        return RollDie.rollDie(data.getDie());
    }
    
    /**
     * Rolls the die and moves the player the corresponding distance.
     * @param diceRolls The values of the dice utilised.
     */
    public void diceMove(int[] diceRolls) {
        RollDie.diceMove(controller, this, data, diceRolls);
    }
    
    /**
     * Transfers the ownership of the current location to the active player, must
     * test for current ownership on the location before utilising this method. 
     */
    public void activePlayerBuyLocation() {
        OwnableLocations.activePlayerBuyLocation(controller, data);
    }
    
    /**
     * Develops the specified property provided that the user is the owner.
     * @param property The property to develop
     * @return The cost of the development.
     */
    public int userDevelopProperty(PropertyLocation property) {
        return OwnableLocations.userDevelopProperty(data, property);
    }
    
    /**
     * Regresses the specified property provided that the user is the owner.
     * @param property The property to regress.
     * @return The cost of the development.
     */
    public int userRegressProperty(PropertyLocation property) {
        return OwnableLocations.userRegressProperty(data, property);
    }
    
    /**
     * Develops the specified property provided that the specified player is the owner.
     * @param property The property to develop
     * @param player The player to develop the property.
     * @return The cost of the development.
     */
    public int specifiedPlayerDevelopProperty(PropertyLocation property, Player player) {
        return OwnableLocations.specifiedPlayerDevelopProperty(data, player, property);
    }
    
    /**
     * Regresses the specified property provided that the specified player is the owner.
     * @param property The property to regress.
     * @param player The player to regress the property.
     * @return The value of the reimbursement.
     */
    public int specifiedPlayerRegressProperty(PropertyLocation property, Player player) {
        return OwnableLocations.specifiedPlayerRegressProperty(data, player, property);
    }
    
    /**
     * Develops the specified property provided that the active player is the owner.
     * @param property The property to develop.
     * @return The cost of the development.
     */
    public int activePlayerDevelopProperty(PropertyLocation property) {
        return OwnableLocations.activePlayerDevelopProperty(data, property);
    }
    
    /**
     * Regresses the specified property provided that the active player is the owner.
     * @param property The property to regress.
     * @return The value of the reimbursement.
     */
    public int activePlayerRegressProperty(PropertyLocation property) {
        return OwnableLocations.activePlayerRegressProperty(data, property);
    }
    
    /**
     * Removes all development from a property group and provides a reimbursement
     * value.
     * @param property The property of the group to reduce.
     * @return The value of reimbursement.
     */
    public int removeAllDevelopmentFromGroup(PropertyLocation property) {
        return EvenDevelopment.removeAllDevelopmentInColourGroup(data.getBoard(), property);
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
     * Retrieves the position of the specified location.
     * @param location The location to determine the position of.
     * @return The board position of the specified location.
     */
    public int retrieveLocationPosition(Location location) {
        return data.getBoard().getLocationPosition(location);
    }
 
    /**
     * Retrieves the colour group containing the specified property.
     * @param property The property in the group.
     * @return         The group of properties containing the property.
     */
    public ArrayList<PropertyLocation> getColourGroup(PropertyLocation property) {
        return data.getBoard().getGroup(property);
    }
    
    /**
     * Determines whether the location provided is an ownable location or not.
     * @param location The location to check.
     * @return         True if ownable, false otherwise.
     */
    public boolean locationIsOwnable(Location location) {
        return (location instanceof PropertyLocation || 
                location instanceof RailwayLocation || 
                location instanceof UtilityLocation);   
    }
    
    /**
     * Retrieves the ownership status of the specified location.
     * @param location The location to check.
     * @return True if owned, false otherwise, or if not an ownable location.
     */
    public boolean locationIsOwned(Location location) {
        if(location instanceof PropertyLocation) {
            return ((PropertyLocation) location).getIsOwned();
        }
        else if(location instanceof RailwayLocation) {
            return ((RailwayLocation) location).getIsOwned();
        }
        else if(location instanceof UtilityLocation) {
            return ((UtilityLocation) location).getIsOwned();
        }
        else {
            return false;
        }
    }
    
    /**
     * Mortgages the specified location if possible to do so.
     * @param location The location to mortgage.
     */
    public void mortgageLocation(Location location) {
        OwnableLocations.mortgageLocation(controller, location);
    }
    
    /**
     * Demortgages the specified location if possible to do so.
     * @param location The location to demortgage.
     */
    public void deMortgageLocation(Location location) {
        OwnableLocations.demortgageLocation(controller, location);
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
     * locations that are 
     */
    public void resolveExtraBalance() {
        if(retrieveLocation(data.getActivePlayer().getPosition()) instanceof PropertyLocation) {
           PropertyLocation property = (PropertyLocation) retrieveLocation(data.getActivePlayer().getPosition());
           property.getOwner().addCash(data.getActivePlayer().getCash());
        }
        else if(retrieveLocation(data.getActivePlayer().getPosition()) instanceof UtilityLocation) {
           UtilityLocation utility = (UtilityLocation) retrieveLocation(data.getActivePlayer().getPosition());
           utility.getOwner().addCash(data.getActivePlayer().getCash());
        }
        else if(retrieveLocation(data.getActivePlayer().getPosition()) instanceof RailwayLocation) {
            RailwayLocation railway = (RailwayLocation) retrieveLocation(data.getActivePlayer().getPosition());
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
            playerListSize.setValue(data.getPlayerList().size());
            
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
            ProcessTrade.resolveTrade(data.getActiveTrade(), this);
            data.clearActiveTrade();
        }   
    }
    
    /**
     * Voids the active trade instance.
     */
    public void cancelActiveTrade() {
        data.clearActiveTrade();
    }
    
    /**
     * Initialises a new active bid instance.
     */
    public void startBid() {
        data.newBid();
    }
    
    /**
     * Retrieves the active bid instance for modification.
     * @return The active bid instance, or null if no trade has been initiated.
     */
    public Bid getActiveBid() {
        return data.getActiveBid();
    }
    
    /**
     * Fully resolves the active bid instance.
     */
    public void resolveActiveBid() {
        if(data.getActiveBid().containsBid()) {
            ProcessBid.resolveBid(controller, data.getActiveBid(), data.getPlayerList());
            data.clearActiveBid();
        }
    }
    
    /**
     * Voids the active bid instance.
     */
    public void clearActiveBid() {
        data.clearActiveBid();
    }
    
    /**
     * Retrieves the active card instance.
     * @return Retrieves the active card.
     */
    public Card getActiveCard() {
        return data.getActiveCard();
    }
    
    /**
     * Processes the card actions provided that there is currently an active card.
     */
    public void processCardActions() {
        
        if(data.getActiveCard() != null) {
            ProcessCard.processCardActions(controller, this, data);
            
            if(data.getActiveCard().getFromChanceDeck()) {
                data.returnChanceCard(data.getActiveCard());
            }
            else {
                data.returnCommunityChestCard(data.getActiveCard());
            }
        }
    }
    
    /**
     * Processes the action required at the specified position. For example rent,
     * fines, jail movement, drawing a card into the active slot etc.
     */
    public void processRequiredPositionAction() {
        ProcessPositionAction.processRequiredPositionAction(controller, this, data);
    }
}
