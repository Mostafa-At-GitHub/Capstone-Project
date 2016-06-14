package com.sarahehabm.carbcalculator.main.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import com.sarahehabm.carbcalculator.main.business.MainBusiness;

import java.util.Calendar;

/**
 * Created by Sarah E. Mostafa on 10-May-16.
 */
public class TodayPagerFragment extends BasePagerFragment {
    private static final String TAG = TodayPagerFragment.class.getSimpleName();
    private final int LOADER_TODAY_ID = 111;

    @Override
    protected void initLoader() {
        getLoaderManager().initLoader(LOADER_TODAY_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return MainBusiness.getCursorLoader(getContext(), getStartRange().getTimeInMillis(),
                        getEndRange().getTimeInMillis());
    }

    @Override
    public Calendar getStartRange() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

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
