package com.sarahehabm.carbcalculator.common.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 17-May-16.
 */
public class Meal {
    private int id;
    private String name;
    private int totalCarbs;
    private long timestamp;

    public Meal() {
    }

    public Meal(String name, int totalCarbs, long timestamp) {
        this.name = name;
        this.totalCarbs = totalCarbs;
        this.timestamp = timestamp;
    }

    public Meal(int id, String name, int totalCarbs, long timestamp) {
        this.id = id;
        this.name = name;
        this.totalCarbs = totalCarbs;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(int totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static Meal fromJson(String json) {
        return new Gson().fromJson(json, Meal.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ArrayList<Meal> listFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Meal>>(){}.getType());
    }

    public static String listToJson(ArrayList<Meal> meals) {
        Gson gson = new Gson();
        return gson.toJson(meals, new TypeToken<ArrayList<Meal>>(){}.getType());
    }
}
