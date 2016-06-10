package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;

import com.sarahehabm.carbcalculator.common.ItemsAlertDialog;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;

import static com.sarahehabm.carbcalculator.common.ItemsAlertDialog.DIALOG_FAVORITE;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class FavoritesItemsPagerFragment extends ItemsBasePagerFragment {

    @Override
    public Cursor getCursor() {
        Cursor cursor = getContext().getContentResolver().query(ItemEntry.CONTENT_URI, null,
                ItemEntry.COLUMN_FAVORITE + " = ? ",
                new String[]{String.valueOf(1)},
                ItemEntry.COLUMN_NAME + " ASC");
        return cursor;
    }

    @Override
    public void onLongClick(int position, int itemId) {
//        super.onLongClick(position, itemId);
//        Toast.makeText(getContext(), "FavoriteItems; should create dialog with " +
//                "remove from favorites & edit item", Toast.LENGTH_SHORT).show();
//        showDialog(DIALOG_FAVORITE, itemId);
        alertDialog = new ItemsAlertDialog(getContext(), DIALOG_FAVORITE, itemId, this);
        alertDialog.showDialog();
    }
}