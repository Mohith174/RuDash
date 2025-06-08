package com.example.ruburger.model;

/**
 * Enum representing size options for beverages and sides.
 */
public enum Size {
    SMALL("Small"),
    MEDIUM("Medium"),
    LARGE("Large");

    private final String name;

    /**
     * Constructor for Size enum
     * @param name of the size
     */
    Size(String name) {
        this.name = name;
    }

    /**
     * Getter for display name of the size
     * @return the size name
     */
    public String getName() {
        return name;
    }

    /**
     * toString Method for size
     * @return name of size
     */
    @Override
    public String toString() {
        return name;
    }
}
