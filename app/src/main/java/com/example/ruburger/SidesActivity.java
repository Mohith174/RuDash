package com.example.ruburger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ruburger.model.Order;
import com.example.ruburger.model.Side;
import com.example.ruburger.model.SideType;
import com.example.ruburger.model.Size;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Activity for customizing and ordering sides.
 * Users can select side type, size, and quantity.
 */
public class SidesActivity extends AppCompatActivity {
    private Spinner sideTypeSpinner;
    private ImageView sidesImageView;
    private RadioGroup sizeGroup;
    private RadioButton smallRB;
    private RadioButton mediumRB;
    private RadioButton largeRB;
    private Spinner sidesQuantitySpinner;
    private TextView sidesPriceTV;
    private Button sidesAddToOrderButton;
    private Button sidesMainMenuButton;
    private RUBurgerDataManager dataManager;
    private Order currentOrder;
    private Side currentSide;
    private final DecimalFormat df = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sides);
        sideTypeSpinner = findViewById(R.id.sideTypeSpinner);
        sidesImageView = findViewById(R.id.sidesImage);
        sizeGroup = findViewById(R.id.sizeGroup);
        smallRB = findViewById(R.id.smallRB);
        mediumRB = findViewById(R.id.mediumRB);
        largeRB = findViewById(R.id.largeRB);
        sidesQuantitySpinner = findViewById(R.id.sidesQuantitySpinner);
        sidesPriceTV = findViewById(R.id.sidesPriceTV);
        sidesAddToOrderButton = findViewById(R.id.sidesAddToOrderButton);
        sidesMainMenuButton = findViewById(R.id.sidesMainMenuButton);
        dataManager = RUBurgerDataManager.getInstance();
        currentOrder = dataManager.getCurrentOrder();
        setupSideTypeSpinner();
        setupQuantitySpinner();
        setupEventListeners();
        updateSide();
    }

    /**
     * Sets up the side type spinner with available options
     */
    private void setupSideTypeSpinner() {
        ArrayList<SideType> sideTypes = new ArrayList<>();
        sideTypes.add(SideType.FRIES);
        sideTypes.add(SideType.CHIPS);
        sideTypes.add(SideType.ONION_RINGS);
        sideTypes.add(SideType.APPLE_SLICES);
        ArrayAdapter<SideType> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, sideTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sideTypeSpinner.setAdapter(adapter);
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
        sidesQuantitySpinner.setAdapter(adapter);
    }

    /**
     * Sets up event listeners for UI components
     */
    private void setupEventListeners() {
        SideTypeSelectedListener sideTypeListener = new SideTypeSelectedListener();
        sideTypeSpinner.setOnItemSelectedListener(sideTypeListener);
        RadioGroupChangeListener sizeListener = new RadioGroupChangeListener();
        sizeGroup.setOnCheckedChangeListener(sizeListener);
        QuantitySelectedListener quantityListener = new QuantitySelectedListener();
        sidesQuantitySpinner.setOnItemSelectedListener(quantityListener);
        ButtonClickListener buttonListener = new ButtonClickListener();
        sidesAddToOrderButton.setOnClickListener(buttonListener);
        sidesMainMenuButton.setOnClickListener(buttonListener);
    }

    /**
     * Updates the side image based on selected side type
     */
    private void updateSideImage() {
        SideType selectedType = (SideType) sideTypeSpinner.getSelectedItem();
        if (selectedType != null) {
            int resourceId;
            switch (selectedType) {
                case CHIPS:
                    resourceId = R.drawable.chips;
                    break;
                case ONION_RINGS:
                    resourceId = R.drawable.onion_rings;
                    break;
                case APPLE_SLICES:
                    resourceId = R.drawable.apple_slices;
                    break;
                case FRIES:
                default:
                    resourceId = R.drawable.fries;
                    break;
            }
            sidesImageView.setImageResource(resourceId);
        }
    }

    /**
     * Updates the side object based on current selections and updates the price display
     */
    private void updateSide() {
        SideType selectedType = (SideType) sideTypeSpinner.getSelectedItem();
        Size selectedSize;
        if (mediumRB.isChecked()) {
            selectedSize = Size.MEDIUM;
        } else if (largeRB.isChecked()) {
            selectedSize = Size.LARGE;
        } else {
            selectedSize = Size.SMALL;
        }
        int quantity = (Integer) sidesQuantitySpinner.getSelectedItem();
        currentSide = new Side(selectedType, selectedSize, quantity);
        updatePriceDisplay();
    }

    /**
     * Updates the price display based on the current side
     */
    private void updatePriceDisplay() {
        if (currentSide != null) {
            double price = currentSide.getTotalPrice();
            sidesPriceTV.setText(df.format(price));
        }
    }

    /**
     * Adds the current side to the order and navigates to the order view
     */
    private void addToOrder() {
        if (currentOrder != null && currentSide != null) {
            currentOrder.addItem(currentSide);
            Toast.makeText(this, "Added to order: " + currentSide.toString(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CurrentOrderActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could not add to order", Toast.LENGTH_SHORT).show();
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
     * Listener class for side type spinner selection changes
     */
    private class SideTypeSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateSideImage();
            updateSide();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing
        }
    }

    /**
     * Listener class for size radio group changes
     */
    private class RadioGroupChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            updateSide();
        }
    }

    /**
     * Listener class for quantity spinner selection changes
     */
    private class QuantitySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateSide();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
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
            if (viewId == R.id.sidesAddToOrderButton) {
                addToOrder();
            } else if (viewId == R.id.sidesMainMenuButton) {
                navigateToMainMenu();
            }
        }
    }
}
