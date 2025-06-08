package com.example.ruburger.model;

/**
 * Enum representing protein options for sandwiches and burgers.
 */
public enum Protein {
    ROAST_BEEF("Roast beef", 10.99),
    CHICKEN("Chicken", 8.99),
    SALMON("Salmon", 9.99),
    BEEF_PATTY("Beef patty", 6.99);

    private final String name;
    private final double price;

    /**
     * Constructor for Protein enum
     * @param name of the protein
     * @param price for this protein
     */
    Protein(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Getter for display name of the protein
     * @return the protein name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for base price of the protein
     * @return the base price
     */
    public double getPrice() {
        return price;
    }

    /**
     * toString Method for protein name
     * @return name of protein
     */
    @Override
    public String toString() {
        return name;
    }
}
