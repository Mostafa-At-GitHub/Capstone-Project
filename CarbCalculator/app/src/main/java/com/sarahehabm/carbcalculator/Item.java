package com.sarahehabm.carbcalculator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 17-May-16.
 */
public class Item {
    private int id;
    private String name;
    private boolean isFavorite;

    public Item() {
    }

    public Item(String name, boolean isFavorite) {
        this.name = name;
        this.isFavorite = isFavorite;
    }

    public Item(int id, String name, boolean isFavorite) {

        this.id = id;
        this.name = name;
        this.isFavorite = isFavorite;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static Item fromJson(String json) {
        return new Gson().fromJson(json, Item.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ArrayList<Item> listFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Item>>(){}.getType());
    }

    public static String listToJson(ArrayList<Item> items) {
        Gson gson = new Gson();
        return gson.toJson(items, new TypeToken<ArrayList<Item>>(){}.getType());
    }
}
