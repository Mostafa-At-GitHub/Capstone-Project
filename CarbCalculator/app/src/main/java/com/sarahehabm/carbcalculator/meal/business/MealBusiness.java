package com.sarahehabm.carbcalculator.meal.business;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.sarahehabm.carbcalculator.CarbCounterContract.ItemEntry;

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

    public static CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(
                context, ItemEntry.CONTENT_URI, null, null, null, null
        );
    }
}
