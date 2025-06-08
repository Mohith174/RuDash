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
import com.example.ruburger.model.Order;
import com.example.ruburger.model.Protein;
import com.example.ruburger.model.Sandwich;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Activity for customizing and ordering sandwiches
 */
public class SandwichActivity extends AppCompatActivity {
    private RadioGroup sandwichBreadGroup;
    private RadioGroup proteinGroup;
    private CheckBox sandwichLettuceCB;
    private CheckBox sandwichTomatoesCB;
    private CheckBox sandwichOnionCB;
    private CheckBox sandwichAvocadoCB;
    private CheckBox sandwichCheeseCB;
    private Spinner sandwichQuantitySpinner;
    private TextView sandwichPriceTV;
    private Button sandwichAddToOrderButton;
    private Button sandwichComboButton;
    private Button sandwichMainMenuButton;
    private RUBurgerDataManager dataManager;
    private Order currentOrder;
    private Sandwich currentSandwich;
    private final DecimalFormat df = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich);
        sandwichBreadGroup = findViewById(R.id.sandwichBreadGroup);
        proteinGroup = findViewById(R.id.proteinGroup);
        sandwichLettuceCB = findViewById(R.id.sandwichLettuceCB);
        sandwichTomatoesCB = findViewById(R.id.sandwichTomatoesCB);
        sandwichOnionCB = findViewById(R.id.sandwichOnionCB);
        sandwichAvocadoCB = findViewById(R.id.sandwichAvocadoCB);
        sandwichCheeseCB = findViewById(R.id.sandwichCheeseCB);
        sandwichQuantitySpinner = findViewById(R.id.sandwichQuantitySpinner);
        sandwichPriceTV = findViewById(R.id.sandwichPriceTV);
        sandwichAddToOrderButton = findViewById(R.id.sandwichAddToOrderButton);
        sandwichComboButton = findViewById(R.id.sandwichComboButton);
        sandwichMainMenuButton = findViewById(R.id.sandwichMainMenuButton);
        dataManager = RUBurgerDataManager.getInstance();
        currentOrder = dataManager.getCurrentOrder();
        setupQuantitySpinner();
        setupEventListeners();
        updateSandwich();
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
        sandwichQuantitySpinner.setAdapter(adapter);
    }

    /**
     * Sets up event listeners for UI components
     */
    private void setupEventListeners() {
        BreadRadioListener breadListener = new BreadRadioListener();
        sandwichBreadGroup.setOnCheckedChangeListener(breadListener);
        ProteinRadioListener proteinListener = new ProteinRadioListener();
        proteinGroup.setOnCheckedChangeListener(proteinListener);
        CheckBoxListener checkBoxListener = new CheckBoxListener();
        sandwichLettuceCB.setOnClickListener(checkBoxListener);
        sandwichTomatoesCB.setOnClickListener(checkBoxListener);
        sandwichOnionCB.setOnClickListener(checkBoxListener);
        sandwichAvocadoCB.setOnClickListener(checkBoxListener);
        sandwichCheeseCB.setOnClickListener(checkBoxListener);
        QuantitySelectedListener quantityListener = new QuantitySelectedListener();
        sandwichQuantitySpinner.setOnItemSelectedListener(quantityListener);
        ButtonClickListener buttonListener = new ButtonClickListener();
        sandwichAddToOrderButton.setOnClickListener(buttonListener);
        sandwichComboButton.setOnClickListener(buttonListener);
        sandwichMainMenuButton.setOnClickListener(buttonListener);
    }

    /**
     * Creates a sandwich based on current selections
     */
    private Sandwich createSandwich() {
        Bread selectedBread;
        int breadId = sandwichBreadGroup.getCheckedRadioButtonId();
        if (breadId == R.id.sandwichWheatRB) {
            selectedBread = Bread.WHEAT;
        } else if (breadId == R.id.sandwichPretzelRB) {
            selectedBread = Bread.PRETZEL;
        } else if (breadId == R.id.sandwichBagelRB) {
            selectedBread = Bread.BAGEL;
        } else if (breadId == R.id.sandwichSourdoughRB) {
            selectedBread = Bread.SOURDOUGH;
        } else {
            selectedBread = Bread.BRIOCHE;
        }
        Protein selectedProtein;
        int proteinId = proteinGroup.getCheckedRadioButtonId();
        if (proteinId == R.id.roastBeefRB) {
            selectedProtein = Protein.ROAST_BEEF;
        } else if (proteinId == R.id.salmonRB) {
            selectedProtein = Protein.SALMON;
        } else {
            selectedProtein = Protein.CHICKEN;
        }
        ArrayList<AddOns> addOns = new ArrayList<>();
        if (sandwichLettuceCB.isChecked()) {
            addOns.add(AddOns.LETTUCE);
        }
        if (sandwichTomatoesCB.isChecked()) {
            addOns.add(AddOns.TOMATOES);
        }
        if (sandwichOnionCB.isChecked()) {
            addOns.add(AddOns.ONIONS);
        }
        if (sandwichAvocadoCB.isChecked()) {
            addOns.add(AddOns.AVOCADO);
        }
        if (sandwichCheeseCB.isChecked()) {
            addOns.add(AddOns.CHEESE);
        }
        int quantity = (Integer) sandwichQuantitySpinner.getSelectedItem();
        return new Sandwich(selectedBread, selectedProtein, addOns, quantity);
    }

    /**
     * Updates the sandwich object based on current UI selections and updates the price display.
     */
    private void updateSandwich() {
        currentSandwich = createSandwich();
        updatePriceDisplay();
    }

    /**
     * Updates the displayed price based on current selections
     */
    private void updatePriceDisplay() {
        if (currentSandwich != null) {
            double price = currentSandwich.getTotalPrice();
            sandwichPriceTV.setText(df.format(price));
        }
    }

    /**
     * Adds the current sandwich to the order and navigates to the order view
     */
    private void addToOrder() {
        if (currentOrder != null && currentSandwich != null) {
            currentOrder.addItem(currentSandwich);
            Toast.makeText(this, "Added to order: " + currentSandwich.toString(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could not add to order", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Creates a combo with the current sandwich and navigates to the combo activity
     */
    private void createCombo() {
        if (currentSandwich != null) {
            currentOrder.addItem(currentSandwich);
            Intent intent = new Intent(this, ComboActivity.class);
            intent.putExtra("FROM_SANDWICH_ACTIVITY", true);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could not create combo", Toast.LENGTH_SHORT).show();
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
     * Listener class for bread radio group changes
     */
    private class BreadRadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            updateSandwich();
        }
    }

    /**
     * Listener class for protein radio group changes
     */
    private class ProteinRadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            updateSandwich();
        }
    }

    /**
     * Listener class for add-on checkbox changes
     */
    private class CheckBoxListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            updateSandwich();
        }
    }

    /**
     * Listener class for quantity spinner selection changes
     */
    private class QuantitySelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
            updateSandwich();
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
            if (viewId == R.id.sandwichAddToOrderButton) {
                addToOrder();
            } else if (viewId == R.id.sandwichComboButton) {
                createCombo();
            } else if (viewId == R.id.sandwichMainMenuButton) {
                navigateToMainMenu();
            }
        }
    }
}
