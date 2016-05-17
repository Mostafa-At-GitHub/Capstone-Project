package com.sarahehabm.carbcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.sarahehabm.carbcalculator.CarbCounterContract.*;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 17-May-16.
 */
public class CarbCounterInterface {
    private static final String TAG = CarbCounterInterface.class.getSimpleName();

    public static Uri insertItem(Context context, Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemEntry.COLUMN_ID, item.getId());
        contentValues.put(ItemEntry.COLUMN_NAME, item.getName());
        contentValues.put(ItemEntry.COLUMN_FAVORITE, item.isFavorite());

        return context.getContentResolver().insert(ItemEntry.CONTENT_URI, contentValues);
    }

    public static Item getItem(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(ItemEntry.CONTENT_URI, null,
                ItemEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getItem count: " + cursor.getCount());

        Item item = new Item();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ID));
            String itemName = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));
            int favoriteInt = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_FAVORITE));
            boolean isFavorite = favoriteInt == 1;

            item = new Item(itemId, itemName, isFavorite);
        }

        cursor.close();
        return item;
    }

    public static ArrayList<Item> getAllItems(Context context) {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(ItemEntry.CONTENT_URI, null, null, null, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getItems count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ID));
            String itemName = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));
            int favoriteInt = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_FAVORITE));
            boolean isFavorite = favoriteInt == 1;

            Item item = new Item(itemId, itemName, isFavorite);
            items.add(item);
        }

        cursor.close();
        return items;
    }

    public static Uri insertMeal(Context context, Meal meal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MealEntry.COLUMN_ID, meal.getId());
        contentValues.put(MealEntry.COLUMN_TITLE, meal.getName());
        contentValues.put(MealEntry.COLUMN_TOTAL_CARBS, meal.getTotalCarbs());
        contentValues.put(MealEntry.COLUMN_TIMESTAMP, meal.getTimestamp());

        return context.getContentResolver().insert(MealEntry.CONTENT_URI, contentValues);
    }

    public static Meal getMeal(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(MealEntry.CONTENT_URI, null,
                MealEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getMeal count: " + cursor.getCount());

//        String result = "";
        Meal meal = new Meal();
        while (cursor.moveToNext()) {
            int mealId = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_ID));
            String mealName = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TITLE));
            int totalCarbs = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
            long timestamp = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TIMESTAMP));

            meal = new Meal(mealId, mealName, totalCarbs, timestamp);
        }

        cursor.close();
        return meal;
    }

    public static ArrayList<Meal> getAllMeals(Context context) {
        ArrayList<Meal> meals = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(MealEntry.CONTENT_URI, null, null, null, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getMeals count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int mealId = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_ID));
            String mealName = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TITLE));
            int totalCarbs = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
            long timestamp = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TIMESTAMP));

            Meal meal = new Meal(mealId, mealName, totalCarbs, timestamp);
            meals.add(meal);
        }

        cursor.close();
        return meals;
    }

    public static Uri insertAmount(Context context, Amount amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AmountEntry.COLUMN_ID, amount.getId());
        contentValues.put(AmountEntry.COLUMN_CARB_GRAMS, amount.getCarbGrams());
        contentValues.put(AmountEntry.COLUMN_QUANTITY, amount.getQuantity());
        contentValues.put(AmountEntry.COLUMN_UNIT, amount.getUnit());
        contentValues.put(AmountEntry.COLUMN_ITEM_ID, amount.getItemId());

        return context.getContentResolver().insert(AmountEntry.CONTENT_URI, contentValues);
    }

    public static Amount getAmount(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(AmountEntry.CONTENT_URI, null,
                AmountEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getAmount count: " + cursor.getCount());

        Amount amount = new Amount();
        while (cursor.moveToNext()) {
            int amountId = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_ID));
            int carbGrams = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_CARB_GRAMS));
            int quantity = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndex(AmountEntry.COLUMN_UNIT));
            int itemId = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_ITEM_ID));

            amount = new Amount(amountId, carbGrams, quantity, unit, itemId);
        }

        cursor.close();
        return amount;
    }

    public static ArrayList<Amount> getAllAmounts(Context context) {
        ArrayList<Amount> amounts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(AmountEntry.CONTENT_URI, null, null, null, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getAmounts count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int amountId = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_ID));
            int carbGrams = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_CARB_GRAMS));
            int quantity = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndex(AmountEntry.COLUMN_UNIT));
            int itemId = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_ITEM_ID));

            Amount amount = new Amount(amountId, carbGrams, quantity, unit, itemId);
            amounts.add(amount);
        }

        cursor.close();
        return amounts;
    }

    public static Uri insertItemAmount(Context context, ItemAmount itemAmount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemAmountEntry.COLUMN_ID, itemAmount.getId());
        contentValues.put(ItemAmountEntry.COLUMN_ITEM_ID, itemAmount.getItemId());
        contentValues.put(ItemAmountEntry.COLUMN_AMOUNT_ID, itemAmount.getAmountId());
        contentValues.put(ItemAmountEntry.COLUMN_MEAL_ID, itemAmount.getMealId());
        contentValues.put(ItemAmountEntry.COLUMN_TOTAL_QUANTITY, itemAmount.getTotalQuantity());

        return context.getContentResolver().insert(ItemAmountEntry.CONTENT_URI, contentValues);
    }

    public static ItemAmount getItemAmount(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(ItemAmountEntry.CONTENT_URI, null,
                ItemAmountEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getItemAmount count: " + cursor.getCount());

        ItemAmount itemAmount = new ItemAmount();
        while (cursor.moveToNext()) {
            int itemAmountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ID));
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ITEM_ID));
            int amountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_AMOUNT_ID));
            int mealId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_MEAL_ID));
            int totalQuantity = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_QUANTITY));

            itemAmount = new ItemAmount(itemAmountId, itemId, amountId, totalQuantity, mealId);
        }

        cursor.close();
        return itemAmount;
    }

    public static ArrayList<ItemAmount> getAllItemAmounts(Context context) {
        ArrayList<ItemAmount> itemAmounts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(ItemAmountEntry.CONTENT_URI, null, null, null, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Log.v(TAG, "getItemAmounts count: " + cursor.getCount());

        while (cursor.moveToNext()) {
            int itemAmountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ID));
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ITEM_ID));
            int amountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_AMOUNT_ID));
            int mealId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_MEAL_ID));
            int totalQuantity = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_QUANTITY));

            ItemAmount itemAmount = new ItemAmount(itemAmountId, itemId, amountId, totalQuantity, mealId);
            itemAmounts.add(itemAmount);
        }

        cursor.close();
        return itemAmounts;
    }

}
