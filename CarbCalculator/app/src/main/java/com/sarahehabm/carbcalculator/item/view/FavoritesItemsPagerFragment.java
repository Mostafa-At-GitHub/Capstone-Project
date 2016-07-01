package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sarahehabm.carbcalculator.common.ItemsAlertDialog;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;

import static com.sarahehabm.carbcalculator.common.ItemsAlertDialog.DIALOG_FAVORITE;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class FavoritesItemsPagerFragment extends ItemsBasePagerFragment {
    private final int LOADER_FAVORITE_ITEMS_ID = 122;

    @Override
    public Cursor getCursor() {
        Cursor cursor = getContext().getContentResolver().query(ItemEntry.CONTENT_URI, null,
                ItemEntry.COLUMN_FAVORITE + " = ? ",
                new String[]{String.valueOf(1)},
                ItemEntry.COLUMN_NAME + " COLLATE NOCASE ASC");
        return cursor;
    }

    @Override
    public void onLongClick(int position, int itemId) {
        selectedPosition = position;
        alertDialog = new ItemsAlertDialog(getContext(), DIALOG_FAVORITE, itemId, this);
        alertDialog.showDialog();
    }

    @Override
    protected void initLoader() {
        getLoaderManager().initLoader(LOADER_FAVORITE_ITEMS_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getContext(), ItemEntry.CONTENT_URI, null,
                ItemEntry.COLUMN_FAVORITE + " = ? ",
                new String[]{String.valueOf(1)},
                ItemEntry.COLUMN_NAME + " COLLATE NOCASE ASC");
        return cursorLoader;
    }
}