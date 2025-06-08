package com.example.ruburger.model;

/**
 * Enum representing bread options for sandwiches and burgers.
 */
public enum Bread {
    BRIOCHE("Brioche"),
    WHEAT("Wheat bread"),
    PRETZEL("Pretzel"),
    BAGEL("Bagel"),
    SOURDOUGH("Sourdough");

    private final String name;

    /**
     * Constructor for Bread enum
     * @param name of the bread
     */
    Bread(String name) {
        this.name = name;
    }

    /**
     * Getter for name of the bread
     * @return bread name
     */
    public String getName() {
        return name;
    }

    /**
     * toString Method for bread name
     * @return name of bread
     */
    @Override
    public String toString() {
        return name;
    }
}
