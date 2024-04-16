package com.example.a2101658224_ba26_project.database.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a2101658224_ba26_project.database.DatabaseHelper;
import com.example.a2101658224_ba26_project.database.DbConfig;
import com.example.a2101658224_ba26_project.model.User;

public class UserDatabase {
    private final Context context;

    public UserDatabase(Context context) {
        this.context = context;
    }

    public long insertUser(@NonNull User user) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConfig.UserDb.USERNAME, user.getUsername());
        contentValues.put(DbConfig.UserDb.PASSWORD, user.getPassword());
        contentValues.put(DbConfig.UserDb.EMAIL, user.getEmail());
        contentValues.put(DbConfig.UserDb.PHONE_NUMBER, user.getPhoneNumber());
        contentValues.put(DbConfig.UserDb.OTP, user.getOtp());
        contentValues.put(DbConfig.UserDb.VERIFIED, user.isVerified());

        long id = -1;
        try {
            id = database.insertOrThrow(DbConfig.UserDb.TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
        return id;
    }

    @Nullable
    public User findUser(String username, String password) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        String findUserQuery = "SELECT * FROM " + DbConfig.UserDb.TABLE_NAME
                + " WHERE "
                + DbConfig.UserDb.USERNAME + " = '" + username + "'"
                + " AND "
                + DbConfig.UserDb.PASSWORD + " = '" + password + "'"
                + " AND "
                + DbConfig.UserDb.VERIFIED + " = 'true'";

        try (Cursor cursor = database.rawQuery(findUserQuery, null)) {
            if (cursor.moveToFirst()) {
                return new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DbConfig.UserDb.USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.UserDb.USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.UserDb.PHONE_NUMBER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.UserDb.EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.UserDb.OTP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.UserDb.VERIFIED)).equals("true")
                );
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public String getOtpForUser(int userId) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        String findUserQuery = "SELECT * FROM " + DbConfig.UserDb.TABLE_NAME
                + " WHERE "
                + DbConfig.UserDb.USER_ID + " = '" + userId + "'";

        try (Cursor cursor = database.rawQuery(findUserQuery, null)) {
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.UserDb.OTP));
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateIsVerifyForUser(int userId, boolean isVerified) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        String strSQL = "UPDATE " + DbConfig.UserDb.TABLE_NAME +
                " SET " + DbConfig.UserDb.VERIFIED + " = '" + isVerified + "'" +
                " WHERE " + DbConfig.UserDb.USER_ID + " = '" + userId + "'";

        try {
            database.execSQL(strSQL);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean updateOtp(int userId, String newOtp) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        String strSQL = "UPDATE " + DbConfig.UserDb.TABLE_NAME +
                " SET " + DbConfig.UserDb.OTP + " = '" + newOtp + "'" +
                " WHERE " + DbConfig.UserDb.USER_ID + " = '" + userId + "'";

        try {
            database.execSQL(strSQL);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

}
