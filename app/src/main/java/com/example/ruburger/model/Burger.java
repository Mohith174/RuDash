package com.example.ruburger.model;
import java.util.ArrayList;

/**
 * This class represents a burger menu item.
 * It extends Sandwich and adds the option for a double patty.
 * The price is adjusted if a double patty is selected.
 *
 * (Adheres to the coding standards by including proper Javadoc and following naming conventions.)
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public class Burger extends Sandwich {
    private static final double DOUBLE_PATTY_PRICE = 2.50;

    private boolean doublePatty;

    /**
     * Default constructs a Burger object.
     */
    public Burger() {
        super();
        this.protein = Protein.BEEF_PATTY;
        this.doublePatty = false;
    }

    /**
     * Constructor with specified bread
     * @param bread type
     */
    public Burger(Bread bread) {
        super(bread, Protein.BEEF_PATTY);
        this.doublePatty = false;
    }

    /**
     * Constructor with specified bread and patty option
     * @param bread type
     * @param doublePatty true for double patty, false for single
     */
    public Burger(Bread bread, boolean doublePatty) {
        super(bread, Protein.BEEF_PATTY);
        this.doublePatty = doublePatty;
    }

    /**
     * Constructor with specified bread, patty option, and add-ons
     * @param bread type
     * @param doublePatty true for double patty, false for single
     * @param addOns list
     */
    public Burger(Bread bread, boolean doublePatty, ArrayList<AddOns> addOns) {
        super(bread, Protein.BEEF_PATTY, addOns);
        this.doublePatty = doublePatty;
    }

    /**
     * Constructor with specified bread, patty option, add-ons, and quantity
     * @param bread type
     * @param doublePatty true for double patty, false for single
     * @param addOns list
     * @param quantity ordered
     */
    public Burger(Bread bread, boolean doublePatty, ArrayList<AddOns> addOns, int quantity) {
        super(bread, Protein.BEEF_PATTY, addOns, quantity);
        this.doublePatty = doublePatty;
    }

    /**
     * Check if this burger has a double patty
     * @return true if double patty, false if single
     */
    public boolean isDoublePatty() {
        return doublePatty;
    }

    /**
     * Set whether this burger has a double patty
     * @param doublePatty true for double patty, false for single
     */
    public void setDoublePatty(boolean doublePatty) {
        this.doublePatty = doublePatty;
    }



    /**
     * Calculates the price of the burger.
     * If doublePatty is true, an extra charge is added.
     * @return the total price of the burger.
     */
    @Override
    public double price() {
        double price = Protein.BEEF_PATTY.getPrice();
        if (doublePatty) {
            price += DOUBLE_PATTY_PRICE;
        }
        for (AddOns addOn : addOns) {
            price += addOn.getPrice();
        }
        return price;
    }

    /**
     * String representation of the burger
     * @return a string describing the burger
     */
    @Override
    public String toString() {
        String pattyType = doublePatty ? "Double" : "Single";
        return pattyType + " Patty Burger on " + bread.getName() +
                " with " + getAddOnsString() + " " +
                formatPriceString(price());
    }
}