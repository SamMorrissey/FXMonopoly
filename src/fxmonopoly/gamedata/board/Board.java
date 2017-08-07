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
        if(group.get(0).getOwner() != null) {
            boolean sameOwner = true;
            
            for(PropertyLocation property : group) {
                if(property.getOwner() != group.get(0).getOwner()) {
                    sameOwner = false;
                }
            }
            
            if(sameOwner) {
                group.forEach(property -> property.setInColourMonopolyStatus(true));
                groupDevelopable(group);
            }
            else {
                group.forEach(property -> property.setInColourMonopolyStatus(false));
            }
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
            group.forEach(property -> property.setDevelopableStatus(true));
        }
        else {
            group.forEach(property -> property.setDevelopableStatus(false));
        }
    }
    
    /**
     * Retrieves the location at the specified board position. Exactly equivalent
     * to an ArrayList.get() method call. Will only provide valid returns in the
     * range of 0 to 39 inclusive.
     * @param position
     * @return 
     */
    public Location getLocation(int position) {
        
        return boardLocations.get(position);
        
    }
}
