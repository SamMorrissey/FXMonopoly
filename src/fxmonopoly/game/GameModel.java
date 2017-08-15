/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.players.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
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
        
        boolean user = false;
        for(Player player : initialList) {
            if(player instanceof CPUPlayer) {
                user = true;
            }
        }
        
        return user;
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
        if(data.getActivePlayer().hasGOJFCard()) {
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
        return user == data.getActivePlayer();
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
    
    public void sellHouses() {
        
    }
}
