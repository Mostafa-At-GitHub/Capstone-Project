package com.sarahehabm.carbcalculator.common.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.AmountEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemAmountEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.MealEntry;
import com.sarahehabm.carbcalculator.common.model.Amount;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.common.model.ItemAmount;
import com.sarahehabm.carbcalculator.common.model.Meal;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 17-May-16.
 */
public class CarbCounterInterface {
    private static final String TAG = CarbCounterInterface.class.getSimpleName();

    public static Uri insertItem(Context context, Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemEntry.COLUMN_NAME, item.getName());
        contentValues.put(ItemEntry.COLUMN_FAVORITE, item.isFavorite());

        return context.getContentResolver().insert(ItemEntry.CONTENT_URI, contentValues);
    }

    public static int updateItem(Context context, Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemEntry.COLUMN_ID, item.getId());
        contentValues.put(ItemEntry.COLUMN_FAVORITE, item.isFavorite());
        contentValues.put(ItemEntry.COLUMN_NAME, item.getName());

        return context.getContentResolver().update(ItemEntry.CONTENT_URI, contentValues,
                ItemEntry.COLUMN_ID + " = ? ", new String[]{String.valueOf(item.getId())});
    }

    public static int updateItemFavorite(Context context, int itemId, boolean isFavorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemEntry.COLUMN_FAVORITE, isFavorite? 1 : 0);

        return context.getContentResolver().update(ItemEntry.CONTENT_URI, contentValues,
                ItemEntry.COLUMN_ID + " = ? ", new String[]{String.valueOf(itemId)});
    }

    public static int insertItems(Context context, ArrayList<Item> items) {
        ContentValues[] contentValuesArray = new ContentValues[items.size()];
        for (int i = 0; i<items.size(); i++) {
            Item item = items.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(ItemEntry.COLUMN_NAME, item.getName());
            contentValues.put(ItemEntry.COLUMN_FAVORITE, item.isFavorite());

            contentValuesArray[i] = contentValues;
        }

        return context.getContentResolver().bulkInsert(ItemEntry.CONTENT_URI, contentValuesArray);
    }

    public static Item getItem(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(ItemEntry.CONTENT_URI, null,
                ItemEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

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
        contentValues.put(MealEntry.COLUMN_TITLE, meal.getName());
        contentValues.put(MealEntry.COLUMN_TOTAL_CARBS, meal.getTotalCarbs());
        long timestamp = meal.getTimestamp();
        contentValues.put(MealEntry.COLUMN_TIMESTAMP, String.valueOf(timestamp));

        return context.getContentResolver().insert(MealEntry.CONTENT_URI, contentValues);
    }

    public static int updateMealCarbs(Context context, int mealId, int totalCarbs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MealEntry.COLUMN_TOTAL_CARBS, totalCarbs);

        return context.getContentResolver().update(MealEntry.CONTENT_URI, contentValues,
                MealEntry.COLUMN_ID + " = ? ", new String[]{String.valueOf(mealId)});
    }

    public static Meal getMeal(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(MealEntry.CONTENT_URI, null,
                MealEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        Meal meal = new Meal();
        while (cursor.moveToNext()) {
            int mealId = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_ID));
            String mealName = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TITLE));
            int totalCarbs = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
            String timestampStr = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TIMESTAMP));
            long timestamp = Long.parseLong(timestampStr);

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

        while (cursor.moveToNext()) {
            int mealId = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_ID));
            String mealName = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TITLE));
            int totalCarbs = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
            String timestampStr = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TIMESTAMP));
            long timestamp = Long.parseLong(timestampStr);

            Meal meal = new Meal(mealId, mealName, totalCarbs, timestamp);
            meals.add(meal);
        }

        cursor.close();
        return meals;
    }

    public static Uri insertAmount(Context context, Amount amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AmountEntry.COLUMN_CARB_GRAMS, amount.getCarbGrams());
        contentValues.put(AmountEntry.COLUMN_QUANTITY, amount.getQuantity());
        contentValues.put(AmountEntry.COLUMN_UNIT, amount.getUnit());
        contentValues.put(AmountEntry.COLUMN_ITEM_ID, amount.getItemId());

        return context.getContentResolver().insert(AmountEntry.CONTENT_URI, contentValues);
    }

    public static int insertAmounts(Context context, ArrayList<Amount> amounts) {
        ContentValues[] contentValuesArray = new ContentValues[amounts.size()];
        for (int i = 0; i<amounts.size(); i++) {
            Amount amount = amounts.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(AmountEntry.COLUMN_CARB_GRAMS, amount.getCarbGrams());
            contentValues.put(AmountEntry.COLUMN_QUANTITY, amount.getQuantity());
            contentValues.put(AmountEntry.COLUMN_UNIT, amount.getUnit());
            contentValues.put(AmountEntry.COLUMN_ITEM_ID, amount.getItemId());

            contentValuesArray[i] = contentValues;
        }

        return context.getContentResolver().bulkInsert(AmountEntry.CONTENT_URI, contentValuesArray);
    }

    public static int insertOrUpdateAmounts(Context context, ArrayList<Amount> amounts) {
        int numInsertedUpdated = 0;
        for (int i = 0; i < amounts.size(); i++) {
            Amount amount = amounts.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(AmountEntry.COLUMN_CARB_GRAMS, amount.getCarbGrams());
            contentValues.put(AmountEntry.COLUMN_QUANTITY, amount.getQuantity());
            contentValues.put(AmountEntry.COLUMN_UNIT, amount.getUnit());
            contentValues.put(AmountEntry.COLUMN_ITEM_ID, amount.getItemId());


            Amount tempAmount = getAmount(context, amount.getId());
            if(tempAmount == null) { //Amount doesn't exist in DB; insert
                Uri insertUri = context.getContentResolver().insert(AmountEntry.CONTENT_URI, contentValues);
            } else { //Amount exists in DB; update
                numInsertedUpdated += context.getContentResolver().update(
                        AmountEntry.CONTENT_URI, contentValues, AmountEntry.COLUMN_ID + " = ? ",
                        new String[]{String.valueOf(tempAmount.getId())});
            }
        }

        return numInsertedUpdated;
    }

    public static Amount getAmount(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(AmountEntry.CONTENT_URI, null,
                AmountEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

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

    public static ArrayList<Amount> getAmountsByItemId(Context context, int itemId) {
        Cursor cursor = context.getContentResolver().query(AmountEntry.CONTENT_URI, null,
                AmountEntry.COLUMN_ITEM_ID + " = ? ", new String[]{String.valueOf(itemId)},
                null);

        if (cursor == null || cursor.getCount() <= 0)
            return null;

        ArrayList<Amount> amounts = new ArrayList<>();
        while (cursor.moveToNext()) {
            Amount amount = new Amount();

            int amountId = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_ID));
            int carbGrams = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_CARB_GRAMS));
            int quantity = cursor.getInt(cursor.getColumnIndex(AmountEntry.COLUMN_QUANTITY));
            String unit = cursor.getString(cursor.getColumnIndex(AmountEntry.COLUMN_UNIT));

            amount = new Amount(amountId, carbGrams, quantity, unit, itemId);
            amounts.add(amount);
        }

        cursor.close();
        return amounts;
    }

    public static String[] getAmountsByItemId_TEMP(Context context, int itemId) {
        ArrayList<Amount> amounts = getAmountsByItemId(context, itemId);
        if(amounts == null)
            return new String[]{};

        String[] arr = new String[amounts.size()];
        for (int i = 0; i < amounts.size(); i++) {
            arr[i] = /*amounts.get(i).getCarbGrams() + */amounts.get(i).getUnit();
        }

        return arr;
    }

    public static ArrayList<Amount> getAllAmounts(Context context) {
        ArrayList<Amount> amounts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(AmountEntry.CONTENT_URI, null, null, null, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

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
        contentValues.put(ItemAmountEntry.COLUMN_ITEM_ID, itemAmount.getItemId());
        contentValues.put(ItemAmountEntry.COLUMN_AMOUNT_ID, itemAmount.getAmountId());
        contentValues.put(ItemAmountEntry.COLUMN_MEAL_ID, itemAmount.getMealId());
        contentValues.put(ItemAmountEntry.COLUMN_TOTAL_WEIGHT, itemAmount.getTotalWeight());
        contentValues.put(ItemAmountEntry.COLUMN_TOTAL_QUANTITY, itemAmount.getTotalQuantity());

        return context.getContentResolver().insert(ItemAmountEntry.CONTENT_URI, contentValues);
    }

    public static int insertItemAmounts(Context context, ArrayList<ItemAmount> itemAmounts) {
        ContentValues[] contentValuesArray = new ContentValues[itemAmounts.size()];
        for (int i = 0; i<itemAmounts.size(); i++) {
            ItemAmount itemAmount = itemAmounts.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(ItemAmountEntry.COLUMN_ITEM_ID, itemAmount.getItemId());
            contentValues.put(ItemAmountEntry.COLUMN_AMOUNT_ID, itemAmount.getAmountId());
            contentValues.put(ItemAmountEntry.COLUMN_MEAL_ID, itemAmount.getMealId());
            contentValues.put(ItemAmountEntry.COLUMN_TOTAL_WEIGHT, itemAmount.getTotalWeight());
            contentValues.put(ItemAmountEntry.COLUMN_TOTAL_QUANTITY, itemAmount.getTotalQuantity());

            contentValuesArray[i] = contentValues;
        }

        return context.getContentResolver().bulkInsert(ItemAmountEntry.CONTENT_URI, contentValuesArray);
    }

    public static ItemAmount getItemAmount(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(ItemAmountEntry.CONTENT_URI, null,
                ItemAmountEntry.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        ItemAmount itemAmount = new ItemAmount();
        while (cursor.moveToNext()) {
            int itemAmountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ID));
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ITEM_ID));
            int amountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_AMOUNT_ID));
            int mealId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_MEAL_ID));
            int totalWeight = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_WEIGHT));
            int totalQuantity = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_QUANTITY));

            itemAmount = new
                    ItemAmount(itemAmountId, itemId, amountId, totalWeight, totalQuantity, mealId);
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

        while (cursor.moveToNext()) {
            int itemAmountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ID));
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ITEM_ID));
            int amountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_AMOUNT_ID));
            int mealId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_MEAL_ID));
            int totalWeight = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_WEIGHT));
            int totalQuantity = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_QUANTITY));

            ItemAmount itemAmount = new
                    ItemAmount(itemAmountId, itemId, amountId, totalWeight, totalQuantity, mealId);
            itemAmounts.add(itemAmount);
        }

        cursor.close();
        return itemAmounts;
    }

    public static ArrayList<ItemAmount> getItemAmountsNyMealId(Context context, int mealId) {
        ArrayList<ItemAmount> itemAmounts = new ArrayList<>();
        Cursor cursor = context.getContentResolver().
                query(ItemAmountEntry.CONTENT_URI, null,
                        ItemAmountEntry.COLUMN_MEAL_ID + " = ? ",
                        new String[]{String.valueOf(mealId)}, null);

        if(cursor == null || cursor.getCount() <= 0)
            return null;

        while (cursor.moveToNext()) {
            int itemAmountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ID));
            int itemId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_ITEM_ID));
            int amountId = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_AMOUNT_ID));
            int totalWeight = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_WEIGHT));
            int totalQuantity = cursor.getInt(cursor.getColumnIndex(ItemAmountEntry.COLUMN_TOTAL_QUANTITY));

            ItemAmount itemAmount = new
                    ItemAmount(itemAmountId, itemId, amountId, totalWeight, totalQuantity, mealId);
            itemAmounts.add(itemAmount);
        }

        cursor.close();
        return itemAmounts;
    }
}
