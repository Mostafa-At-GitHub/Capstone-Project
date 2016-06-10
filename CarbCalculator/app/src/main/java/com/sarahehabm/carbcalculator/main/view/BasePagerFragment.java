package com.sarahehabm.carbcalculator.main.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.model.ChartEntry;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.google.common.primitives.Ints;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.MealEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Sarah E. Mostafa on 09-May-16.
 */
public abstract class BasePagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private final int LOADER_ID = 10;
    protected LineChartView chartView;
    protected TextView textViewEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        initLoader();

        chartView = (LineChartView) rootView.findViewById(R.id.barchart);
        textViewEmpty = (TextView) rootView.findViewById(R.id.textView_empty);

        /*BarSet barSet = new BarSet();
        barSet.addBar(new Bar("", 137));
        barSet.addBar(new Bar("", 50));
        barSet.addBar(new Bar("", 296));
        barSet.addBar(new Bar("", 110));
        barSet.addBar(new Bar("", 137));
        barSet.addBar(new Bar("", 50));
        barSet.addBar(new Bar("", 296));
        barSet.addBar(new Bar("", 110));
        barSet.addBar(new Bar("", 137));
        barSet.addBar(new Bar("", 50));
        barSet.addBar(new Bar("", 296));
        barSet.addBar(new Bar("", 110));

        chartView.addData(barSet);

//        chartView.setBarSpacing(Tools.fromDpToPx(100));
        chartView.setRoundCorners(Tools.fromDpToPx(3));

        chartView.setXAxis(false)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));

        chartView.show();*/

        /*int[] order = {1, 6, 0, 10, 2, 5, 3, 8, 4, 7, 9, 11};
//        final Runnable auxAction = action;
        Runnable chartOneAction = new Runnable() {
            @Override
            public void run() {
//                auxAction.run();
//                showTooltip();
            }
        };
        chartView.show(new Animation()
                .setOverlap(.7f, order)
//                .setAlpha(2)
//                .setEndAction(chartOneAction)
        );*/

        /*Cursor cursor = getContext().getContentResolver()
                .query(MealEntry.CONTENT_URI, null, null, null, null);
        if(cursor==null || cursor.getCount() == 0)
            return rootView;

        BarSet barSet = new BarSet();
        while (cursor.moveToNext()) {
            int carbs = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
            barSet.addBar(new Bar("", carbs));
        }

        chartView.addData(barSet);

//        chartView.setBarSpacing(Tools.fromDpToPx(100));
        chartView.setRoundCorners(Tools.fromDpToPx(3));

        chartView.setXAxis(false)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));*/

        return rootView;
    }

    protected abstract void initLoader();

    @Override
    public abstract Loader<Cursor> onCreateLoader(int id, Bundle args);

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getContext(), "LoadFinished", Toast.LENGTH_SHORT).show();

        if(data == null || data.getCount()==0 || data.isClosed() || data.isLast())
            return;

        if(data.isAfterLast()) {
            if(!data.moveToFirst())
                return;
        }

        LineSet barSet = new LineSet();
        while (data.moveToNext()) {
            int carbs = data.getInt(data.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));
//            barSet.addBar(new Bar("", carbs));
            barSet.addPoint(new Point("", carbs));
        }

//        barSet.addBar(new Bar("", 137));
//        barSet.addBar(new Bar("", 50));
//        barSet.addBar(new Bar("", 296));
//        barSet.addBar(new Bar("", 110));

        chartView.addData(barSet);

////        chartView.setBarSpacing(Tools.fromDpToPx(100));
//        chartView.setRoundCorners(Tools.fromDpToPx(3));

        chartView.setXAxis(false)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                /*.setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"))*/;

//        int numBars = barSet.getEntries().size();
        ArrayList<ChartEntry> entries = barSet.getEntries();
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
//        chartView.refreshDrawableState();
//        chartView.notifyDataUpdate();

//        chartView.show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Toast.makeText(getContext(), "LoadReset", Toast.LENGTH_SHORT).show();
    }
}
