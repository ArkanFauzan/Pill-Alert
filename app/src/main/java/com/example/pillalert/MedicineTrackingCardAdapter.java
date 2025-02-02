package com.example.pillalert;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineTrackingCardAdapter extends RecyclerView.Adapter<MedicineTrackingCardAdapter.CardViewHolder> {

    private Context context;
    private List<MedicineTrackingModel> cardList;
    private Boolean showMedicineName;

    public MedicineTrackingCardAdapter(Context context, List<MedicineTrackingModel> cardList, Boolean showMedicineName) {
        this.context = context;
        this.cardList = cardList;
        this.showMedicineName = showMedicineName;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_medicine_tracking_layout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        // Initialize card
        MedicineTrackingModel card = cardList.get(position);

        holder.nameText.setText(card.getTargetDate());
        holder.statusText.setText(card.getTrackingType().getEnglishTranslation());
        // holder.statusIcon.setImageIcon();
        holder.consumeDateText.setText(card.getConsumeDate());

        // hide if id <= 0 (not exist, only for preview)
        if (card.getId() <= 0) {
            holder.editIcon.setVisibility(View.GONE);
            holder.statusText.setVisibility(View.GONE);
            holder.statusIcon.setVisibility(View.GONE);
        }

        if (showMedicineName) {
            // set medicine name
            holder.medicineNameText.setText(card.getMedicine(context).getName());
        }
        else {
            // hide
            holder.medicineNameText.setVisibility(View.GONE);
        }

        if (card.getConsumeDate().trim().isEmpty()) {
            holder.consumeDateContainer.setVisibility(View.GONE);
        }

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

        // Open detail page on CardView click
        holder.cardView.setOnClickListener(v -> {

        });

        holder.editIcon.setOnClickListener(v -> {
            // Handle edit click
            Intent intent = new Intent(context, MedicineTrackingEditActivity.class);
            intent.putExtra("id", card.getId()); // parsing id to edit activity
            intent.putExtra("medicineId", card.getMedicineId()); // parsing id to edit activity
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, statusText, consumeDateText, medicineNameText;
        ImageView statusIcon, editIcon;
        LinearLayout consumeDateContainer;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.medicineTrackingName);
            statusText = itemView.findViewById(R.id.medicineTrackingStatus);
            statusIcon = itemView.findViewById(R.id.medicineTrackingStatusIcon);
            editIcon = itemView.findViewById(R.id.editIcon);
            consumeDateContainer = itemView.findViewById(R.id.consumeDateContainer);
            consumeDateText = itemView.findViewById(R.id.consumeDateText);
            medicineNameText = itemView.findViewById(R.id.medicineName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
