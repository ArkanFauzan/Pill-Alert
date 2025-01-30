package com.example.pillalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineTrackingCardAdapter extends RecyclerView.Adapter<MedicineTrackingCardAdapter.CardViewHolder> {

    private Context context;
    private List<MedicineTrackingModel> cardList;

    public MedicineTrackingCardAdapter(Context context, List<MedicineTrackingModel> cardList) {
        this.context = context;
        this.cardList = cardList;
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

        // hide if id == 0 (not exist)
        if (card.getId() == 0) {
            holder.editIcon.setVisibility(View.GONE);
            holder.statusText.setVisibility(View.GONE);
            holder.statusIcon.setVisibility(View.GONE);
        }

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

        // Open detail page on CardView click
        holder.cardView.setOnClickListener(v -> {

        });

        holder.editIcon.setOnClickListener(v -> {
            // Handle edit click
//            Intent intent = new Intent(context, MedicineEditActivity.class);
//            intent.putExtra("id", card.getId()); // parsing id to edit activity
//            intent.putExtra("diseaseId", card.getDiseaseId()); // parsing id to edit activity
//            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, statusText;
        ImageView statusIcon, editIcon;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.medicineTrackingName);
            statusText = itemView.findViewById(R.id.medicineTrackingStatus);
            statusIcon = itemView.findViewById(R.id.medicineTrackingStatusIcon);
            editIcon = itemView.findViewById(R.id.editIcon);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
