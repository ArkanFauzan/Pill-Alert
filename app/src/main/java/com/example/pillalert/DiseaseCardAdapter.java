package com.example.pillalert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DiseaseCardAdapter extends RecyclerView.Adapter<DiseaseCardAdapter.CardViewHolder> {

    private Context context;
    private DatabaseHelperDiseaseTable diseaseTable;
    private List<DiseaseModel> cardList;

    public DiseaseCardAdapter(Context context, List<DiseaseModel> cardList) {
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

        // Initialize database
        diseaseTable = new DatabaseHelperDiseaseTable(context);

        // Initialize card
        DiseaseModel card = cardList.get(position);

        holder.nameText.setText(card.getName());
        holder.descriptionText.setText(card.getDescription());
        holder.dateText.setText(card.getDate());

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

        // Open detail page on CardView click
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DiseaseDetailActivity.class);
            intent.putExtra("id", card.getId());
            context.startActivity(intent);
        });

        holder.editIcon.setOnClickListener(v -> {
            // Handle edit click
            Intent intent = new Intent(context, DiseaseEditActivity.class);
            intent.putExtra("id", card.getId()); // parsing id to edit activity
            context.startActivity(intent);
        });

        holder.deleteIcon.setOnClickListener(v -> {
            // Handle delete click
            new AlertDialog.Builder(context)
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete \""+card.getName()+"\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete & Remove item from the list
                        diseaseTable.deleteDisease(card.getId()); // delete by id
                        cardList.remove(position); // remove from list
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cardList.size());

                        // if called from detail activity (only 1 card and delete this data)
                        if (context instanceof Activity && context.getClass().getSimpleName().equals("DiseaseDetailActivity")) {
                            Activity activity = (Activity) context;
                            activity.finish(); // Example: Close the activity
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, descriptionText, dateText;
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
