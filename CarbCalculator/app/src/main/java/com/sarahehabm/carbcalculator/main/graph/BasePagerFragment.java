package com.sarahehabm.carbcalculator.main.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.Tools;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.sarahehabm.carbcalculator.R;

/**
 * Created by Sarah E. Mostafa on 09-May-16.
 */
public class BasePagerFragment extends Fragment {
    private BarChartView chartView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        chartView = (BarChartView) rootView.findViewById(R.id.barchart);
//        chartView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        BarSet barSet = new BarSet();
        barSet.addBar(new Bar("", 137));
        barSet.addBar(new Bar("", 50));
        barSet.addBar(new Bar("", 296));
        barSet.addBar(new Bar("", 110));

        chartView.addData(barSet);

        chartView.setBarSpacing(Tools.fromDpToPx(100));
        chartView.setRoundCorners(Tools.fromDpToPx(3));
//        chartView.setBarBackgroundColor(Color.parseColor("#592932"));


        chartView.setXAxis(false)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));

//        chartView.show();
        int[] order = {1, 0, 2, 3};
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
        );

        return rootView;
    }
}
