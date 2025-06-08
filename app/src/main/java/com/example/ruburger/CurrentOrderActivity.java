package com.example.ruburger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ruburger.model.MenuItem;
import com.example.ruburger.model.Order;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Activity for displaying and managing the current order.
 * Users can view items in their order, remove items, and place the order.
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private ListView orderItemsListView;
    private TextView subtotalValueTextView, taxValueTextView, totalValueTextView;
    private Button removeItemButton, placeOrderButton, mainMenuButton;
    private Order currentOrder;
    private RUBurgerDataManager dataManager;
    private ArrayList<String> orderItemsDisplay;
    private ArrayAdapter<String> orderAdapter;
    private DecimalFormat df = new DecimalFormat("$0.00");
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        dataManager = RUBurgerDataManager.getInstance();
        currentOrder = dataManager.getCurrentOrder();
        initializeViews();
        setupListeners();
        refreshOrderDisplay();
    }

    /**
     * Initialize all view components
     */
    private void initializeViews() {
        orderItemsListView = findViewById(R.id.orderItemsListView);
        subtotalValueTextView = findViewById(R.id.subtotalValueTextView);
        taxValueTextView = findViewById(R.id.taxValueTextView);
        totalValueTextView = findViewById(R.id.totalValueTextView);
        removeItemButton = findViewById(R.id.removeItemButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        orderItemsDisplay = new ArrayList<>();
        orderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, orderItemsDisplay);
        orderItemsListView.setAdapter(orderAdapter);
    }

    /**
     * Setup click listeners for UI components
     */
    private void setupListeners() {
        orderItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                orderItemsListView.setItemChecked(position, true);
                removeItemButton.setEnabled(true);
            }
        });
        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedItem();
            }
        });
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Refresh the order items display and price calculations
     */
    private void refreshOrderDisplay() {
        orderItemsDisplay.clear();
        if (currentOrder != null && currentOrder.getItems().size() > 0) {
            for (MenuItem item : currentOrder.getItems()) {
                String displayText;
                if (item.getQuantity() > 1) {
                    displayText = String.format("Qty: %d - %s (Total: $%.2f)", item.getQuantity(),
                            item.toString().replaceAll("\\s+\\(\\$.*\\)", ""),
                            item.getTotalPrice());
                } else {
                    displayText = item.toString();
                }
                orderItemsDisplay.add(displayText);
            }
            double subtotal = currentOrder.calculateTotal();
            double tax = currentOrder.calculateTax();
            double total = currentOrder.calculateOrderTotal();
            subtotalValueTextView.setText(df.format(subtotal));
            taxValueTextView.setText(df.format(tax));
            totalValueTextView.setText(df.format(total));
            removeItemButton.setEnabled(true);
            placeOrderButton.setEnabled(true);
        } else {
            orderItemsDisplay.add("No items in your order");
            subtotalValueTextView.setText(df.format(0));
            taxValueTextView.setText(df.format(0));
            totalValueTextView.setText(df.format(0));
            removeItemButton.setEnabled(false);
            placeOrderButton.setEnabled(false);
        }
        orderAdapter.notifyDataSetChanged();
    }

    /**
     * Remove the selected item from the order
     */
    private void removeSelectedItem() {
        int selectedPosition = orderItemsListView.getCheckedItemPosition();
        if (selectedPosition != ListView.INVALID_POSITION &&
                selectedPosition < currentOrder.getItems().size()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Remove Item");
            builder.setMessage("Do you want to remove this item from your order?");
            builder.setPositiveButton("Yes", (dialog, id) -> {
                MenuItem itemToRemove = currentOrder.getItems().get(selectedPosition);
                currentOrder.removeItem(itemToRemove);
                refreshOrderDisplay();
                Toast.makeText(CurrentOrderActivity.this,
                        "Item removed", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
            builder.create().show();
        } else {
            Toast.makeText(this,
                    "Please select an item to remove", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Place the current order
     */
    private void placeOrder() {
        if (currentOrder != null && !currentOrder.getItems().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Place Order");
            builder.setMessage("Do you want to place this order?");
            builder.setPositiveButton("Yes", (dialog, id) -> {
                int orderNumber = dataManager.placeOrder();
                Toast.makeText(CurrentOrderActivity.this,
                        "Order #" + orderNumber + " placed successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CurrentOrderActivity.this, AllOrdersActivity.class);
                startActivity(intent);
            });
            builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
            builder.create().show();
        } else {
            Toast.makeText(this,
                    "Cannot place an empty order", Toast.LENGTH_SHORT).show();
        }
    }
}