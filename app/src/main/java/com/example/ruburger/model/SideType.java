package com.example.ruburger.model;

/**
 * Enum representing side options.
 */
public enum SideType {
    CHIPS("Chips", 1.99),
    FRIES("Fries", 2.49),
    ONION_RINGS("Onion Rings", 3.29),
    APPLE_SLICES("Apple Slices", 1.29);

    private final String name;
    private final double basePrice; //small size

    /**
     * Constructor for SideType enum
     * @param name of the side
     * @param basePrice for a small size of this side
     */
    SideType(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    /**
     * Getter for display name of the side
     * @return the side name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for base price of the small side
     * @return the base price
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * toString Method for name of side
     * @return name of side
     */
    @Override
    public String toString() {
        return name;
    }
}
