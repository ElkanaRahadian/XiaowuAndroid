package com.example.a2101658224_ba26_project.database;

public class DbConfig {
    public static final String DATABASE_NAME = "xiaowu.db";
    public static int CURRENT_DB_VERSION = 1;

    public static class UserDb {
        public static String TABLE_NAME = "users";
        public static String USER_ID = "userId";
        public static String USERNAME = "username";
        public static String PASSWORD = "password";
        public static String PHONE_NUMBER = "phoneNumber";
        public static String EMAIL = "email";
        public static String OTP = "otp";
        public static String VERIFIED = "verified";
    }

    public static class TransactionDb {
        public static String TABLE_NAME = "transactions";
        public static String TRANSACTION_ID = "transactionId";
        public static String USER_ID = "userId";
        public static String PRODUCT_ID = "productId";
        public static String TRANSACTION_DATE = "trasactionDate";
    }

    public static class ProductDb {
        public static String TABLE_NAME = "products";
        public static String PRODUCT_ID = "productId";
        public static String PRODUCT_NAME = "productName";
        public static String PRODUCT_IMAGE_LINK = "productImageLink";
        public static String PRODUCT_SPECS = "productSpecs";
        public static String PRODUCT_PRICE = "productPrice";
    }
}
