package com.example.pillalert;

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

public class MedicineCardAdapter extends RecyclerView.Adapter<MedicineCardAdapter.CardViewHolder> {

    private Context context;
    private DatabaseHelperMedicineTable MedicineTable;
    private List<MedicineModel> cardList;

    public MedicineCardAdapter(Context context, List<MedicineModel> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_medicine_layout, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        // Initialize database
        MedicineTable = new DatabaseHelperMedicineTable(context);

        // Initialize card
        MedicineModel card = cardList.get(position);

        holder.nameText.setText(card.getName());
        holder.descriptionText.setText(card.getDescription());
        holder.consumeSummaryText.setText(card.getConsumeSummary());

        holder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

        // Open detail page on CardView click
        holder.cardView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, MedicineDetailActivity.class);
//            intent.putExtra("id", card.getId());
//            intent.putExtra("name", card.getName());
//            intent.putExtra("description", card.getDescription());
//            intent.putExtra("consumeSummary", card.getConsumeSummary());
//            context.startActivity(intent);
        });

        holder.editIcon.setOnClickListener(v -> {
            // Handle edit click
            Intent intent = new Intent(context, MedicineEditActivity.class);
            intent.putExtra("id", card.getId()); // parsing id to edit activity
            intent.putExtra("diseaseId", card.getDiseaseId()); // parsing id to edit activity
            context.startActivity(intent);
        });

        holder.deleteIcon.setOnClickListener(v -> {
            // Handle delete click
            new AlertDialog.Builder(context)
                    .setTitle("Delete Confirmation")
                    .setMessage("Are you sure you want to delete \""+card.getName()+"\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete & Remove item from the list
                        MedicineTable.deleteMedicine(card.getId()); // delete by id
                        cardList.remove(position); // remove from list
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, cardList.size());
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
        TextView nameText, descriptionText, consumeSummaryText;
        ImageView editIcon, deleteIcon;
        CardView cardView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.medicineName);
            consumeSummaryText = itemView.findViewById(R.id.medicineConsumeSummary);
            descriptionText = itemView.findViewById(R.id.medicineDescription);
            editIcon = itemView.findViewById(R.id.editIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
