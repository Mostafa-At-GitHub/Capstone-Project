package com.sarahehabm.carbcalculator;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sarah E. Mostafa on 13-May-16.
 */
public class CarbCounterContract {
    public static final String CONTENT_AUTHORITY = "com.sarahehabm.carbcalculator";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEM = "item";
    public static final String PATH_AMOUNT = "amount";
    public static final String PATH_ITEM_AMOUNT = "item_amount";
    public static final String PATH_MEAL = "meal";

    public static final class ItemEntry implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "item";

        //Table columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FAVORITE = "isFavorite";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
//                + COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_FAVORITE + " INTEGER"
                + ")";

        public static void createTable(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE);
        }
    }

    public static final class AmountEntry implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "amount";

        //Table columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CARB_GRAMS = "carb_grams";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_ITEM_ID = "item_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_AMOUNT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_AMOUNT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_AMOUNT;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CARB_GRAMS + " INTEGER NOT NULL, "
                + COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + COLUMN_UNIT + " TEXT NOT NULL, "
                + COLUMN_ITEM_ID + " INTEGER NOT NULL"
                + ")";

        public static void createTable(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE);
        }
    }

    public static final class ItemAmountEntry implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "item_amount";

        //Table columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_AMOUNT_ID = "amount_id";
        public static final String COLUMN_TOTAL_QUANTITY = "total_quantity";
        public static final String COLUMN_MEAL_ID = "meal_id";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM_AMOUNT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM_AMOUNT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM_AMOUNT;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ITEM_ID + " INTEGER NOT NULL, "
                + COLUMN_AMOUNT_ID + " INTEGER NOT NULL, "
                + COLUMN_TOTAL_QUANTITY + " INTEGER NOT NULL, "
                + COLUMN_MEAL_ID + " INTEGER NOT NULL"
                + ")";

        public static void createTable(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE);
        }
    }

    public static final class MealEntry implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "meal";

        //Table columns
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_TOTAL_CARBS = "total_carbs";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEAL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEAL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEAL;

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL, "
                + COLUMN_TOTAL_CARBS + " INTEGER NOT NULL"
                + ")";

        public static void createTable(SQLiteDatabase database) {
            database.execSQL(SQL_CREATE_TABLE);
        }
    }
}