package com.sarahehabm.carbcalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sarahehabm.carbcalculator.CarbCounterContract.*;

/**
 * Created by Sarah E. Mostafa on 13-May-16.
 */
public class CarbCounterDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CarbCounter.db";

    public CarbCounterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ItemEntry.createTable(db);
        AmountEntry.createTable(db);
        ItemAmountEntry.createTable(db);
        MealEntry.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AmountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemAmountEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MealEntry.TABLE_NAME);

        onCreate(db);
    }
}
