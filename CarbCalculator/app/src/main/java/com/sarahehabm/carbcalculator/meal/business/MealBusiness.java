package com.sarahehabm.carbcalculator.meal.business;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.sarahehabm.carbcalculator.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.Item;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 28-May-16.
 */

public class MealBusiness {

    public static Cursor filter(Context context, CharSequence filterKey) {
        if (filterKey == null)
            filterKey = "";

        String select = ItemEntry.COLUMN_NAME + " LIKE ? ";
        String[] selectArgs = {"%" + filterKey + "%"};

        return context.getContentResolver().
                query(ItemEntry.CONTENT_URI, null, select, selectArgs, null);
    }

    public static Cursor filter(Context context, CharSequence filterKey, ArrayList<Item> excludeItems) {
        if (filterKey == null)
            filterKey = "";

//        String select = ItemEntry.COLUMN_NAME + " LIKE ? ";
        String select = computeSelect(excludeItems);
//        String[] selectArgs = {"%" + filterKey + "%"};
        String[] selectArgs = computeSelectArgs(filterKey, excludeItems);

        return context.getContentResolver().
                query(ItemEntry.CONTENT_URI, null, select, selectArgs, null);
    }

    private static String computeSelect(ArrayList<Item> excludeItems) {
        String select = ItemEntry.COLUMN_NAME + " LIKE ? ";

        if(excludeItems==null || excludeItems.size()==0 || excludeItems.isEmpty())
            return select;

        select += " AND NOT (";
        for (int i=0; i<excludeItems.size()-1; i++) {
            select += ItemEntry.COLUMN_ID + " = ? OR ";
        }
        select += ItemEntry.COLUMN_ID + " = ? )";

        return select;
    }

    private static String[] computeSelectArgs(CharSequence filterKey, ArrayList<Item> excludeItems) {
        ArrayList<String> selectArgsArrayList = new ArrayList<>();
        selectArgsArrayList.add("%" + filterKey + "%");


        if (excludeItems != null && excludeItems.size() != 0 && !excludeItems.isEmpty()) {
            for (int i=0; i<excludeItems.size(); i++)
                selectArgsArrayList.add(String.valueOf(excludeItems.get(i).getId()));
        }

        String[] selectArgs = new String[selectArgsArrayList.size()];
        selectArgs = selectArgsArrayList.toArray(selectArgs);

        return selectArgs;
    }

    public static CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(
                context, ItemEntry.CONTENT_URI, null, null, null, null
        );
    }
}
