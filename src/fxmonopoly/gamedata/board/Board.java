/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board;

import fxmonopoly.gamedata.board.locations.*;
import java.util.ArrayList;

/**
 * Defines the board class.
 * @author Sam P. Morrissey
 */
public class Board {
    
    private final ArrayList<Location> boardLocations;
    
    private final ArrayList<PropertyLocation> brownGroup;
    private final ArrayList<PropertyLocation> lightBlueGroup;
    private final ArrayList<PropertyLocation> pinkGroup;
    private final ArrayList<PropertyLocation> orangeGroup;
    private final ArrayList<PropertyLocation> redGroup;
    private final ArrayList<PropertyLocation> yellowGroup;
    private final ArrayList<PropertyLocation> greenGroup;
    private final ArrayList<PropertyLocation> blueGroup;
    
    private int houses;
    private int hotels;
    
    /**
     * Creates a board instance, and populates all the locations and property
     * groups that the board consists of.
     */
    public Board() {
        boardLocations = new ArrayList<>();
        brownGroup = new ArrayList<>();
        lightBlueGroup = new ArrayList<>();
        pinkGroup = new ArrayList<>();
        orangeGroup = new ArrayList<>();
        redGroup = new ArrayList<>();
        yellowGroup = new ArrayList<>();
        greenGroup = new ArrayList<>();
        blueGroup = new ArrayList<>();
        
        houses = 32;
        hotels = 10;
        
        populateLocations();
        populateGroups();
    }
    
    /**
     * Populates the board location ArrayList with the necessary elements,
     * in the exact order they appear on the board in Monopoly, in the range of
     * 0 to 39.
     */
    private void populateLocations() {
        boardLocations.add(new GoLocation("Go", 200));
        boardLocations.add(new PropertyLocation("Old Kent Road",
                                                60,
                                                new int[]{2, 10, 30, 90, 160, 250},
                                                50));
        boardLocations.add(new CommunityChestLocation("Community Chest"));
        boardLocations.add(new PropertyLocation("Whitechapel Road",
                                                60,
                                                new int[]{4, 20, 60, 180, 320, 450},
                                                50));
        boardLocations.add(new TaxLocation("Income Tax", 200));
        boardLocations.add(new RailwayLocation("Kings Cross Station"));
        boardLocations.add(new PropertyLocation("The Angel Islington",
                                                100,
                                                new int[]{6, 30, 90, 270, 400, 550},
                                                50));
        boardLocations.add(new ChanceLocation("Chance"));
        boardLocations.add(new PropertyLocation("Euston Road",
                                                100,
                                                new int[]{6, 30, 90, 270, 400, 550},
                                                50));
        boardLocations.add(new PropertyLocation("Pentonville Road",
                                                120,
                                                new int[]{8, 40, 100, 300, 450, 600},
                                                50));
        boardLocations.add(new JailLocation("Jail"));
        boardLocations.add(new PropertyLocation("Pall Mall",
                                                140,
                                                new int[]{10, 50, 150, 450, 625, 750},
                                                100));
        boardLocations.add(new UtilityLocation("Electric Company"));
        boardLocations.add(new PropertyLocation("Whitehall",
                                                140,
                                                new int[]{10, 50, 150, 450, 625, 750},
                                                100));
        boardLocations.add(new PropertyLocation("Northumberland Avenue",
                                                160,
                                                new int[]{12, 60, 180, 500, 700, 900},
                                                100));
        boardLocations.add(new RailwayLocation("Marylebone Station"));
        boardLocations.add(new PropertyLocation("Bow Street",
                                                180,
                                                new int[]{14, 70, 200, 550, 750, 950},
                                                100));
        boardLocations.add(new CommunityChestLocation("Community Chest"));
        boardLocations.add(new PropertyLocation("Marlborough Street",
                                                180,
                                                new int[]{14, 70, 200, 550, 750, 950},
                                                100));
        boardLocations.add(new PropertyLocation("Vine Street",
                                                200,
                                                new int[]{16, 80, 220, 600, 800, 1000},
                                                100));
        boardLocations.add(new FreeParkingLocation("Free Parking"));
        boardLocations.add(new PropertyLocation("Strand",
                                                220,
                                                new int[]{18, 90, 250, 700, 875, 1050},
                                                150));
        boardLocations.add(new ChanceLocation("Chance"));
        boardLocations.add(new PropertyLocation("Fleet Street",
                                                220,
                                                new int[]{18, 90, 250, 700, 875, 1050},
                                                150));
        boardLocations.add(new PropertyLocation("Trafalgar Square",
                                                240,
                                                new int[]{20, 100, 300, 750, 925, 1100},
                                                150));
        boardLocations.add(new RailwayLocation("Fenchurch Street Station"));
        boardLocations.add(new PropertyLocation("Leicester Square",
                                                260,
                                                new int[]{22, 110, 330, 800, 975, 1150},
                                                150));
        boardLocations.add(new PropertyLocation("Coventry Street",
                                                260,
                                                new int[]{22, 110, 330, 800, 975, 1150},
                                                150));
        boardLocations.add(new UtilityLocation("Waterworks"));
        boardLocations.add(new PropertyLocation("Piccadilly",
                                                280,
                                                new int[]{24, 120, 360, 850, 1025, 1200},
                                                150));
        boardLocations.add(new GoToJailLocation("Go To Jail", 10));
        boardLocations.add(new PropertyLocation("Regent Street",
                                                300,
                                                new int[]{26, 130, 390, 900, 1100, 1275},
                                                200));
        boardLocations.add(new PropertyLocation("Oxford Street",
                                                300,
                                                new int[]{26, 130, 390, 900, 1100, 1275},
                                                200));
        boardLocations.add(new CommunityChestLocation("Community Chest"));
        boardLocations.add(new PropertyLocation("Bond Street",
                                                320,
                                                new int[]{28, 150, 450, 1000, 1200, 1400},
                                                200));
        boardLocations.add(new RailwayLocation("Liverpool Street Station"));
        boardLocations.add(new ChanceLocation("Chance"));
        boardLocations.add(new PropertyLocation("Park Lane",
                                                350,
                                                new int[]{35, 175, 500, 1100, 1300, 1500},
                                                200));
        boardLocations.add(new TaxLocation("Super Tax", 100));
        boardLocations.add(new PropertyLocation("Mayfair",
                                                400,
                                                new int[]{50, 200, 600, 1400, 1700, 2000},
                                                200));
    }
    
    /**
     * Utilises the properties created in the populateLocations method, and adds
     * the correct properties to their respective colourGroups.
     */
    private void populateGroups() {
        brownGroup.add((PropertyLocation) boardLocations.get(1));
        brownGroup.add((PropertyLocation) boardLocations.get(3));
        
        lightBlueGroup.add((PropertyLocation) boardLocations.get(6));
        lightBlueGroup.add((PropertyLocation) boardLocations.get(8));
        lightBlueGroup.add((PropertyLocation) boardLocations.get(9));
        
        pinkGroup.add((PropertyLocation) boardLocations.get(11));
        pinkGroup.add((PropertyLocation) boardLocations.get(13));
        pinkGroup.add((PropertyLocation) boardLocations.get(14));
        
        orangeGroup.add((PropertyLocation) boardLocations.get(16));
        orangeGroup.add((PropertyLocation) boardLocations.get(18));
        orangeGroup.add((PropertyLocation) boardLocations.get(19));
        
        redGroup.add((PropertyLocation) boardLocations.get(21));
        redGroup.add((PropertyLocation) boardLocations.get(23));
        redGroup.add((PropertyLocation) boardLocations.get(24));
        
        yellowGroup.add((PropertyLocation) boardLocations.get(26));
        yellowGroup.add((PropertyLocation) boardLocations.get(27));
        yellowGroup.add((PropertyLocation) boardLocations.get(29));
        
        greenGroup.add((PropertyLocation) boardLocations.get(31));
        greenGroup.add((PropertyLocation) boardLocations.get(32));
        greenGroup.add((PropertyLocation) boardLocations.get(34));
        
        blueGroup.add((PropertyLocation) boardLocations.get(37));
        blueGroup.add((PropertyLocation) boardLocations.get(39));
    }
    
    /**
     * Retrieves the group of properties that the input parameter forms part of.
     * @param property The property whose group will be retrieved.
     * @return         The group retrieved.
     */
    public ArrayList<PropertyLocation> getGroup(PropertyLocation property) {
        if(brownGroup.contains(property)) {
            return brownGroup;
        }
        else if(lightBlueGroup.contains(property)) {
            return lightBlueGroup;
        }
        else if(pinkGroup.contains(property)) {
            return pinkGroup;
        }
        else if(orangeGroup.contains(property)) {
            return orangeGroup;
        }
        else if(redGroup.contains(property)) {
            return redGroup;
        }
        else if(yellowGroup.contains(property)) {
            return yellowGroup;
        }
        else if(greenGroup.contains(property)) {
            return greenGroup;
        }
        else {
            return blueGroup;
        }
    }
    
    /**
     * Assimilates the colour monopoly status amongst all members of a colour
     * group. Ensures the properties all reference the same owner, and if true
     * sets the inColourMonopoly status of each to true, otherwise sets each to
     * false.
     * <p>
     * If all properties have the same owner, groupDevelopable is called, but not
     * when false. This is because when set to false, each property automatically
     * sets its developable status to false. However a colour group monopoly is
     * possible even with mortgaged property, which would affect the isDevelopable
     * status. In logic terms: !A => !B but A !=> B.
     * @param group The group to be operated on.
     */
    public void assimilateColourGroupBooleans(ArrayList<PropertyLocation> group) {
        
        boolean sameOwner = true;
            
        for(PropertyLocation property : group) {
            if((property.getOwner() != group.get(0).getOwner()) || property.getOwner() == null) {
                sameOwner = false;
            }
        }
            
        if(sameOwner) {
            group.forEach(property -> property.setInColourMonopolyStatus(true));
            groupDevelopable(group);
            upgradeableToHotelCheck(group);
        }
        else {
            group.forEach(property ->  property.setInColourMonopolyStatus(false));
            group.forEach(property -> property.setDevelopableStatus(false));
        }
        
        
        
    }
    
    /**
     * Assimilates the developable status of the properties, on the assumption
     * that each has inColourMonopoly set to true. Hence this method should
     * ONLY be called in the coloutGroupMonopoly method of this class, when the 
     * sameOwner boolean has been looped through and remains true. 
     * @param group The group on which to operate.
     */
    private void groupDevelopable(ArrayList<PropertyLocation> group) {
        boolean noneMortgaged = true;
        
        for(PropertyLocation property : group) {
            if(property.getMortgagedStatus()) {
                noneMortgaged = false;
            }
        }
        
        if(noneMortgaged) {
            group.forEach(property -> individualDevelopable(group, property));
        }
        else {
            group.forEach(property -> property.setDevelopableStatus(false));
        }
            
    }
    
    /**
     * Determines whether individual property is developable, based on its
     * difference to the other members of the same colour group.
     * @param group The colour group to check in.
     * @param property The property to check.
     */
    private void individualDevelopable(ArrayList<PropertyLocation> group, PropertyLocation property) {
        
        if(property.getIsHotel())
            property.setDevelopableStatus(false);
        else {
            property.setDevelopableStatus(true);
            
            group.stream().filter((temp) -> (!temp.equals(property)))
                          .forEachOrdered(e -> {
                              if(property.getNumberOfHouses() > e.getNumberOfHouses())
                                  property.setDevelopableStatus(false);
                          });
        }
    }
    
    /**
     * Checks whether the conditions are correct to set the properties in the colour
     * group to be upgradeable to hotel status.
     * @param group The property group to check.
     */
    private void upgradeableToHotelCheck(ArrayList<PropertyLocation> group) {
        
        ArrayList<PropertyLocation> temp = new ArrayList<>();
        
        group.stream().filter((property) -> (property.getNumberOfHouses() == 4 || property.getIsHotel()))
                      .forEachOrdered((property) -> {
                          temp.add(property);
                      });
        
        if(temp.size() != group.size()) {
            group.forEach(property -> property.setUpgradeableToHotel(false));
        }
        else {
            group.forEach(property -> {
                if(property.getNumberOfHouses() == 4)
                    property.setUpgradeableToHotel(true);
                else
                    property.setUpgradeableToHotel(false);
            });
        }
        
    }
    
    /**
     * Evenly distributes developments among the properties of the colour group
     * corresponding to the input property. 
     * <p>
     * Naturally this method only develops the specified property if it is either
     * distinctly the least developed, or equal to the least developed. Otherwise
     * it will enact on the least developed property in the group, and if two
     * are equal then the earliest on the board will receive the development.
     * <p>
     * @param property The property to improve.
     * @return         The value of the cost of the improvement.
     */
    public int evenlyDevelop(PropertyLocation property) {
        
        int cost = 0;
        
        if(houses > 0) {
            
            int j = 5;
            for(PropertyLocation temp : getGroup(property)) {
                if(temp.getNumberOfHouses() < j && !temp.getIsHotel()) {
                    j = temp.getNumberOfHouses();
                }
            }
            
            if(property.getNumberOfHouses() == j && property.getUpgradeableToHotel()) {
                property.addHouse();
                cost = property.getHousePrice();
                houses += 4;
                hotels -= 1;
            }
            else if(property.getNumberOfHouses() == j && !property.getUpgradeableToHotel() && !property.getIsHotel()) {
                property.addHouse();
                cost = property.getHousePrice();
                houses -= 1;
            }
            else if(property.getNumberOfHouses() != j && property.getIsHotel()) {
                for(PropertyLocation temp : getGroup(property)) {
                    if(temp != property && temp.getNumberOfHouses() == j) {
                        temp.addHouse();
                        cost = temp.getHousePrice();
                        houses += 4;
                        hotels -= 1;
                        break;
                    }
                }
            }
            else if(property.getNumberOfHouses() != j && !property.getIsHotel()) {
                for(PropertyLocation temp : getGroup(property)) {
                    if(temp != property && temp.getNumberOfHouses() == j) {
                        temp.addHouse();
                        cost = temp.getHousePrice();
                        this.houses -= 1;
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
     * @param property The property to reduce.
     * @return         The value of the reimbursement.
     */
    public int evenlyReduce(PropertyLocation property) {
        
        int reimburse = 0;
        
        if(property.getIsHotel() && this.houses < 4) {
            reimburse = reimburseHotelDevelopments(property);
        }
        else if(property.getIsHotel() && this.houses >= 4) {
            property.removeHouse();
            houses -= 4;
            hotels += 1;
            reimburse = property.getHousePrice() / 2;
        }
        else if(property.getNumberOfHouses() == retrieveMostDeveloped(getGroup(property)).getNumberOfHouses()) {
            property.removeHouse();
            houses += 1;
            reimburse = property.getHousePrice() / 2;
        }
        else if(property != retrieveMostDeveloped(getGroup(property))) {
            PropertyLocation temp = retrieveMostDeveloped(getGroup(property));
            if(temp.getIsHotel() && houses < 4) {
                reimburse = reimburseHotelDevelopments(property);
            }
            else if(temp.getIsHotel() && houses > 4) {
                temp.removeHouse();
                houses -= 4;
                hotels += 1;
                reimburse = temp.getHousePrice() / 2;
            }
            else {
                temp.removeHouse();
                houses += 1;
                reimburse = temp.getHousePrice() / 2;
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
     * @param property The property determined for regression.
     * @return         The value of the reimbursement.
     */
    private int reimburseHotelDevelopments(PropertyLocation property) {
        
        int reimburse;
        int houseTotal = 0;
        
        ArrayList<PropertyLocation> areHotels = new ArrayList<>();
        ArrayList<PropertyLocation> nonHotels = new ArrayList<>();
        
        for(PropertyLocation temp : getGroup(property)) {
            if(temp.getIsHotel()) {
                areHotels.add(temp);
                houseTotal += 5;
            }   
            else {
                nonHotels.add(temp);
                houseTotal += temp.getNumberOfHouses();
            }
        }
        
        if(areHotels.size() == getGroup(property).size()) {
            allHotelsRegress(areHotels, property);
        }
        else {
            notAllHotelsUndevelop(areHotels, nonHotels, property);
        }
        
        int newHouseTotal = 0;
        
        for(PropertyLocation temp : getGroup(property)) {
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
    private PropertyLocation retrieveMostDeveloped(ArrayList<PropertyLocation> group) {
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
     * @param areHotels A list of properties that are hotels.
     * @param property The property initially specified for regression.
     */
    private void allHotelsRegress(ArrayList<PropertyLocation> areHotels, PropertyLocation property) {
        int modulo = houses % areHotels.size();
        int eachProperty = houses / areHotels.size();
            
        areHotels.forEach((temp) -> {
            while(!(temp.getNumberOfHouses() == eachProperty)) {
                temp.removeHouse();
            }
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
    private void notAllHotelsUndevelop(ArrayList<PropertyLocation> areHotels, ArrayList<PropertyLocation> nonHotels, PropertyLocation property) {
        
        while(!(((houses / areHotels.size()) - retrieveMostDeveloped(nonHotels).getNumberOfHouses()) == -1) || 
              !(((houses / areHotels.size()) - retrieveMostDeveloped(nonHotels).getNumberOfHouses()) == 0)) {
            retrieveMostDeveloped(nonHotels).removeHouse();
            houses++;
        }
            
        int modulo = houses % areHotels.size();
        int eachProperty = houses / areHotels.size();
            
        areHotels.forEach((temp) -> {
            while(!(temp.getNumberOfHouses() == eachProperty)) {
                temp.removeHouse();
            }
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
     * Retrieves the location at the specified board position. Exactly equivalent
     * to an ArrayList.get() method call. Will only provide valid returns in the
     * range of 0 to 39 inclusive.
     * @param position The position of the location to retrieve.
     * @return         The location retrieved.
     */
    public Location getLocation(int position) {
        
        return boardLocations.get(position);
        
    }
    
    /**
     * Retrieves the number of houses available for players to buy.
     * @return The number of houses.
     */
    public int getNumberOfHousesLeft() {
        return houses;
    }
    
    /**
     * Retrieves the number of hotels available for players to buy.
     * @return The number of hotels.
     */
    public int getNumberOfHotelsLeft() {
        return hotels;
    }
    
    
}
