package com.example.ruburger;
import com.example.ruburger.model.Order;
import com.example.ruburger.model.OrderManager;

/**
 * This class implements the Singleton design pattern to share data between different activities.
 * It manages the app's data state including orders and current selections.
 */
public class RUBurgerDataManager {
    private static RUBurgerDataManager instance;

    private OrderManager orderManager;
    private Order currentOrder;

    /**
     * Private constructor to prevent direct instantiation.
     * This is part of the Singleton design pattern.
     */
    private RUBurgerDataManager() {
        orderManager = new OrderManager();
        currentOrder = orderManager.createWorkingOrder();
    }

    /**
     * Gets the single instance of the data manager.
     * Creates the instance if it doesn't exist yet.
     * @return The singleton instance
     */
    public static synchronized RUBurgerDataManager getInstance() {
        if (instance == null) {
            instance = new RUBurgerDataManager();
        }
        return instance;
    }

    /**
     * Get the current order manager
     * @return The order manager
     */
    public OrderManager getOrderManager() {
        return orderManager;
    }

    /**
     * Get the current working order
     * @return The current order
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Set the current working order
     * @param order The order to set as current
     */
    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }
    /**
     * Creates a new working order
     * @return The newly created order
     */
    public Order createNewOrder() {
        currentOrder = orderManager.createWorkingOrder();
        return currentOrder;
    }

    /**
     * Places the current order and creates a new working order
     * @return The order number of the placed order
     */
    public int placeOrder() {
        int orderNumber = currentOrder.getNumber();
        orderManager.addOrder(currentOrder);
        currentOrder = orderManager.createWorkingOrder();
        return orderNumber;
    }

    /**
     * Cancel a specific order
     * @param orderNumber The order number to cancel
     * @return true if cancelled successfully, false otherwise
     */
    public boolean cancelOrder(int orderNumber) {
        return orderManager.cancelOrder(orderNumber);
    }
}
