package com.sarahehabm.carbcalculator.main.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.MealEntry;

import java.util.Calendar;

/**
 * Created by Sarah E. Mostafa on 10-May-16.
 */
public class WeekPagerFragment extends BasePagerFragment {
    private static final String TAG = WeekPagerFragment.class.getSimpleName();
    private final int LOADER_WEEK_ID = 112;

    @Override
    protected void initLoader() {
        getLoaderManager().initLoader(LOADER_WEEK_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MealEntry.CONTENT_URI, null, null, null, null);
    }

    /*@Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        super.onLoadFinished(loader, data);

        if(data == null || data.getCount()==0 || data.isClosed() || data.isLast())
            return;

        if(data.isAfterLast()) {
            if(!data.moveToFirst())
                return;
        }

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        calendarStart.add(Calendar.DAY_OF_MONTH, -7);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);

        Date dateStart = new Date(calendarStart.getTimeInMillis()),
                dateEnd = new Date(calendarEnd.getTimeInMillis());

        Log.v(TAG, "start= " + new SimpleDateFormat().format(dateStart));
        Log.v(TAG, "end= " + new SimpleDateFormat().format(dateEnd));

        LineSet lineSet = new LineSet();
        while (data.moveToNext()) {
            int carbs = data.getInt(data.getColumnIndex(CarbCounterContract.MealEntry.COLUMN_TOTAL_CARBS));
            String timestamp = data.getString(data.getColumnIndex(CarbCounterContract.MealEntry.COLUMN_TIMESTAMP));
            long timeStamp = Long.parseLong(timestamp);
            Date date = new Date(timeStamp);
            boolean isToday = false;

            if(date.after(dateStart) && date.before(dateEnd))
                isToday = true;

            Log.v(TAG, new SimpleDateFormat().format(new Date(timeStamp)));
            Log.v(TAG, timestamp + *//*" START=" + timestampStart + " END=" + timestampEnd +*//* " isToday= " + isToday);
            if(isToday) {
                Point point = new Point("", carbs);
                lineSet.addPoint(point);
            }
        }

        if(lineSet.size() < 1) {
            chartView.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
            return;
        } else {
            chartView.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.GONE);
        }

        chartView.addData(lineSet);

        ArrayList<ChartEntry> entries = lineSet.getEntries();
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            order.add(i);
        }
        long seed = System.currentTimeMillis();
        Collections.shuffle(order, new Random(seed));
        int[] orderArr = Ints.toArray(order);
        chartView.show(new Animation()
                .setOverlap(.7f, orderArr)
        );
    }*/

    @Override
    public Calendar getStartRange() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        calendarStart.add(Calendar.DAY_OF_MONTH, -7);

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
