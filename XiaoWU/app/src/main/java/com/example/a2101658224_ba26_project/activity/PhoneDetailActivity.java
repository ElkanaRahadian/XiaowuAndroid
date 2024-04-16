package com.example.a2101658224_ba26_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2101658224_ba26_project.database.products.ProductDatabase;
import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.database.TransactionHistoryDatabase;
import com.example.a2101658224_ba26_project.model.Product;
import com.example.a2101658224_ba26_project.model.TransactionHistory;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PhoneDetailActivity extends AppCompatActivity {
    private ImageView phoneImageIV;
    private TextView phoneIdTV;
    private TextView phoneNameTV;
    private TextView productSpecsTV;
    private TextView productPriceTV;
    private TextView longitudeTV;
    private TextView latitudeTV;
    private Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        phoneImageIV = findViewById(R.id.productImageIV);
        phoneNameTV = findViewById(R.id.phoneNameTV);
        phoneIdTV = findViewById(R.id.phoneIdTV);
        productSpecsTV = findViewById(R.id.productSpecsTV);
        productPriceTV = findViewById(R.id.productPriceTV);
        longitudeTV = findViewById(R.id.longitudeTV);
        latitudeTV = findViewById(R.id.latitudeTV);
        buyBtn = findViewById(R.id.buyBtn);

        int productId = getIntent().getIntExtra("productId", -1);
        Product product = new ProductDatabase(this).findProduct(productId);
        if (product != null) {
            setDataToUI(product);
        }
    }

    private void setDataToUI(Product product) {
        Picasso
                .get()
                .load(product.getImageLink())
                .into(phoneImageIV);
        phoneIdTV.setText(String.valueOf(product.getId()));
        phoneNameTV.setText(product.getName());
        productSpecsTV.setText(product.getProductSpecs());
        productPriceTV.setText(String.valueOf(product.getProductPrice()));
        buyBtn.setOnClickListener(v -> {
            Calendar rightNow = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.US);
            TransactionHistory transactionHistory = new TransactionHistory(
                    product.getId(),
                    simpleDateFormat.format(rightNow.getTime())
            );
            long transactionHistoryId = new TransactionHistoryDatabase(this).insertTransactionHistory(transactionHistory);
            if (transactionHistoryId != -1) {
                Toast.makeText(this, "The product was successfully purchased", Toast.LENGTH_SHORT).show();
            }
        });
    }
}