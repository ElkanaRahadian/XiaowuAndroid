package com.example.a2101658224_ba26_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a2101658224_ba26_project.adapter.ProductAdapter;
import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.database.products.ProductDatabase;
import com.example.a2101658224_ba26_project.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton logoutButton;
    private RecyclerView recyclerView;
    private final ProductAdapter productAdapter = new ProductAdapter(product -> {
        Intent detailIntent = new Intent(HomeActivity.this, PhoneDetailActivity.class);
        detailIntent.putExtra("productId", product.getId());
        startActivity(detailIntent);
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_item_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initializePhoneList();
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> showLogoutConfirmationDialog());
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout ?")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(HomeActivity.this, "You've Been Logged Out", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.itemViewAboutUsPage) {
            Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.itemViewHistoryPage) {
            Intent intent = new Intent(HomeActivity.this, TransactionHistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializePhoneList() {
        ProductDatabase db = new ProductDatabase(this);
        boolean isProductListEmpty = db.getSize() == 0;
        if (isProductListEmpty) {
            // get from API
            Log.e("HomeActivityDebug", "ambil dari server/API");
            String url = "https://api.npoint.io/b34ecacafc7f6f6bb0a1";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null, response -> {
                ArrayList<Product> products = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Product product = new Product(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getInt("price"),
                                jsonObject.getString("specs"),
                                jsonObject.getString("imageLink")
                        );
                        products.add(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                productAdapter.addProducts(products);
                db.saveProducts(products);
            }, error -> {

            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        } else {
            Log.e("HomeActivityDebug", "ambil dari lokal");
            List<Product> productList = db.getAllProducts();
            productAdapter.addProducts(productList);
        }

        recyclerView.setAdapter(productAdapter);
    }
}