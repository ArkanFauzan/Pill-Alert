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

public class CardDiseaseAdapter extends RecyclerView.Adapter<CardDiseaseAdapter.CardViewHolder> {

    private Context context;
    private List<CardDiseaseModel> cardList;

    public CardDiseaseAdapter(Context context, List<CardDiseaseModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_disease_layout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardDiseaseModel card = cardList.get(position);

        holder.nameText.setText(card.getName());
        holder.descriptionText.setText(card.getDescription());
        holder.dateText.setText(card.getDate());


        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

        holder.editIcon.setOnClickListener(v -> {
            // Handle edit click
        });

        holder.deleteIcon.setOnClickListener(v -> {
            // Handle delete click
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, descriptionText, dateText, emailText;
        ImageView editIcon, deleteIcon;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.diseaseName);
            descriptionText = itemView.findViewById(R.id.diseaseDescription);
            dateText = itemView.findViewById(R.id.diseaseDate);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
