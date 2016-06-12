package com.sarahehabm.carbcalculator.main.business;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.MealEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Meal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sarah E. Mostafa on 11-May-16.
 */
public class MainBusiness {
    /*public static ArrayList<String> getMeals() {
        ArrayList<String> meals = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            meals.add("Meal#" + (i+1));

        return meals;
    }
*/
    public static ArrayList<Meal> getTodayMeals(Context context) {
        ArrayList<Meal> allMeals = CarbCounterInterface.getAllMeals(context);

        if(allMeals == null || allMeals.isEmpty())
            return allMeals;

        Calendar calendarStart = Calendar.getInstance(), calendarEnd = Calendar.getInstance();
//        calendarStart.setTimeInMillis(System.currentTimeMillis());

        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

//        calendarEnd = calendarStart;
//        calendarEnd.add(Calendar.MILLISECOND, 86400000);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 999);

//        long timestampStart = calendarStart.getTimeInMillis(),
//                timestampEnd = calendarEnd.getTimeInMillis();

        Date dateStart = new Date(calendarStart.getTimeInMillis()),
                dateEnd = new Date(calendarEnd.getTimeInMillis());

        ArrayList<Meal> todayMeals = new ArrayList<>();
        for (int i = 0; i < allMeals.size(); i++) {
            Meal meal = allMeals.get(i);
            long mealTimestamp = meal.getTimestamp();
            Date mealDate = new Date(mealTimestamp);


            if(mealDate.after(dateStart) && mealDate.before(dateEnd))
                todayMeals.add(meal);
        }

        return todayMeals;
    }

    public static CursorLoader getCursorLoader(Context context,
                                               long timestampStart, long timestampEnd) {
        String selection = MealEntry.COLUMN_TIMESTAMP + " between ? and ?";
        String[] selectionArgs = new String[]{String.valueOf(timestampStart),
                String.valueOf(timestampEnd)};
        return new CursorLoader(context, MealEntry.CONTENT_URI,
                null, selection, selectionArgs, null);
    }
}
