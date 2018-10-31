package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import javafx.beans.property.SimpleObjectProperty;

public abstract class BaseOwnableLocation extends Location {

    protected int baseRent;
    protected int price;
    protected boolean isOwned;
    protected boolean isMortgaged;
    protected final SimpleObjectProperty<Player> owner;

    public BaseOwnableLocation(String name) {
        super(name);
        owner = new SimpleObjectProperty(this, "owner", null);
    }

    /**
     * Retrieves the price of this location.
     * @return The price of the location.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Retrieves the boolean ownership status of this location.
     * @return True if owned, false otherwise.
     */
    public boolean getIsOwned() {
        return isOwned;
    }

    /**
     * Sets the ownership status based on the input parameter.
     * @param status The ownership status of this location.
     */
    protected void setIsOwned(boolean status) {
        isOwned = status;
    }

    /**
     * Retrieves the owner of this location.
     * @return The location owner.
     */
    public Player getOwner() {
        return owner.getValue();
    }

    /**
     * Retrieves the owner (JavaBeans) property of this utility.
     * @return The owner property.
     */
    public SimpleObjectProperty getOwnerProperty() {
        return owner;
    }

    /**
     * Sets the owner of the location based on the input parameter.
     * @param player The player to own this location.
     */
    public void setOwner(Player player) {
        owner.setValue(player);
        if(player != null)
            setIsOwned(true);
        else
            setIsOwned(false);
    }

    /**
     * Removes the current ownership (both the owner and the isOwned boolean).
     */
    public void removeOwner() {
        owner.setValue(null);
        setIsOwned(false);
    }

    /**
     * Sets the mortgaged status to the specified input.
     * @param status The mortgaged status.
     */
    public void setMortgaged(boolean status) {
        this.isMortgaged = status;
    }

    /**
     * Retrieves the mortgaged status of this location.
     * @return True if mortgaged, false otherwise.
     */
    public boolean getMortgaged() {
        return isMortgaged;
    }

    /**
     * Retrieves the String providing information to the user owner of the property.
     * @return The text String.
     */
    public abstract String getUserOwnedString();
}
