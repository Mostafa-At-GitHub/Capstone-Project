package com.sarahehabm.carbcalculator.main.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.*;

import java.util.Calendar;

/**
 * Created by Sarah E. Mostafa on 10-May-16.
 */
public class MonthPagerFragment extends BasePagerFragment {
    private final int LOADER_MONTH_ID = 113;

    @Override
    protected void initLoader() {
        getLoaderManager().initLoader(LOADER_MONTH_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MealEntry.CONTENT_URI, null, null, null, null);
    }

    /*@Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        super.onLoadFinished(loader, data);
    }*/



    @Override
    public Calendar getStartRange() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        calendarStart.add(Calendar.MONTH, -1);

        return calendarStart;
    }

    @Override
    public Calendar getEndRange() {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);

        return calendarEnd;
    }
}
