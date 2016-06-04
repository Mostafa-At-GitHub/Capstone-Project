package com.sarahehabm.carbcalculator;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 Created by Sarah E. Mostafa on 04-Jun-16.
 */

public class CarbCalculatorApplication extends Application {
    private GoogleAnalytics googleAnalytics;
    public Tracker mTracker;

    public void startTracking() {
        if(mTracker == null) {
            googleAnalytics = GoogleAnalytics.getInstance(this);
            mTracker = googleAnalytics.newTracker(R.xml.tracker_app);
            googleAnalytics.enableAutoActivityReports(this);
            googleAnalytics.getLogger().setLogLevel(com.google.android.gms.analytics.Logger.LogLevel.VERBOSE);
        }
    }

    public Tracker getTracker() {
        startTracking();
        return mTracker;
    }
}
