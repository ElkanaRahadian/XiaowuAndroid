package com.example.a2101658224_ba26_project.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2101658224_ba26_project.R;
import com.example.a2101658224_ba26_project.adapter.TransactionHistoryAdapter;
import com.example.a2101658224_ba26_project.database.TransactionHistoryDatabase;
import com.example.a2101658224_ba26_project.model.TransactionHistory;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final TransactionHistoryAdapter adapter = new TransactionHistoryAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        recyclerView = findViewById(R.id.transactionHistoryRecyclerView);
        initializeTransactionList();
    }

    private void initializeTransactionList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(new TransactionHistoryDatabase(this).getAllTransactionHistory());
        adapter.setHistoryListener(new TransactionHistoryAdapter.TransactionHistoryListener() {
            @Override
            public void onDeleteHistory(TransactionHistory history) {
                AlertDialog dialog = new AlertDialog.Builder(TransactionHistoryActivity.this)
                        .setMessage("Are you sure you want to delete this transaction history " +
                                "with TransactionID " + history.getTransactionId() + " ?")
                        .setTitle("Delete Transaction History?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TransactionHistoryDatabase database = new TransactionHistoryDatabase(TransactionHistoryActivity.this);
                                database.deleteTransactionHistory(history.getTransactionId());
                                adapter.replaceAll(database.getAllTransactionHistory());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
            }

            @Override
            public void onHistoryClicked(TransactionHistory history) {
            }
        });
    }
}
