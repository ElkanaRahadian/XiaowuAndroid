package com.example.a2101658224_ba26_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2101658224_ba26_project.database.products.ProductDatabase;
import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.model.Product;
import com.example.a2101658224_ba26_project.model.TransactionHistory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder> {
    private ArrayList<TransactionHistory> transactionHistories = new ArrayList<>();
    @Nullable
    private TransactionHistoryListener historyListener;

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction_history, parent, false);
        return new TransactionHistoryAdapter.TransactionHistoryViewHolder(view, historyListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {
        holder.bind(transactionHistories.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionHistories.size();
    }

    public void replaceAll(List<TransactionHistory> transactionHistories) {
        this.transactionHistories.clear();
        this.transactionHistories.addAll(transactionHistories);
        notifyDataSetChanged();
    }

    public void setHistoryListener(@Nullable TransactionHistoryListener historyListener) {
        this.historyListener = historyListener;
    }

    public static class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView transactionId;
        TextView phoneId;
        ImageView phoneImage;
        TextView phoneName;
        TextView productSpecs;
        TextView productPrice;
        TextView longitude;
        TextView latitude;
        TextView transactionDate;
        TextView delete;
        @Nullable
        private final TransactionHistoryListener historyListener;

        public TransactionHistoryViewHolder(@NonNull View itemView, @Nullable TransactionHistoryListener listener) {
            super(itemView);
            phoneImage = itemView.findViewById(R.id.productImageIV);
            transactionId = itemView.findViewById(R.id.transactionId);
            phoneId = itemView.findViewById(R.id.phoneIdTV);
            phoneName = itemView.findViewById(R.id.phoneNameTV);
            productSpecs = itemView.findViewById(R.id.productSpecsTV);
            productPrice = itemView.findViewById(R.id.productPriceTV);
            longitude = itemView.findViewById(R.id.longitudeTV);
            latitude = itemView.findViewById(R.id.latitudeTV);
            transactionDate = itemView.findViewById(R.id.transactionDateTV);
            delete = itemView.findViewById(R.id.deleteHistory);
            historyListener = listener;
        }

        public void bind(TransactionHistory transactionHistory) {
            transactionId.setText(String.valueOf(transactionHistory.getTransactionId()));
            Product product = new ProductDatabase(itemView.getContext()).findProduct(transactionHistory.getPhoneId());
            if (product != null) {
                phoneId.setText(String.valueOf(product.getId()));
                phoneName.setText(product.getName());
                productSpecs.setText(product.getProductSpecs());
                productPrice.setText(String.valueOf(product.getProductPrice()));
                Picasso.get().load(product.getImageLink()).into(phoneImage);
            }
            transactionDate.setText(transactionHistory.getTransactionDate());

            delete.setOnClickListener(view -> {
                if (historyListener != null) {
                    historyListener.onDeleteHistory(transactionHistory);
                }
            });
            itemView.setOnClickListener(view -> {
                if (historyListener != null) {
                    historyListener.onHistoryClicked(transactionHistory);
                }
            });
        }
    }

    public interface TransactionHistoryListener {
        void onDeleteHistory(TransactionHistory history);

        void onHistoryClicked(TransactionHistory history);
    }
}
