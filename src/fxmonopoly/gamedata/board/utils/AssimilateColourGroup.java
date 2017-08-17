/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.utils;

import fxmonopoly.gamedata.board.locations.PropertyLocation;
import java.util.ArrayList;

/**
 * Assimilates the colour monopoly status amongst all members of a colour
 * group. Ensures the properties all reference the same owner, and if true
 * sets the inColourMonopoly status of each to true, otherwise sets each to
 * false.
 * <p>
 * Provides only a single publicly accessible static method, since this class has
 * no state of its own and merely provides an operation.
 * <p>
 * 
 * @author Sam P. Morrissey
 */
public final class AssimilateColourGroup {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private AssimilateColourGroup() {}
    
    /**
     * Assimilates the state of all properties within a colour group.
     * <p>
     * If all properties have the same owner, groupDevelopable is called, but not
     * when false. This is because when set to false, each property automatically
     * sets its developable status to false. However a colour group monopoly is
     * possible even with mortgaged property, which would affect the isDevelopable
     * status. In logic terms: !A => !B but A !=> B.
     * @param group The group to operate on.
     */
    public static void assimilate(ArrayList<PropertyLocation> group) {
        
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
    private static void groupDevelopable(ArrayList<PropertyLocation> group) {
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
    private static void individualDevelopable(ArrayList<PropertyLocation> group, PropertyLocation property) {
        
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
    private static void upgradeableToHotelCheck(ArrayList<PropertyLocation> group) {
        
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
}
