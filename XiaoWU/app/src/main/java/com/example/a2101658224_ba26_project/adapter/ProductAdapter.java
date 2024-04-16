package com.example.a2101658224_ba26_project.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.PhoneViewHolder> {
    private final ArrayList<Product> products = new ArrayList<>();
    private final ProductListener listener;

    public ProductAdapter(ProductListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = products.get(position);
        holder.bind(product);
        holder.itemView.setOnClickListener(v -> listener.onProductClick(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addProducts(List<Product> newProducts) {
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public static class PhoneViewHolder extends RecyclerView.ViewHolder {
        ImageView phoneImage;
        TextView phoneId;
        TextView phoneName;
        TextView productSpecs;
        TextView productPrice;
        TextView longitude;
        TextView latitude;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneImage = itemView.findViewById(R.id.productImageIV);
            phoneId = itemView.findViewById(R.id.phoneIdTV);
            phoneName = itemView.findViewById(R.id.phoneNameTV);
            productSpecs = itemView.findViewById(R.id.productSpecsTV);
            productPrice = itemView.findViewById(R.id.productPriceTV);
            longitude = itemView.findViewById(R.id.longitudeTV);
            latitude = itemView.findViewById(R.id.latitudeTV);
        }

        public void bind(@NonNull Product product) {
            Picasso.get()
                    .load(product.getImageLink())
                    .into(phoneImage);
            phoneId.setText(String.valueOf(product.getId()));
            phoneName.setText(product.getName());
            productSpecs.setText(product.getProductSpecs());
            productPrice.setText(String.valueOf(product.getProductPrice()));
        }
    }

    public interface ProductListener {
        void onProductClick(Product product);
    }
}
