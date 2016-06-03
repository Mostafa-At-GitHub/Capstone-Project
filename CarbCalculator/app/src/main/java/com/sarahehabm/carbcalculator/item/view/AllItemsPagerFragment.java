package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;

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

    @Override
    public void onLongClick(int position, int itemId) {
//        super.onLongClick(position, itemId);
//        Toast.makeText(getContext(), "AllItems; should create dialog with " +
//                "add/remove to/from favorites & edit item", Toast.LENGTH_SHORT).show();
        if(isFavorite(position))
            showDialog(DIALOG_FAVORITE, itemId);
        else
            showDialog(DIALOG_ALL, itemId);
    }
}
