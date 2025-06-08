package com.example.ruburger;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mainTitleTextView;
    private ImageButton burgerButton;
    private ImageButton sandwichButton;
    private ImageButton beverageButton;
    private ImageButton sidesButton;
    private Button currentOrderButton;
    private Button allOrdersButton;
    private RUBurgerDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mainTitleTextView = findViewById(R.id.mainTitleTextView);
        burgerButton = findViewById(R.id.imageButton3);
        sandwichButton = findViewById(R.id.imageButton4);
        beverageButton = findViewById(R.id.imageButton5);
        sidesButton = findViewById(R.id.imageButton6);
        currentOrderButton = findViewById(R.id.button);
        allOrdersButton = findViewById(R.id.button2);
        dataManager = RUBurgerDataManager.getInstance();
        if (dataManager.getCurrentOrder() == null) {
            dataManager.createNewOrder();
        }
        setupEventListeners();
    }

    /**
     * Sets up click listeners for all UI elements
     */
    private void setupEventListeners() {
        burgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToBurger();
            }
        });
        sandwichButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSandwich();
            }
        });
        beverageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToBeverage();
            }
        });
        sidesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSides();
            }
        });
        currentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCurrentOrder();
            }
        });
        allOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAllOrders();
            }
        });
    }

    /**
     * Navigates to the burger customization activity
     */
    private void navigateToBurger() {
        Intent intent = new Intent(this, BurgerActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the sandwich customization activity
     */
    private void navigateToSandwich() {
        Intent intent = new Intent(this, SandwichActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the beverage selection activity
     */
    private void navigateToBeverage() {
        Intent intent = new Intent(this, BeverageActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the sides selection activity
     */
    private void navigateToSides() {
        Intent intent = new Intent(this, SidesActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the current order view
     */
    private void navigateToCurrentOrder() {
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the all orders view
     */
    private void navigateToAllOrders() {
        Intent intent = new Intent(this, AllOrdersActivity.class);
        startActivity(intent);
    }

    /**
     * Create a new order when returning to the main menu if needed
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (dataManager.getCurrentOrder() == null) {
            dataManager.createNewOrder();
        }
    }
}