package com.example.ruburger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ruburger.model.Beverage;
import com.example.ruburger.model.Combo;
import com.example.ruburger.model.Flavor;
import com.example.ruburger.model.MenuItem;
import com.example.ruburger.model.Sandwich;
import com.example.ruburger.model.Side;
import com.example.ruburger.model.SideType;
import com.example.ruburger.model.Size;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for creating combo meals by adding sides and drinks to a sandwich or burger
 */
public class ComboActivity extends AppCompatActivity {
    private TextView comboSandwichTV;
    private Spinner comboSideSpinner;
    private ImageView comboSideImage;
    private Spinner comboDrinkSpinner;
    private ImageView comboDrinkImage;
    private Spinner comboQuantitySpinner;
    private TextView comboPriceTV;
    private Button comboAddToOrderButton;
    private Button comboMainMenuButton;
    private RUBurgerDataManager dataManager;
    private Sandwich baseSandwich;
    private SideType selectedSideType;
    private Flavor selectedDrinkFlavor;
    private int quantity = 1;
    private final DecimalFormat df = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);
        comboSandwichTV = findViewById(R.id.comboSandwichTV);
        comboSideSpinner = findViewById(R.id.comboSideSpinner);
        comboSideImage = findViewById(R.id.comboSideImage);
        comboDrinkSpinner = findViewById(R.id.comboDrinkSpinner);
        comboDrinkImage = findViewById(R.id.comboDrinkImage);
        comboQuantitySpinner = findViewById(R.id.comboQuantitySpinner);
        comboPriceTV = findViewById(R.id.comboPriceTV);
        comboAddToOrderButton = findViewById(R.id.comboAddToOrderButton);
        comboMainMenuButton = findViewById(R.id.comboMainMenuButton);
        dataManager = RUBurgerDataManager.getInstance();
        findBaseSandwich();
        setupSideSpinner();
        setupDrinkSpinner();
        setupQuantitySpinner();
        setupEventListeners();
        updateCombo();
    }

    /**
     * Finds the base sandwich/burger from the order that was most recently added
     */
    private void findBaseSandwich() {
        boolean fromBurgerActivity = getIntent().getBooleanExtra("FROM_BURGER_ACTIVITY", false);
        boolean fromSandwichActivity = getIntent().getBooleanExtra("FROM_SANDWICH_ACTIVITY", false);
        if (fromBurgerActivity || fromSandwichActivity) {
            ArrayList<MenuItem> items = dataManager.getCurrentOrder().getItems();
            if (!items.isEmpty()) {
                MenuItem lastItem = items.get(items.size() - 1);
                if (lastItem instanceof Sandwich) {
                    baseSandwich = (Sandwich) lastItem;
                    dataManager.getCurrentOrder().removeItem(lastItem);
                    comboSandwichTV.setText(baseSandwich.toString().replaceAll("\\s+\\(\\$.*\\)", ""));
                    return;
                }
            }
        }
        Toast.makeText(this, "Please select a sandwich or burger first.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Sets up the side spinner with available side options
     */
    private void setupSideSpinner() {
        List<SideType> sideTypes = new ArrayList<>();
        sideTypes.add(SideType.CHIPS);
        sideTypes.add(SideType.APPLE_SLICES);
        ArrayAdapter<SideType> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                sideTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboSideSpinner.setAdapter(adapter);
        comboSideSpinner.setSelection(0);
        selectedSideType = SideType.CHIPS;
    }

    /**
     * Sets up the drink spinner with available flavors
     */
    private void setupDrinkSpinner() {
        List<Flavor> drinkFlavors = new ArrayList<>();
        drinkFlavors.add(Flavor.COKE);
        drinkFlavors.add(Flavor.ICED_TEA);
        drinkFlavors.add(Flavor.APPLE);
        ArrayAdapter<Flavor> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, drinkFlavors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboDrinkSpinner.setAdapter(adapter);
        comboDrinkSpinner.setSelection(0);
        selectedDrinkFlavor = Flavor.COKE;
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
        comboQuantitySpinner.setAdapter(adapter);
        comboQuantitySpinner.setSelection(0);
    }

    /**
     * Sets up event listeners for UI components
     */
    private void setupEventListeners() {
        SideSpinnerListener sideListener = new SideSpinnerListener();
        comboSideSpinner.setOnItemSelectedListener(sideListener);
        DrinkSpinnerListener drinkListener = new DrinkSpinnerListener();
        comboDrinkSpinner.setOnItemSelectedListener(drinkListener);
        QuantitySpinnerListener quantityListener = new QuantitySpinnerListener();
        comboQuantitySpinner.setOnItemSelectedListener(quantityListener);
        ButtonClickListener buttonListener = new ButtonClickListener();
        comboAddToOrderButton.setOnClickListener(buttonListener);
        comboMainMenuButton.setOnClickListener(buttonListener);
    }

    /**
     * Updates the side image based on selected side type
     */
    private void updateSideImage() {
        int resourceId;
        switch (selectedSideType) {
            case APPLE_SLICES:
                resourceId = R.drawable.apple_slices;
                break;
            case CHIPS:
            default:
                resourceId = R.drawable.chips;
                break;
        }
        comboSideImage.setImageResource(resourceId);
    }

    /**
     * Updates the drink image based on selected flavor
     */
    private void updateDrinkImage() {
        int resourceId;
        switch (selectedDrinkFlavor) {
            case ICED_TEA:
                resourceId = R.drawable.iced_tea;
                break;
            case APPLE:
                resourceId = R.drawable.apple;
                break;
            case COKE:
            default:
                resourceId = R.drawable.coke;
                break;
        }
        comboDrinkImage.setImageResource(resourceId);
    }

    /**
     * Updates the combo based on current selections and updates price
     */
    private void updateCombo() {
        if (baseSandwich != null) {
            updateSideImage();
            updateDrinkImage();
            updatePrice();
        }
    }

    /**
     * Creates a combo based on current selections
     */
    private Combo createCombo() {
        if (baseSandwich == null) {
            return null;
        }
        Side side = new Side(selectedSideType, Size.SMALL);
        Beverage drink = new Beverage(Size.MEDIUM, selectedDrinkFlavor);
        return new Combo(baseSandwich, drink, side, quantity);
    }

    /**
     * Updates the displayed price based on the current combo
     */
    private void updatePrice() {
        Combo combo = createCombo();
        if (combo != null) {
            double price = combo.getTotalPrice();
            comboPriceTV.setText(df.format(price));
        }
    }

    /**
     * Adds the current combo to the order and navigates to the order view
     */
    private void addToOrder() {
        Combo combo = createCombo();
        if (combo != null) {
            dataManager.getCurrentOrder().addItem(combo);
            Toast.makeText(this, "Added combo to order", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error creating combo", Toast.LENGTH_SHORT).show();
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
     * Listener class for side spinner
     */
    private class SideSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedSideType = (SideType) parent.getItemAtPosition(position);
            updateCombo();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing
        }
    }

    /**
     * Listener class for drink spinner
     */
    private class DrinkSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedDrinkFlavor = (Flavor) parent.getItemAtPosition(position);
            updateCombo();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    /**
     * Listener class for quantity spinner
     */
    private class QuantitySpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            quantity = (Integer) parent.getItemAtPosition(position);
            updateCombo();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    /**
     * Listener class for button clicks
     */
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.comboAddToOrderButton) {
                addToOrder();
            } else if (viewId == R.id.comboMainMenuButton) {
                navigateToMainMenu();
            }
        }
    }
}
