package com.example.a2101658224_ba26_project.database;

import static com.example.a2101658224_ba26_project.database.DbConfig.CURRENT_DB_VERSION;
import static com.example.a2101658224_ba26_project.database.DbConfig.DATABASE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static volatile DatabaseHelper databaseHelper;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, CURRENT_DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + DbConfig.UserDb.TABLE_NAME + "("
                + DbConfig.UserDb.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DbConfig.UserDb.USERNAME + " TEXT NOT NULL UNIQUE, "
                + DbConfig.UserDb.PASSWORD + " TEXT NOT NULL, "
                + DbConfig.UserDb.PHONE_NUMBER + " TEXT NOT NULL UNIQUE, "
                + DbConfig.UserDb.EMAIL + " TEXT NOT NULL UNIQUE, "
                + DbConfig.UserDb.OTP + " TEXT NOT NULL, "
                + DbConfig.UserDb.VERIFIED + " TEXT NOT NULL"
                + ")";

        String createProductTable = "CREATE TABLE " + DbConfig.ProductDb.TABLE_NAME + "("
                + DbConfig.ProductDb.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DbConfig.ProductDb.PRODUCT_NAME + " TEXT NOT NULL, "
                + DbConfig.ProductDb.PRODUCT_PRICE + " INTEGER NOT NULL, "
                + DbConfig.ProductDb.PRODUCT_SPECS + " TEXT NOT NULL, "
                + DbConfig.ProductDb.PRODUCT_IMAGE_LINK + " TEXT NOT NULL"
                + ")";

        String createTransactionTable = "CREATE TABLE " + DbConfig.TransactionDb.TABLE_NAME + "("
                + DbConfig.TransactionDb.TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DbConfig.TransactionDb.USER_ID + " TEXT NOT NULL, "
                + DbConfig.TransactionDb.PRODUCT_ID + " INTEGER NOT NULL, "
                + DbConfig.TransactionDb.TRANSACTION_DATE + " TEXT NOT NULL, "
                + "FOREIGN KEY (" + DbConfig.TransactionDb.USER_ID + ") REFERENCES " + DbConfig.UserDb.TABLE_NAME + "(" + DbConfig.UserDb.USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + DbConfig.TransactionDb.PRODUCT_ID + ") REFERENCES " + DbConfig.ProductDb.TABLE_NAME + "(" + DbConfig.ProductDb.PRODUCT_ID + ") ON UPDATE CASCADE ON DELETE CASCADE"
                + ")";

        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createProductTable);
        sqLiteDatabase.execSQL(createTransactionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
