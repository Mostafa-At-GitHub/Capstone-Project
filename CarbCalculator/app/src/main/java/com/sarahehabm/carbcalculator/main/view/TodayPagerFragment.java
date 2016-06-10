package com.sarahehabm.carbcalculator.main.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.db.chart.model.ChartEntry;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.google.common.primitives.Ints;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

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
        CursorLoader cursorLoader =
                new CursorLoader(getContext(), CarbCounterContract.MealEntry.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getContext(), "LoadFinished", Toast.LENGTH_SHORT).show();

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

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);

        Date dateStart = new Date(calendarStart.getTimeInMillis()),
                dateEnd = new Date(calendarEnd.getTimeInMillis());

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
            Log.v(TAG, timestamp + /*" START=" + timestampStart + " END=" + timestampEnd +*/ " isToday= " + isToday);
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
        chartView.setXAxis(false)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                /*.setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"))*/;

        ArrayList<ChartEntry> entries = lineSet.getEntries();
        ArrayList<Integer> order = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            order.add(i);
        }
        long seed = System.currentTimeMillis();
        Collections.shuffle(order, new Random(seed));
        int[] orderArr = Ints.toArray(order);
//        int[] order = {1, 0, 2, 3};
        chartView.show(new Animation()
                .setOverlap(.7f, orderArr)
        );
    }
}
