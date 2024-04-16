package com.example.a2101658224_ba26_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.a2101658224_ba26_project.model.TransactionHistory;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryDatabase {
    private final Context context;
    public TransactionHistoryDatabase(Context context) {
        this.context = context;
    }

    public long insertTransactionHistory(TransactionHistory history) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConfig.TransactionDb.TRANSACTION_DATE, history.getTransactionDate());
        contentValues.put(DbConfig.TransactionDb.PRODUCT_ID, history.getPhoneId());
        contentValues.put(DbConfig.TransactionDb.USER_ID, 1);

        long id = -1;
        try {
            id = database.insertOrThrow(DbConfig.TransactionDb.TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
        return id;
    }

    public List<TransactionHistory> getAllTransactionHistory() {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        String getAllPhonesQuery = "SELECT * FROM " + DbConfig.TransactionDb.TABLE_NAME;

        ArrayList<TransactionHistory> histories = new ArrayList<>();
        try (Cursor cursor = database.rawQuery(getAllPhonesQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    TransactionHistory history = getTransactionHistory(cursor);
                    histories.add(history);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return histories;
    }

    public void deleteTransactionHistory(int transactionHistoryId) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        String query = "delete from " + DbConfig.TransactionDb.TABLE_NAME + " where " + DbConfig.TransactionDb.TRANSACTION_ID + " = '" + transactionHistoryId + "'";
        try {
            database.execSQL(query);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        database.close();
    }

    private TransactionHistory getTransactionHistory(Cursor cursor) {
        return new TransactionHistory(
                cursor.getInt(cursor.getColumnIndexOrThrow(DbConfig.TransactionDb.TRANSACTION_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DbConfig.TransactionDb.PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.TransactionDb.TRANSACTION_DATE))
        );
    }
}
