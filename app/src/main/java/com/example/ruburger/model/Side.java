package com.example.ruburger.model;

/**
 * This class represents a side menu item.
 * It extends MenuItem and accounts for size-based price adjustments.
 */
public class Side extends MenuItem {
    private static final double MEDIUM_PRICE_ADDITION = 0.50;
    private static final double LARGE_PRICE_ADDITION = 1.00;

    private SideType type;
    private Size size;

    /**
     * Default constructs a Side object.
     */
    public Side() {
        super();
        this.type = SideType.CHIPS;
        this.size = Size.SMALL;
    }

    /**
     * Constructor with specified type and size
     * @param type the type of side
     * @param size the size of side
     */
    public Side(SideType type, Size size) {
        super();
        this.type = type;
        this.size = size;
    }

    /**
     * Constructor with specified type, size, and quantity
     * @param type the type of side
     * @param size the size of side
     * @param quantity the quantity ordered
     */
    public Side(SideType type, Size size, int quantity) {
        super(quantity);
        this.type = type;
        this.size = size;
    }

    /**
     * Getter for side type
     * @return the side type
     */
    public SideType getType() {
        return type;
    }

    /**
     * Setter for side type
     * @param type of side new
     */
    public void setType(SideType type) {
        this.type = type;
    }

    /**
     * Getter for side size
     * @return the side size
     */
    public Size getSize() {
        return size;
    }

    /**
     * Setter for side size
     * @param size the new side size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Calculates the price of the side.
     * Additional charges apply for medium and large sizes.
     *
     * @return the total price of the side.
     */
    @Override
    public double price() {
        double price = type.getBasePrice();
        if (size == Size.MEDIUM) {
            price += MEDIUM_PRICE_ADDITION;
        } else if (size == Size.LARGE) {
            price += LARGE_PRICE_ADDITION;
        }
        return price;
    }

    /**
     * String representation of the side
     * @return a string describing the side
     */
    @Override
    public String toString() {
        return size.getName() + " " + type.getName() + " " +
                formatPriceString(price());
    }
}