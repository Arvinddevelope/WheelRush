package com.arvind.wheelrush.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arvind.wheelrush.R;
import com.arvind.wheelrush.models.TransactionModel;

import java.util.List;

/**
 * Adapter to display transaction history in a RecyclerView.
 */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<TransactionModel> list;

    public TransactionAdapter(List<TransactionModel> list) {
        this.list = list;
    }

    /**
     * ViewHolder class for RecyclerView items.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textType, textAmount, textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textType = itemView.findViewById(R.id.textType);
            textAmount = itemView.findViewById(R.id.textAmount);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel model = list.get(position);

        holder.textType.setText(model.getType());
        holder.textAmount.setText("₹" + model.getAmount());
        holder.textDate.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
