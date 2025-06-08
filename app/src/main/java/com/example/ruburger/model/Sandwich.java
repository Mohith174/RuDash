package com.example.ruburger.model;
import java.util.ArrayList;

/**
 * This class represents a sandwich menu item.
 * It extends MenuItem and includes the bread type, protein type, and add-ons.
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public class Sandwich extends MenuItem {
    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Default constructs a Sandwich object.
     */
    public Sandwich() {
        super();
        this.bread = Bread.BRIOCHE;
        this.protein = Protein.CHICKEN;
        this.addOns = new ArrayList<>();
    }

    /**
     * Constructor with specified bread and protein
     *
     * @param bread   type
     * @param protein type
     */
    public Sandwich(Bread bread, Protein protein) {
        super();
        this.bread = bread;
        this.protein = protein;
        this.addOns = new ArrayList<>();
    }

    /**
     * Constructor with specified bread, protein, and add-ons
     *
     * @param bread   type
     * @param protein type
     * @param addOns list
     */
    public Sandwich(Bread bread, Protein protein, ArrayList<AddOns> addOns) {
        super();
        this.bread = bread;
        this.protein = protein;
        this.addOns = addOns;
    }

    /**
     * Constructor with specified bread, protein, add-ons, and quantity
     *
     * @param bread    type
     * @param protein  type
     * @param addOns list
     * @param quantity ordered
     */
    public Sandwich(Bread bread, Protein protein, ArrayList<AddOns> addOns, int quantity) {
        super(quantity);
        this.bread = bread;
        this.protein = protein;
        this.addOns = addOns;
    }

    /**
     * Getter for bread type
     *
     * @return the bread type
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Setter for bread type
     *
     * @param bread type new
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }

    /**
     * Getter for protein type
     *
     * @return the protein type
     */
    public Protein getProtein() {
        return protein;
    }

    /**
     * Setter for protein type
     *
     * @param protein type new
     */
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    /**
     * Getter for list of add-ons
     *
     * @return the list of add-ons
     */
    public ArrayList<AddOns> getAddOns() {
        return addOns;
    }

    /**
     * Setter for list of add-ons
     *
     * @param addOns list new
     */
    public void setAddOns(ArrayList<AddOns> addOns) {
        this.addOns = addOns;
    }

    /**
     * Add an add-on to the sandwich
     *
     * @param addOn to add
     */
    public void addAddOn(AddOns addOn) {
        addOns.add(addOn);
    }

    /**
     * Remove an add-on from the sandwich
     *
     * @param addOn to remove
     * @return true if the add-on was removed, false otherwise
     */
    public boolean removeAddOn(AddOns addOn) {
        return addOns.remove(addOn);
    }


    /**
     * Calculates the price of the sandwich based on the protein and add-ons.
     *
     * @return the total price of the sandwich.
     */
    @Override
    public double price() {
        double price = protein.getPrice();
        for (AddOns addOn : addOns) {
            price += addOn.getPrice();
        }
        return price;
    }

    /**
     * Get a formatted string representation of all add-ons
     *
     * @return a string listing all add-ons
     */
    public String getAddOnsString() {
        if (addOns.isEmpty()) {
            return "No add-ons";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addOns.size(); i++) {
            sb.append(addOns.get(i).getName());
            if (i < addOns.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * String representation of the sandwich
     *
     * @return a string describing the sandwich
     */
    @Override
    public String toString() {
        return protein.getName() + " Sandwich on " + bread.getName() +
                " with " + getAddOnsString() + " " +
                formatPriceString(price());
    }
}