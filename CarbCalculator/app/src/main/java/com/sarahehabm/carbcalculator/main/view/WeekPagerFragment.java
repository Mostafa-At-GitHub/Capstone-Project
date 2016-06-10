package com.sarahehabm.carbcalculator.main.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.*;

/**
 * Created by Sarah E. Mostafa on 10-May-16.
 */
public class WeekPagerFragment extends BasePagerFragment {
    private final int LOADER_WEEK_ID = 112;

    @Override
    protected void initLoader() {
        getLoaderManager().initLoader(LOADER_WEEK_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MealEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        super.onLoadFinished(loader, data);
    }
}
