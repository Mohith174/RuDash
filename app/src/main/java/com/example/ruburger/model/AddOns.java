package com.example.ruburger.model;

/**
 * This class represents an add-on option for sandwiches and burgers.
 */
public enum AddOns {
    LETTUCE("Lettuce", 0.30),
    TOMATOES("Tomatoes", 0.30),
    ONIONS("Onions", 0.30),
    AVOCADO("Avocado", 0.50),
    CHEESE("Cheese", 1.00);

    private final String name;
    private final double price;

    /**
     * Constructs an AddOns object.
     *
     * @param name of the add-on.
     * @param price of the add-on.
     */
    AddOns(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the name of the add-on.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the add-on.
     * @return price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * toString Method for AddOns
     * @return name of AddOns
     */
    @Override
    public String toString() {
        return name;
    }
}
