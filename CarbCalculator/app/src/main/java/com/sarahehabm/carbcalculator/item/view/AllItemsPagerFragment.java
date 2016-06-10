package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;

import com.sarahehabm.carbcalculator.common.ItemsAlertDialog;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;

import static com.sarahehabm.carbcalculator.common.ItemsAlertDialog.DIALOG_ALL;
import static com.sarahehabm.carbcalculator.common.ItemsAlertDialog.DIALOG_FAVORITE;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class AllItemsPagerFragment extends ItemsBasePagerFragment {

    @Override
    public Cursor getCursor() {
        Cursor cursor = getContext().getContentResolver().query(ItemEntry.CONTENT_URI, null,
                null, null,
                ItemEntry.COLUMN_NAME + " ASC");
        return cursor;
    }

    /*@Override
    public ItemsAlertDialog initializeDialog() {
        return null;
    }*/

    @Override
    public void onLongClick(int position, int itemId) {
//        super.onLongClick(position, itemId);
//        Toast.makeText(getContext(), "AllItems; should create dialog with " +
//                "add/remove to/from favorites & edit item", Toast.LENGTH_SHORT).show();
        if(isFavorite(position)) {
//            showDialog(DIALOG_FAVORITE, itemId);
            alertDialog = new ItemsAlertDialog(getContext(), DIALOG_FAVORITE, itemId, this);
        } else {
//            showDialog(DIALOG_ALL, itemId);
            alertDialog = new ItemsAlertDialog(getContext(), DIALOG_ALL, itemId, this);
        }
        alertDialog.showDialog();
    }
}
