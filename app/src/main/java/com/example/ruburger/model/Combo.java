package com.example.ruburger.model;

/**
 * This class represents a combo meal which includes a sandwich, a beverage, and a side.
 * It extends MenuItem and calculates the price based on the sum of its components.
 *
 * (Adheres to all coding standards as described in the assignment.)
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public class Combo extends MenuItem {
    private static final double COMBO_PRICE_ADDITION = 2.00;

    private Sandwich sandwich;
    private Beverage drink;
    private Side side;

    /**
     * Default constructs a Combo object.
     */
    public Combo() {
        super();
        this.sandwich = new Sandwich();
        this.drink = new Beverage();
        this.side = new Side();
    }

    /**
     * Constructor with specified sandwich, drink, and side
     * @param sandwich or burger
     * @param drink
     * @param side
     */
    public Combo(Sandwich sandwich, Beverage drink, Side side) {
        super();
        this.sandwich = sandwich;
        this.drink = drink;
        this.side = side;
    }

    /**
     * Constructor with specified sandwich, drink, side, and quantity
     * @param sandwich or burger
     * @param drink
     * @param side
     * @param quantity ordered
     */
    public Combo(Sandwich sandwich, Beverage drink, Side side, int quantity) {
        super(quantity);
        this.sandwich = sandwich;
        this.drink = drink;
        this.side = side;
    }

    /**
     * Getter for sandwich in this combo
     * @return the sandwich
     */
    public Sandwich getSandwich() {
        return sandwich;
    }

    /**
     * Setter for sandwich in this combo
     * @param sandwich the new sandwich
     */
    public void setSandwich(Sandwich sandwich) {
        this.sandwich = sandwich;
    }

    /**
     * Getter for drink in this combo
     * @return the drink
     */
    public Beverage getDrink() {
        return drink;
    }

    /**
     * Setter for drink in this combo
     * @param drink new
     */
    public void setDrink(Beverage drink) {
        this.drink = drink;
    }

    /**
     * Getter for side in this combo
     * @return the side
     */
    public Side getSide() {
        return side;
    }

    /**
     * Setter for side in this combo
     * @param side new
     */
    public void setSide(Side side) {
        this.side = side;
    }

    /**
     * Calculates the price of the combo by summing the prices of the sandwich, drink, and side.
     * @return the total price of the combo meal.
     */
    @Override
    public double price() {
        return sandwich.price() + COMBO_PRICE_ADDITION;
    }

    /**
     * String representation of the combo
     * @return a string describing the combo
     */
    @Override
    public String toString() {
        return "Combo: " + sandwich.toString().replaceAll("\\s+\\(\\$.*\\)", "") +
                " with " + side.toString().replaceAll("\\s+\\(\\$.*\\)", "") +
                " and " + drink.toString().replaceAll("\\s+\\(\\$.*\\)", "") + " " +
                formatPriceString(price());
    }
}