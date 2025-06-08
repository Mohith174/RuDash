package com.example.ruburger.model;
import java.util.ArrayList;

/**
 * This class represents an order, which contains multiple menu items.
 * Each order is uniquely identified by an order number.
 *
 * (This implementation follows the required coding standard and project instructions.)
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public class Order {
    private static final double SALES_TAX = 0.06625;
    private final int number; // Unique order identifier.
    private final ArrayList<MenuItem> items;

    /**
     * Constructs an Order object with the given order number.
     * @param number
     */
    public Order(int number) {
        this.number = number;
        this.items = new ArrayList<>();
    }

    /**
     * Getter for the order number
     * @return the order number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Getter for the items in this order
     * @return the list of items
     */
    public ArrayList<MenuItem> getItems() {
        return items;
    }

    /**
     * Adds a menu item to the order.
     * @param item the menu item to be added.
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * Removes a menu item from the order.
     * @param item the menu item to be removed.
     */
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    /**
     * Calculates the total price of the order before tax.
     * @return the total price of all items.
     */
    public double calculateTotal() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    /**
     * Calculates the sales tax for the order.
     * New Jersey tax rate is 6.625%.
     * @return the sales tax amount.
     */
    public double calculateTax() {
        return calculateTotal() * SALES_TAX;
    }

    /**
     * Calculates the final order total including sales tax.
     * @return the total order amount.
     */
    public double calculateOrderTotal() {
        return calculateTotal() + calculateTax();
    }

    /**
     * Returns a string representation of the order
     * @return a string with the order details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(number).append("\n");
        if (items.isEmpty()) {
            sb.append("No items in order.\n");
        } else {
            for (MenuItem item : items) {
                // Include quantity in the display for exported orders
                if (item.getQuantity() > 1) {
                    sb.append("- Qty: ").append(item.getQuantity()).append(" - ")
                            .append(item.toString())
                            .append(" (Total: $").append(String.format("%.2f", item.getTotalPrice())).append(")\n");
                } else {
                    sb.append("- ").append(item.toString()).append("\n");
                }
            }
        }
        sb.append("Subtotal: $").append(String.format("%.2f", calculateTotal())).append("\n");
        sb.append("Tax: $").append(String.format("%.2f", calculateTax())).append("\n");
        sb.append("Total: $").append(String.format("%.2f", calculateOrderTotal())).append("\n");
        return sb.toString();
    }
}
