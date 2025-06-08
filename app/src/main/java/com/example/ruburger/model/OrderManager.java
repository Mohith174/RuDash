package com.example.ruburger.model;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class manages all orders in the RU Burger system.
 * It maintains a collection of orders, generates unique order numbers,
 * and provides methods for adding, retrieving, and removing orders.
 *
 * @author
 *   Mohith Kodavati, Nalita Pillay
 */
public class OrderManager {
    private HashMap<Integer, Order> orders;
    private int nextOrderNumber;

    /**
     * Constructs a new OrderManager object.
     * Initializes the orders collection and sets the first order number.
     */
    public OrderManager() {
        this.orders = new HashMap<>();
        this.nextOrderNumber = 1000; // Starting order number
    }

    /**
     * Creates a new working order without adding it to the tracked orders.
     * @return a new Order object that is not yet tracked
     */
    public Order createWorkingOrder() {
        return new Order(nextOrderNumber);
    }

    /**
     * Adds the given order to the system and increments the next order number.
     * @param order the order to be added
     */
    public void addOrder(Order order) {
        if (order.getNumber() == nextOrderNumber) {
            orders.put(nextOrderNumber, order);
            nextOrderNumber++;
        } else {
            orders.put(nextOrderNumber, order);
            nextOrderNumber++;
        }
    }

    /**
     * Retrieves an order by its order number.
     * @param orderNumber
     * @return the Order object, or null if not found
     */
    public Order getOrder(int orderNumber) {
        return orders.get(orderNumber);
    }

    /**
     * Cancels (removes) an order from the system.
     * @param orderNumber
     * @return true if the order was found and removed, false otherwise
     */
    public boolean cancelOrder(int orderNumber) {
        if (orders.containsKey(orderNumber)) {
            orders.remove(orderNumber);
            return true;
        }
        return false;
    }

    /**
     * Gets all orders currently in the system.
     * @return a map of order numbers to Order objects
     */
    public HashMap<Integer, Order> getAllOrders() {
        return orders;
    }

    /**
     * Gets a list of all order numbers in the system.
     * @return an ArrayList of order numbers
     */
    public ArrayList<Integer> getAllOrderNumbers() {
        return new ArrayList<>(orders.keySet());
    }
}
