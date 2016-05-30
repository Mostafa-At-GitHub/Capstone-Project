package com.sarahehabm.carbcalculator.main.business;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.*;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Meal;

import java.util.ArrayList;

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
        return CarbCounterInterface.getAllMeals(context);
    }

    public static CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(context, MealEntry.CONTENT_URI,
                null, null, null, null);
    }
}
