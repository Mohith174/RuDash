package com.example.ruburger.model;

/**
 * Enum representing flavor options for beverages.
 */
public enum Flavor {
    COKE("Coke"),
    DIET_COKE("Diet Coke"),
    CHERRY_COKE("Cherry Coke"),
    GINGER_ALE("Ginger Ale"),
    LEMON_LIME("Lemon Lime"),
    ORANGE("Orange"),
    ROOT_BEER("Root Beer"),
    GRAPE("Grape"),
    STRAWBERRY("Strawberry"),
    APPLE("Apple"),
    LEMONADE("Lemonade"),
    ICED_TEA("Iced Tea"),
    GREEN_TEA("Green Tea"),
    COFFEE("Coffee"),
    HOT_CHOCOLATE("Hot Chocolate");

    private final String name;

    /**
     * Constructor for Flavor enum
     * @param name of the flavor
     */
    Flavor(String name) {
        this.name = name;
    }

    /**
     * Getter for display name of the flavor
     * @return flavor name
     */
    public String getName() {
        return name;
    }

    /**
     * toString Method for name of flavor
     * @return name of flavor
     */
    @Override
    public String toString() {
        return name;
    }
}
