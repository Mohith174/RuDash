package com.example.ruburger.model;

/**
 * This class represents a beverage menu item.
 * It extends MenuItem and includes attributes for size and flavor.
 *
 * (Follows the coding standard for naming and documentation.)
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
/**
 * This class represents a beverage menu item.
 * It extends MenuItem and includes attributes for size and flavor.
 *
 * (Follows the coding standard for naming and documentation.)
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public class Beverage extends MenuItem {
    private static final double SMALL_PRICE = 1.99;
    private static final double MEDIUM_PRICE = 2.49;
    private static final double LARGE_PRICE = 2.99;

    private Size size;
    private Flavor flavor;

    /**
     * Default constructs a Beverage object.
     */
    public Beverage() {
        super();
        this.size = Size.SMALL;
        this.flavor = Flavor.COKE;
    }

    /**
     * Constructor with specified size and flavor
     * @param size of the beverage
     * @param flavor of the beverage
     */
    public Beverage(Size size, Flavor flavor) {
        super();
        this.size = size;
        this.flavor = flavor;
    }

    /**
     * Constructor with specified size, flavor, and quantity
     * @param size of the beverage
     * @param flavor of the beverage
     * @param quantity ordered
     */
    public Beverage(Size size, Flavor flavor, int quantity) {
        super(quantity);
        this.size = size;
        this.flavor = flavor;
    }

    /**
     * Getter for size of the beverage
     * @return size
     */
    public Size getSize() {
        return size;
    }

    /**
     * Setter for size of the beverage
     * @param size new
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Getter for flavor of the beverage
     * @return the flavor
     */
    public Flavor getFlavor() {
        return flavor;
    }

    /**
     * Setter for flavor of the beverage
     * @param flavor new
     */
    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    /**
     * Calculates the price of the beverage based on its size.
     * @return the total price of the beverage.
     */
    @Override
    public double price() {
        switch (size) {
            case SMALL:
                return SMALL_PRICE;
            case MEDIUM:
                return MEDIUM_PRICE;
            case LARGE:
                return LARGE_PRICE;
            default:
                return SMALL_PRICE; // Default to small price
        }
    }

    /**
     * String representation of the beverage
     * @return a string describing the beverage
     */
    @Override
    public String toString() {
        return size.getName() + " " + flavor.getName() + " " +
                formatPriceString(price());
    }
}