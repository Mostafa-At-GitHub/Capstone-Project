package com.sarahehabm.carbcalculator.common.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 17-May-16.
 */
public class Amount {
    private int id;
    private int carbGrams;
    private int quantity;
    private String unit;
    private int itemId;

    public Amount() {
    }

    public Amount(int id, int carbGrams, int quantity, String unit, int itemId) {

        this.id = id;
        this.carbGrams = carbGrams;
        this.quantity = quantity;
        this.unit = unit;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarbGrams() {
        return carbGrams;
    }

    public void setCarbGrams(int carbGrams) {
        this.carbGrams = carbGrams;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public static Amount fromJson(String json) {
        return new Gson().fromJson(json, Amount.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ArrayList<Amount> listFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Amount>>(){}.getType());
    }

    public static String listToJson(ArrayList<Amount> amounts) {
        Gson gson = new Gson();
        return gson.toJson(amounts, new TypeToken<ArrayList<Amount>>(){}.getType());
    }
}
