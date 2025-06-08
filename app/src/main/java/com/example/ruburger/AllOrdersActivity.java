package com.example.ruburger;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruburger.model.MenuItem;
import com.example.ruburger.model.Order;
import com.example.ruburger.model.OrderManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for viewing and managing all placed orders.
 * Users can select orders, view their details, and cancel orders.
 */
public class AllOrdersActivity extends AppCompatActivity {
    private Spinner orderNumberSpinner;
    private ListView orderDetailsListView;
    private TextView orderTotalValueTextView;
    private Button cancelOrderButton;
    private Button mainMenuButton;
    private RUBurgerDataManager dataManager;
    private OrderManager orderManager;
    private ArrayAdapter<Integer> orderNumberAdapter;
    private ArrayAdapter<String> orderDetailsAdapter;
    private Integer orderToCancel;
    private final DecimalFormat df = new DecimalFormat("$0.00");
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        orderDetailsListView = findViewById(R.id.orderDetailsListView);
        orderTotalValueTextView = findViewById(R.id.orderTotalValueTextView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        dataManager = RUBurgerDataManager.getInstance();
        orderManager = dataManager.getOrderManager();
        orderDetailsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        populateOrderSpinner();
        setupEventListeners();
        if (orderNumberAdapter != null && orderNumberAdapter.getCount() > 0) {
            displaySelectedOrder();
        } else {
            clearOrderDisplay();
        }
    }

    /**
     * Sets up all event listeners
     */
    private void setupEventListeners() {
        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displaySelectedOrder();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clearOrderDisplay();
            }
        });
        orderDetailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;
                orderDetailsListView.setItemChecked(position, true);
            }
        });
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCancelOrder();
            }
        });
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainMenu();
            }
        });
    }

    /**
     * Populates the order number spinner with all available order numbers
     */
    private void populateOrderSpinner() {
        ArrayList<Integer> orderNumbers = orderManager.getAllOrderNumbers();
        if (orderNumbers.isEmpty()) {
            Toast.makeText(this, "No orders have been placed yet", Toast.LENGTH_SHORT).show();
            cancelOrderButton.setEnabled(false);
            orderNumberAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, new ArrayList<>());
        } else {
            orderNumberAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, orderNumbers);
            cancelOrderButton.setEnabled(true);
        }
        orderNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(orderNumberAdapter);
    }

    /**
     * Displays the details of the currently selected order
     */
    private void displaySelectedOrder() {
        Integer selectedOrderNumber = (Integer) orderNumberSpinner.getSelectedItem();
        if (selectedOrderNumber != null) {
            Order selectedOrder = orderManager.getOrder(selectedOrderNumber);
            if (selectedOrder != null) {
                List<String> itemDescriptions = new ArrayList<>();
                for (MenuItem item : selectedOrder.getItems()) {
                    String itemInfo;
                    if (item.getQuantity() > 1) {
                        itemInfo = String.format("Qty: %d - %s (Total: %s)", item.getQuantity(),
                                item.toString().replaceAll("\\s+\\(\\$.*\\)", ""),
                                df.format(item.getTotalPrice()));
                    } else {
                        itemInfo = item.toString();
                    }
                    itemDescriptions.add(itemInfo);
                }
                orderDetailsAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, itemDescriptions);
                orderDetailsListView.setAdapter(orderDetailsAdapter);

                orderTotalValueTextView.setText(df.format(selectedOrder.calculateOrderTotal()));
            }
        } else {
            clearOrderDisplay();
        }
    }

    /**
     * Clears the order display when no order is selected
     */
    private void clearOrderDisplay() {
        orderDetailsListView.setAdapter(null);
        orderTotalValueTextView.setText(getString(R.string.price_placeholder));
    }

    /**
     * Shows a confirmation dialog before canceling the selected order
     */
    private void confirmCancelOrder() {
        orderToCancel = (Integer) orderNumberSpinner.getSelectedItem();
        if (orderToCancel != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cancel Order");
            builder.setMessage("Are you sure you want to cancel order #" + orderToCancel + "?");
            builder.setPositiveButton("Yes", new DialogClickListener());
            builder.setNegativeButton("No", null);
            builder.show();
        } else {
            Toast.makeText(this, "No order selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Cancels the selected order and updates the UI
     */
    private void cancelSelectedOrder() {
        if (orderToCancel != null) {
            boolean cancelled = dataManager.cancelOrder(orderToCancel);
            if (cancelled) {
                Toast.makeText(this, "Order #" + orderToCancel + " has been cancelled",
                        Toast.LENGTH_SHORT).show();
                populateOrderSpinner();
                if (orderNumberAdapter.getCount() > 0) {
                    displaySelectedOrder();
                } else {
                    clearOrderDisplay();
                }
            } else {
                Toast.makeText(this, "Could not cancel order #" + orderToCancel,
                        Toast.LENGTH_SHORT).show();
            }
            orderToCancel = null;
        }
    }

    /**
     * Navigates back to the main menu
     */
    private void navigateToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Inner class to handle spinner item selection events
     */
    private class SpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            displaySelectedOrder();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            clearOrderDisplay();
        }
    }

    /**
     * Inner class to handle button click events
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.cancelOrderButton) {
                confirmCancelOrder();
            } else if (viewId == R.id.mainMenuButton) {
                navigateToMainMenu();
            }
        }
    }

    /**
     * Inner class to handle dialog button click events
     */
    private class DialogClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                cancelSelectedOrder();
            }
        }
    }
}