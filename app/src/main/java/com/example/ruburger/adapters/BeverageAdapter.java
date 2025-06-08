package com.example.ruburger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruburger.R;
import com.example.ruburger.model.Flavor;
import java.util.List;

/**
 * Adapter for the RecyclerView displaying beverage flavor options
 */
public class BeverageAdapter extends RecyclerView.Adapter<BeverageAdapter.BeverageViewHolder> {
    private final Context context;
    private final List<Flavor> flavors;
    private final OnFlavorSelectedListener listener;
    private int selectedPosition = 0; // Default to first position

    /**
     * Interface for flavor selection callbacks
     */
    public interface OnFlavorSelectedListener {
        void onFlavorSelected(Flavor flavor, int position);
    }

    /**
     * Constructor for the adapter
     * @param context The activity context
     * @param flavors List of beverage flavors to display
     * @param listener Callback for flavor selection events
     */
    public BeverageAdapter(Context context, List<Flavor> flavors, OnFlavorSelectedListener listener) {
        this.context = context;
        this.flavors = flavors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BeverageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_beverage, parent, false);
        return new BeverageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeverageViewHolder holder, int position) {
        Flavor flavor = flavors.get(position);
        holder.flavorNameTextView.setText(flavor.getName());
        int imageResourceId = getImageResourceForFlavor(flavor);
        holder.flavorImageView.setImageResource(imageResourceId);
        if (position == selectedPosition) {
            holder.cardView.setCardElevation(8f);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.selected_item_bg, null));
        } else {
            holder.cardView.setCardElevation(2f);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.card_background, null));
        }

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(oldSelectedPosition);
                notifyItemChanged(selectedPosition);
                if (listener != null) {
                    listener.onFlavorSelected(flavor, selectedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return flavors.size();
    }

    /**
     * Returns the currently selected position
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Sets the selected position and updates the UI
     */
    public void setSelectedPosition(int position) {
        if (position >= 0 && position < flavors.size()) {
            int oldPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedPosition);
        }
    }

    /**
     * Maps flavor enum to drawable resource ID
     */
    private int getImageResourceForFlavor(Flavor flavor) {
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
                return R.drawable.coke; // Default image
        }
    }

    /**
     * ViewHolder class for beverage items
     */
    static class BeverageViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView flavorImageView;
        TextView flavorNameTextView;

        BeverageViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            flavorImageView = itemView.findViewById(R.id.flavorImageView);
            flavorNameTextView = itemView.findViewById(R.id.flavorNameTextView);
        }
    }
}
