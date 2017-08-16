/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.utils;

import fxmonopoly.gamedata.board.Board;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import java.util.ArrayList;

/**
 * Defines the methods necessary to ensure uniform distribution of developments
 * both upwards and downwards.
 * <p>
 * This class provides only static methods due to not forming a part of the game, 
 * merely operating on the game and ensuring a consistent state. The testing of 
 * this class forms part of the BoardTest file.
 * @author Sam P. Morrissey.
 */
public class EvenDevelopment {
    
    /**
     * Evenly distributes developments among the properties of the colour group
     * corresponding to the input property. 
     * <p>
     * Naturally this method only develops the specified property if it is either
     * distinctly the least developed, or equal to the least developed. Otherwise
     * it will enact on the least developed property in the group, and if two
     * are equal then the earliest on the board will receive the development.
     * @param board    The board on which to operate.
     * @param property The property to improve.
     * @return         The value of the cost of the improvement.
     */
    public static int evenlyDevelop(Board board, PropertyLocation property) {
        
        int cost = 0;
        
        if(board.getNumberOfHousesLeft() > 0) {
            
            int j = 5;
            for(PropertyLocation temp : board.getGroup(property)) {
                if(temp.getNumberOfHouses() < j && !temp.getIsHotel()) {
                    j = temp.getNumberOfHouses();
                }
            }
            
            if(property.getNumberOfHouses() == j && property.getUpgradeableToHotel()) {
                property.addHouse();
                cost = property.getHousePrice();
                board.addHouses(4);
                board.addHotels(-1);  
            }
            else if(property.getNumberOfHouses() == j && !property.getUpgradeableToHotel() && !property.getIsHotel()) {
                if(property.getDevelopableStatus()) {
                    property.addHouse();
                    cost = property.getHousePrice();
                    board.addHouses(-1);
                }
            }
            else if(property.getNumberOfHouses() != j && property.getIsHotel()) {
                for(PropertyLocation temp : board.getGroup(property)) {
                    if(temp != property && temp.getNumberOfHouses() == j && temp.getDevelopableStatus()) {
                        temp.addHouse();
                        cost = temp.getHousePrice();
                        board.addHouses(4);
                        board.addHotels(-1);
                        break;
                    }
                }
            }
            else if(property.getNumberOfHouses() != j && !property.getIsHotel()) {
                for(PropertyLocation temp : board.getGroup(property)) {
                    if(temp != property && temp.getNumberOfHouses() == j && temp.getDevelopableStatus()) {
                        temp.addHouse();
                        cost = temp.getHousePrice();
                        board.addHouses(-1);
                        break;
                    }
                }
            }
            
        }
        
        return cost;
    }
    
    /**
     * Evenly reduces the developments on the properties of the colour group
     * corresponding to the inputted property. It is necessary to utilise this
     * method to sell houses/hotels because they behave differently to each other
     * and require certain conditions to be met (the larger issue is with hotels).
     * <p>
     * Naturally this method only reduces the specified property, if the specified
     * property is distinctly the most developed, or equal to the most developed.
     * Otherwise another property will be dealt with instead.
     * @param board    The board on which to operate.
     * @param property The property to reduce.
     * @return         The value of the reimbursement.
     */
    public static int evenlyReduce(Board board, PropertyLocation property) {
        
        int reimburse = 0;
        
        if(property.getIsHotel() && board.getNumberOfHousesLeft() < 4) {
            reimburse = reimburseHotelDevelopments(board, property);
        }
        else if(property.getIsHotel() && board.getNumberOfHousesLeft() >= 4) {
            property.removeHouse();
            board.addHouses(-4);
            board.addHotels(1);
            reimburse = property.getHousePrice() / 2;
        }
        else if(property.getNumberOfHouses() == retrieveMostDeveloped(board.getGroup(property)).getNumberOfHouses() &&
                property.getNumberOfHouses() != 0) {
            property.removeHouse();
            board.addHouses(1);
            reimburse = property.getHousePrice() / 2;
        }
        else if(property != retrieveMostDeveloped(board.getGroup(property))) {
            PropertyLocation temp = retrieveMostDeveloped(board.getGroup(property));
            
            if(temp.getIsHotel() && board.getNumberOfHousesLeft() < 4) {
                reimburse = reimburseHotelDevelopments(board, property);
            }
            else if(temp.getIsHotel() && board.getNumberOfHousesLeft() > 4) {
                temp.removeHouse();
                board.addHouses(-4);
                board.addHotels(1);
                reimburse = temp.getHousePrice() / 2;
            }
            else {
                if(temp.getNumberOfHouses() != 0) {
                    temp.removeHouse();
                    board.addHouses(1);
                    reimburse = temp.getHousePrice() / 2;
                }
            }
        }
        
        return reimburse;
        
    }
    
    /**
     * Determines the value of the reimbursement for hotels when they are 
     * being sold. Should only be called when the number of houses available is 
     * less than four, although this method will still work perfectly fine if
     * not. Will return zero if there are no reimbursements to be made, or
     * conditions are not met.
     * @param board The board on which to operate.
     * @param property The property determined for regression.
     * @return         The value of the reimbursement.
     */
    private static int reimburseHotelDevelopments(Board board, PropertyLocation property) {
        
        int reimburse;
        int houseTotal = 0;
        
        ArrayList<PropertyLocation> areHotels = new ArrayList<>();
        ArrayList<PropertyLocation> nonHotels = new ArrayList<>();
        
        for(PropertyLocation temp : board.getGroup(property)) {
            if(temp.getIsHotel()) {
                areHotels.add(temp);
                houseTotal += 5;
            }   
            else {
                nonHotels.add(temp);
                houseTotal += temp.getNumberOfHouses();
            }
        }
        
        if(areHotels.size() == board.getGroup(property).size()) {
            allHotelsRegress(board, areHotels, property);
        }
        else {
            notAllHotelsUndevelop(board, areHotels, nonHotels, property);
        }
        
        int newHouseTotal = 0;
        
        for(PropertyLocation temp : board.getGroup(property)) {
            newHouseTotal += temp.getNumberOfHouses();
        }
        
        reimburse = (houseTotal - newHouseTotal) * (property.getHousePrice() / 2);
        return reimburse;
    }
    
    /**
     * Retrieves the most highly developed property in a specified group.
     * <p>
     * If there are no hotels then the entire group will be iterated through to
     * ensure the value is valid, however if there are two of the same value, then
     * the first is returned. If there is a hotel, then that property will 
     * immediately be returned upon evaluation.
     * @param group The group to find the property in.
     * @return The most highly developed property (or the earliest on the board
     *         if two properties have equal houses, or there is a hotel).
     */
    private static PropertyLocation retrieveMostDeveloped(ArrayList<PropertyLocation> group) {
        PropertyLocation location = group.get(0);
        
        for(PropertyLocation temp : group) {
            if(temp.getNumberOfHouses() > location.getNumberOfHouses() && !temp.getIsHotel())
                location = temp;
            else if(temp.getIsHotel()) {
                location = temp;
                return location;
            }
        }
        
        return location;
    }
    
    /**
     * Calculates and performs the necessary changes to the development status of
     * all properties in the list, and results in a consistent state each time.
     * This method should only be called when all the properties are hotels, and
     * when it is necessary to reimburse hotels due to selling.
     * <p>
     * Both the remainder and the number of houses for each property are calculated
     * based on the currently available houses. Each property then has its number of
     * houses reduced to the specified number.
     * <p>
     * Following this any remainder is added on. If the remainder is 1, then the
     * value is automatically added to the highest price property in the colour
     * group, otherwise the remainder is added uniformly to the properties OTHER
     * than the one specified.
     * @param board The board on which to operate.
     * @param areHotels A list of properties that are hotels.
     * @param property The property initially specified for regression.
     */
    private static void allHotelsRegress(Board board, ArrayList<PropertyLocation> areHotels, PropertyLocation property) {
        int modulo = board.getNumberOfHousesLeft() % areHotels.size();
        int eachProperty = board.getNumberOfHousesLeft() / areHotels.size();
            
        areHotels.forEach((temp) -> {
            while(!(temp.getNumberOfHouses() == eachProperty)) {
                temp.removeHouse();
            }
            board.addHotels(1);
        });
                
        if(modulo != 0) {
            int i;
            
            for(i = 0; i < modulo; i++) {
                if(modulo == 1) {
                    areHotels.get(areHotels.size() - 1).addHouse();
                }
                else {
                    for(PropertyLocation temp : areHotels) {
                        if(temp != property && temp.getNumberOfHouses() <= eachProperty) {
                            temp.addHouse();
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Calculates and performs the necessary changes to the development status of
     * all properties in the list, and results in a consistent state each time.
     * This method should only be called when not all the properties are hotels,
     * and when it is necessary to reimburse hotels due to selling.
     * <p>
     * Takes the most developed from the nonHotels list and reduces it until the 
     * point where the number of houses available divided by the size of the areHotels 
     * list (to spread them evenly) is equal to either 0 or -1. At this point, both
     * the remainder and the houses for each property are calculated based on the
     * number of houses now available after reduction. 
     * <p>
     * Each of the properties with hotels is then reduced to the number of houses
     * specified for each property. Following this any remainders are assigned to
     * the properties OTHER than the one specified. If there is a single house
     * remaining it is automatically assigned to the highest price property in
     * the colour group.
     * @param areHotels A list of properties that are hotels.
     * @param nonHotels A list of properties that are not hotels.
     * @param property The property initially specified for regression.
     */
    private static void notAllHotelsUndevelop(Board board, ArrayList<PropertyLocation> areHotels, ArrayList<PropertyLocation> nonHotels, PropertyLocation property) {
        
        while(!(((board.getNumberOfHousesLeft() / areHotels.size()) - retrieveMostDeveloped(nonHotels).getNumberOfHouses()) == -1) && 
              !(((board.getNumberOfHousesLeft() / areHotels.size()) - retrieveMostDeveloped(nonHotels).getNumberOfHouses()) == 0)) {
            retrieveMostDeveloped(nonHotels).removeHouse();
            board.addHouses(1);
        }
            
        int modulo = board.getNumberOfHousesLeft() % areHotels.size();
        int eachProperty = board.getNumberOfHousesLeft() / areHotels.size();
            
        areHotels.forEach((temp) -> {
            while(!(temp.getNumberOfHouses() == eachProperty)) {
                temp.removeHouse();
            }
            board.addHotels(1);
        });
            
        if(modulo != 0) {
            int i;
            for(i = 0; i < modulo; i++) {
                if(modulo == 1) {
                    areHotels.get(areHotels.size() - 1).addHouse();
                }
                else {
                    for(PropertyLocation temp : areHotels) {
                        if(temp != property && temp.getNumberOfHouses() <= eachProperty) {
                            temp.addHouse();
                            break;
                        }
                    }
                }
            }
        } 
    }
}