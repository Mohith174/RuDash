package com.example.ruburger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ruburger.adapters.BeverageAdapter;
import com.example.ruburger.model.Beverage;
import com.example.ruburger.model.Flavor;
import com.example.ruburger.model.Size;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for configuring and ordering beverages
 */
public class BeverageActivity extends AppCompatActivity {
    private RecyclerView beverageRecyclerView;
    private ImageView selectedBeverageImageView;
    private RadioGroup sizeRadioGroup;
    private RadioButton smallRadioButton;
    private RadioButton mediumRadioButton;
    private RadioButton largeRadioButton;
    private Spinner quantitySpinner;
    private TextView priceTotalTextView;
    private Button addToOrderButton;
    private Button mainMenuButton;
    private RUBurgerDataManager dataManager;
    private BeverageAdapter beverageAdapter;
    private List<Flavor> flavors;
    private Flavor selectedFlavor;
    private final DecimalFormat df = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverages);
        beverageRecyclerView = findViewById(R.id.beverageRecyclerView);
        selectedBeverageImageView = findViewById(R.id.selectedBeverageImageView);
        sizeRadioGroup = findViewById(R.id.sizeRadioGroup);
        smallRadioButton = findViewById(R.id.smallRadioButton);
        mediumRadioButton = findViewById(R.id.mediumRadioButton);
        largeRadioButton = findViewById(R.id.largeRadioButton);
        quantitySpinner = findViewById(R.id.quantitySpinner);
        priceTotalTextView = findViewById(R.id.priceTotalTextView);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        dataManager = RUBurgerDataManager.getInstance();
        initFlavorList();
        setupRecyclerView();
        setupQuantitySpinner();
        setupEventListeners();
        selectedFlavor = flavors.get(0);
        updateSelectedBeverageImage();
        updatePrice();
    }

    /**
     * Initializes the list of available beverage flavors
     */
    private void initFlavorList() {
        flavors = new ArrayList<>();
        flavors.add(Flavor.COKE);
        flavors.add(Flavor.DIET_COKE);
        flavors.add(Flavor.CHERRY_COKE);
        flavors.add(Flavor.GINGER_ALE);
        flavors.add(Flavor.LEMON_LIME);
        flavors.add(Flavor.ORANGE);
        flavors.add(Flavor.ROOT_BEER);
        flavors.add(Flavor.GRAPE);
        flavors.add(Flavor.STRAWBERRY);
        flavors.add(Flavor.APPLE);
        flavors.add(Flavor.LEMONADE);
        flavors.add(Flavor.ICED_TEA);
        flavors.add(Flavor.GREEN_TEA);
        flavors.add(Flavor.COFFEE);
        flavors.add(Flavor.HOT_CHOCOLATE);
    }

    /**
     * Sets up the RecyclerView with a grid layout and flavor adapter
     */
    private void setupRecyclerView() {
        BeverageAdapter.OnFlavorSelectedListener flavorListener = new BeverageAdapter.OnFlavorSelectedListener() {
            @Override
            public void onFlavorSelected(Flavor flavor, int position) {
                selectedFlavor = flavor;
                updateSelectedBeverageImage();
                updatePrice();
            }
        };
        beverageAdapter = new BeverageAdapter(this, flavors, flavorListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        beverageRecyclerView.setLayoutManager(layoutManager);
        beverageRecyclerView.setAdapter(beverageAdapter);
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
        RadioGroupChangeListener radioGroupListener = new RadioGroupChangeListener();
        sizeRadioGroup.setOnCheckedChangeListener(radioGroupListener);
        QuantitySelectedListener quantityListener = new QuantitySelectedListener();
        quantitySpinner.setOnItemSelectedListener(quantityListener);
        ButtonClickListener buttonListener = new ButtonClickListener();
        addToOrderButton.setOnClickListener(buttonListener);
        mainMenuButton.setOnClickListener(buttonListener);
    }

    /**
     * Updates the displayed image based on the selected flavor
     */
    private void updateSelectedBeverageImage() {
        if (selectedFlavor != null) {
            int imageResId = getFlavorImageResource(selectedFlavor);
            selectedBeverageImageView.setImageResource(imageResId);
        }
    }

    /**
     * Gets the appropriate image resource ID for the given flavor
     */
    private int getFlavorImageResource(Flavor flavor) {
        switch (flavor) {
            case COKE:
                return R.drawable.coke;
            case DIET_COKE:
                return R.drawable.diet_coke;
            case CHERRY_COKE:
                return R.drawable.cherry_coke;
            case GINGER_ALE:
                return R.drawable.ginger_ale;
            case LEMON_LIME:
                return R.drawable.lemon_lime;
            case ORANGE:
                return R.drawable.orange;
            case ROOT_BEER:
                return R.drawable.root_beer;
            case GRAPE:
                return R.drawable.grape;
            case STRAWBERRY:
                return R.drawable.strawberry;
            case APPLE:
                return R.drawable.apple;
            case LEMONADE:
                return R.drawable.lemonade;
            case ICED_TEA:
                return R.drawable.iced_tea;
            case GREEN_TEA:
                return R.drawable.green_tea;
            case COFFEE:
                return R.drawable.coffee;
            case HOT_CHOCOLATE:
                return R.drawable.hot_chocolate;
            default:
                return R.drawable.coke;
        }
    }

    /**
     * Creates a beverage based on current selections
     */
    private Beverage createBeverage() {
        Size selectedSize;
        if (mediumRadioButton.isChecked()) {
            selectedSize = Size.MEDIUM;
        } else if (largeRadioButton.isChecked()) {
            selectedSize = Size.LARGE;
        } else {
            selectedSize = Size.SMALL;
        }
        int quantity = (Integer) quantitySpinner.getSelectedItem();
        return new Beverage(selectedSize, selectedFlavor, quantity);
    }

    /**
     * Updates the displayed price based on current selections
     */
    private void updatePrice() {
        Beverage beverage = createBeverage();
        double price = beverage.getTotalPrice();
        priceTotalTextView.setText(df.format(price));
    }

    /**
     * Adds the current beverage to the order and navigates to the order view
     */
    private void addToOrder() {
        Beverage beverage = createBeverage();
        dataManager.getCurrentOrder().addItem(beverage);
        Toast.makeText(this, "Added to order: " + beverage.toString(),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CurrentOrderActivity.class);
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
     * Listener class for radio button selection changes
     */
    private class RadioGroupChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
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
            } else if (viewId == R.id.mainMenuButton) {
                navigateToMainMenu();
            }
        }
    }
}
