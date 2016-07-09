package com.sarahehabm.carbcalculator.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.meal.view.NewMeal1Activity;

/**
 Created by Sarah E. Mostafa on 07-Jul-16.
 */

public class NewMealWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context,
                NewMealWidgetProvider.class);

        int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : widgetIds) {
            Intent intent = new Intent(context, NewMeal1Activity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            remoteViews.setOnClickPendingIntent(R.id.imageButton_add, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}


