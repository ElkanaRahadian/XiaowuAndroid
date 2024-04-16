package com.example.a2101658224_ba26_project.database.products;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a2101658224_ba26_project.database.DatabaseHelper;
import com.example.a2101658224_ba26_project.database.DbConfig;
import com.example.a2101658224_ba26_project.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private final Context context;

    public ProductDatabase(Context context) {
        this.context = context;
    }

    public long getSize() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, DbConfig.ProductDb.TABLE_NAME);
        db.close();
        return count;
    }

    public boolean saveProducts(List<Product> products) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getWritableDatabase();

        boolean isAllSuccessful = true;
        try {
            db.beginTransaction();
            for (Product product : products) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbConfig.ProductDb.PRODUCT_ID, product.getId());
                contentValues.put(DbConfig.ProductDb.PRODUCT_NAME, product.getName());
                contentValues.put(DbConfig.ProductDb.PRODUCT_PRICE, product.getProductPrice());
                contentValues.put(DbConfig.ProductDb.PRODUCT_SPECS, product.getProductSpecs());
                contentValues.put(DbConfig.ProductDb.PRODUCT_IMAGE_LINK, product.getImageLink());
                long status = db.insert(DbConfig.ProductDb.TABLE_NAME, null, contentValues);

                if (status == -1) {
                    isAllSuccessful = false;
                    break;
                }
            }
            if (isAllSuccessful) {
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
            db.close();
        }
        return isAllSuccessful;
    }

    public List<Product> getAllProducts() {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        String getAllPhonesQuery = "SELECT * FROM " + DbConfig.ProductDb.TABLE_NAME;

        ArrayList<Product> products = new ArrayList<>();
        try (Cursor cursor = database.rawQuery(getAllPhonesQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Product product = getProduct(cursor);
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Nullable
    public Product findProduct(int id) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        String findUserQuery = "SELECT * FROM " + DbConfig.ProductDb.TABLE_NAME
                + " WHERE "
                + DbConfig.ProductDb.PRODUCT_ID + " = " + id;

        try (Cursor cursor = database.rawQuery(findUserQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    return getProduct(cursor);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    private static Product getProduct(Cursor cursor) {
        return new Product(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4)
        );
    }
}
