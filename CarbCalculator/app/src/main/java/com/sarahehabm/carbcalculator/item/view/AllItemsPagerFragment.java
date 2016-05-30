package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.*;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class AllItemsPagerFragment extends ItemsBasePagerFragment {

    @Override
    public Cursor getCursor() {
        Cursor cursor = getContext().getContentResolver().query(ItemEntry.CONTENT_URI, null,
                null, null, null);
        return cursor;
    }
}
