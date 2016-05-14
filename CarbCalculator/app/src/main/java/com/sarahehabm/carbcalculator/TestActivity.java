package com.sarahehabm.carbcalculator;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.sarahehabm.carbcalculator.CarbCounterContract.*;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void onInsertItemClick(View view) {
        ContentValues values = new ContentValues();
//        values.put(ItemEntry.COLUMN_ID, 715);
        values.put(ItemEntry.COLUMN_NAME, "Apple");
        values.put(ItemEntry.COLUMN_FAVORITE, 0);
        getContentResolver().insert(ItemEntry.CONTENT_URI, values);
    }

    public void onInsertAmountClick(View view) {
        ContentValues values = new ContentValues();
//        values.put(AmountEntry.COLUMN_ID, 0);
        values.put(AmountEntry.COLUMN_QUANTITY, 100);
        values.put(AmountEntry.COLUMN_UNIT, "grams");
        values.put(AmountEntry.COLUMN_CARB_GRAMS, 10);
        values.put(AmountEntry.COLUMN_ITEM_ID, 0);
        getContentResolver().insert(AmountEntry.CONTENT_URI, values);
    }

    public void onInsertItemAmountClick(View view) {
        ContentValues values = new ContentValues();
//        values.put(ItemAmountEntry.COLUMN_ID, 0);
        values.put(ItemAmountEntry.COLUMN_ITEM_ID, 0);
        values.put(ItemAmountEntry.COLUMN_AMOUNT_ID, 0);
        values.put(ItemAmountEntry.COLUMN_MEAL_ID, 0);
        values.put(ItemAmountEntry.COLUMN_TOTAL_QUANTITY, 200);
        getContentResolver().insert(ItemAmountEntry.CONTENT_URI, values);
    }

    public void onInsertMealClick(View view) {
        ContentValues values = new ContentValues();
//        values.put(MealEntry.COLUMN_ID, 0);
        values.put(MealEntry.COLUMN_TITLE, "Breakfast");
        values.put(MealEntry.COLUMN_TOTAL_CARBS, 20);
        values.put(MealEntry.COLUMN_TIMESTAMP, 1463150841);
        getContentResolver().insert(MealEntry.CONTENT_URI, values);
    }
}
