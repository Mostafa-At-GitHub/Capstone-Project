package com.sarahehabm.carbcalculator.main.business;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 11-May-16.
 */
public class MainBusiness {
    public static ArrayList<String> getMeals() {
        ArrayList<String> meals = new ArrayList<>();

        for (int i = 0; i < 10; i++)
            meals.add("Meal#" + (i+1));

        return meals;
    }
}
