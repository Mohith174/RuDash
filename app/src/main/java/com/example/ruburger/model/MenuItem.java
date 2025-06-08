package com.example.ruburger.model;

/**
 * This abstract class represents a menu item.
 * It defines the common attribute and abstract method for all menu items.
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public abstract class MenuItem {
    protected int quantity;

    /**
     * Default constructor to set the quantity to 1
     */
    public MenuItem() {
        this.quantity = 1;
    }

    /**
     * Constructor that sets the quantity to the specified value
     * @param quantity the number of items
     */
    public MenuItem(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter for quantity
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter for quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates and returns the price of the menu item.
     * @return the price of the menu item.
     */
    public abstract double price();

    /**
     * Calculate the total price for this menu item based on quantity
     * @return the total price
     */
    public double getTotalPrice() {
        return price() * quantity;
    }

    /**
     * Format a price string that includes quantity information if quantity > 1
     * @param basePrice the price of a single item
     * @return formatted price string
     */
    protected String formatPriceString(double basePrice) {
        return String.format("($%.2f)", basePrice);
    }

    /**
     * String representation of the menu item
     * @return string describing the menu item
     */
    @Override
    public abstract String toString();
}
