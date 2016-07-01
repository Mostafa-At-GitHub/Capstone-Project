package com.sarahehabm.carbcalculator.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sarah E. Mostafa on 13-May-16.
 */
public class CarbCounterDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "CarbCounter.db";

    public CarbCounterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CarbCounterContract.ItemEntry.createTable(db);
        CarbCounterContract.AmountEntry.createTable(db);
        CarbCounterContract.ItemAmountEntry.createTable(db);
        CarbCounterContract.MealEntry.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CarbCounterContract.ItemEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CarbCounterContract.AmountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CarbCounterContract.ItemAmountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CarbCounterContract.MealEntry.TABLE_NAME);

        onCreate(db);
    }
}
