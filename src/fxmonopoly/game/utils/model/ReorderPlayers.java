/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.players.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Provides a single public method to reorder the player list.
 * <p>
 * All methods are static since there is no reason for this class to have any
 * state as it is a function, not a part of the game.
 * @author Sam P. Morrissey
 */
public final class ReorderPlayers {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private ReorderPlayers() {}
    
    /**
     * Automatically rolls the die for all CPUPlayers in the player list, and maps
     * them alongside the user and user roll value.
     * @param model The model to utilise.
     * @param userRoll The user die roll value.
     */
    public static void reorderList(GameModel model, int userRoll) {
        
        HashMap<Player, Integer> dieRolls = new HashMap<>();
                
        model.getInitialList().forEach((player) -> {
            if(player instanceof UserPlayer) {
                dieRolls.put(player, userRoll);
            }
            else if(player instanceof CPUPlayer) {
                int[] die = model.rollDie();
                dieRolls.put(player, die[0] + die[1]);
            }
        });
        
        playerOrderDecision(model, dieRolls, model.getInitialList());
    }
    
    /**
     * Determines the player order, by checking the first die rolls, then calling
     * recursiveDecision until the order is fully resolved.
     * @param model The model to act upon.
     * @param dieRolls The map of die rolls.
     * @param array The array to be operated on.
     */
    private static void playerOrderDecision(GameModel model, HashMap<Player, Integer> dieRolls, ArrayList<Player> array) {
        
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
                model.getReorderedList().add(secondary.get(0));
            else if(secondary.size() > 1) 
                model.getReorderedList().addAll(recursiveDecision(model, secondary));
            else 
                break;
                
        } 
    }
    
    /**
     * Recursively calls itself until a player tie has been fully resolved, then
     * returns, causing a cascade effect.
     * @param model The model to retrieve the die from.
     * @param array The list to perform the operation on.
     * @return      The sorted list.
     */
    private static ArrayList<Player> recursiveDecision(GameModel model, ArrayList<Player> array) {
        
        HashMap<Player, Integer> map = new HashMap<>();
        ArrayList<ArrayList<Player>> collections = new ArrayList<>();
        
        for(Player player : array) {
            int[] die = model.rollDie();
            map.put(player, die[0] + die[1]);
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
                order.addAll(recursiveDecision(model, secondary));
            else 
                break;
            
        }
        return order;
    }
}
