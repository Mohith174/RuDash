package com.example.ruburger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ruburger.model.AddOns;
import com.example.ruburger.model.Bread;
import com.example.ruburger.model.Burger;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Activity for customizing and ordering burgers
 */
public class BurgerActivity extends AppCompatActivity {
    private RadioGroup pattyGroup;
    private RadioGroup breadGroup;
    private CheckBox lettuceCB;
    private CheckBox tomatoesCB;
    private CheckBox onionCB;
    private CheckBox avocadoCB;
    private CheckBox cheeseCB;
    private Spinner quantitySpinner;
    private TextView priceTV;
    private Button addToOrderButton;
    private Button comboButton;
    private Button mainMenuButton;
    private RUBurgerDataManager dataManager;
    private final DecimalFormat df = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);
        pattyGroup = findViewById(R.id.pattyGroup);
        breadGroup = findViewById(R.id.breadGroup);
        lettuceCB = findViewById(R.id.lettuceCB);
        tomatoesCB = findViewById(R.id.tomatoesCB);
        onionCB = findViewById(R.id.onionCB);
        avocadoCB = findViewById(R.id.avocadoCB);
        cheeseCB = findViewById(R.id.cheeseCB);
        quantitySpinner = findViewById(R.id.quantitySpinner);
        priceTV = findViewById(R.id.priceTV);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        comboButton = findViewById(R.id.comboButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        dataManager = RUBurgerDataManager.getInstance();
        setupQuantitySpinner();
        setupEventListeners();
        updatePrice();
    }

    /**
     * Sets up the quantity spinner with values 1-10
     */
    private void setupQuantitySpinner() {
        ArrayList<Integer> quantities = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quantities.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, quantities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(adapter);
    }

    /**
     * Sets up event listeners for UI components
     */
    private void setupEventListeners() {
        PattyRadioListener pattyListener = new PattyRadioListener();
        pattyGroup.setOnCheckedChangeListener(pattyListener);
        BreadRadioListener breadListener = new BreadRadioListener();
        breadGroup.setOnCheckedChangeListener(breadListener);
        CheckBoxListener checkBoxListener = new CheckBoxListener();
        lettuceCB.setOnClickListener(checkBoxListener);
        tomatoesCB.setOnClickListener(checkBoxListener);
        onionCB.setOnClickListener(checkBoxListener);
        avocadoCB.setOnClickListener(checkBoxListener);
        cheeseCB.setOnClickListener(checkBoxListener);
        QuantitySelectedListener quantityListener = new QuantitySelectedListener();
        quantitySpinner.setOnItemSelectedListener(quantityListener);
        ButtonClickListener buttonListener = new ButtonClickListener();
        addToOrderButton.setOnClickListener(buttonListener);
        comboButton.setOnClickListener(buttonListener);
        mainMenuButton.setOnClickListener(buttonListener);
    }

    /**
     * Creates a burger based on current selections
     */
    private Burger createBurger() {
        Bread selectedBread;
        int breadId = breadGroup.getCheckedRadioButtonId();
        if (breadId == R.id.wheatRB) {
            selectedBread = Bread.WHEAT;
        } else if (breadId == R.id.pretzelRB) {
            selectedBread = Bread.PRETZEL;
        } else {
            selectedBread = Bread.BRIOCHE;
        }
        boolean isDoublePatty = pattyGroup.getCheckedRadioButtonId() == R.id.doublePattyRB;
        ArrayList<AddOns> addOns = new ArrayList<>();
        if (lettuceCB.isChecked()) {
            addOns.add(AddOns.LETTUCE);
        }
        if (tomatoesCB.isChecked()) {
            addOns.add(AddOns.TOMATOES);
        }
        if (onionCB.isChecked()) {
            addOns.add(AddOns.ONIONS);
        }
        if (avocadoCB.isChecked()) {
            addOns.add(AddOns.AVOCADO);
        }
        if (cheeseCB.isChecked()) {
            addOns.add(AddOns.CHEESE);
        }
        int quantity = (Integer) quantitySpinner.getSelectedItem();
        return new Burger(selectedBread, isDoublePatty, addOns, quantity);
    }

    /**
     * Updates the displayed price based on current selections
     */
    private void updatePrice() {
        Burger burger = createBurger();
        double price = burger.getTotalPrice();
        priceTV.setText(df.format(price));
    }

    /**
     * Adds the current burger to the order and navigates to the order view
     */
    private void addToOrder() {
        Burger burger = createBurger();
        dataManager.getCurrentOrder().addItem(burger);
        Toast.makeText(this, "Added to order: " + burger.toString(),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CurrentOrderActivity.class);
        startActivity(intent);
    }

    /**
     * Creates a combo with the current burger and navigates to the combo activity
     */
    private void createCombo() {
        Burger burger = createBurger();
        dataManager.getCurrentOrder().addItem(burger);
        Intent intent = new Intent(this, ComboActivity.class);
        intent.putExtra("FROM_BURGER_ACTIVITY", true);
        startActivity(intent);
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
     * Listener class for patty radio group changes
     */
    private class PattyRadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            updatePrice();
        }
    }

    /**
     * Listener class for bread radio group changes
     */
    private class BreadRadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            updatePrice();
        }
    }

    /**
     * Listener class for add-on checkbox changes
     */
    private class CheckBoxListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            updatePrice();
        }
    }

    /**
     * Listener class for quantity spinner selection changes
     */
    private class QuantitySelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
            updatePrice();
        }
        @Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {
            // Do nothing
        }
    }

    /**
     * Listener class for button clicks
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.addToOrderButton) {
                addToOrder();
            } else if (viewId == R.id.comboButton) {
                createCombo();
            } else if (viewId == R.id.mainMenuButton) {
                navigateToMainMenu();
            }
        }
    }
}