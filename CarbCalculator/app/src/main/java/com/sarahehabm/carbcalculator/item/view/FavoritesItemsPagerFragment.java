package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class FavoritesItemsPagerFragment extends ItemsBasePagerFragment {

    @Override
    public Cursor getCursor() {
        Cursor cursor = getContext().getContentResolver().query(ItemEntry.CONTENT_URI, null,
                ItemEntry.COLUMN_FAVORITE + " = ? ",
                new String[]{String.valueOf(1)},
                null);
        return cursor;
    }
}