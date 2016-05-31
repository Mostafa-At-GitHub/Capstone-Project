package com.sarahehabm.carbcalculator.common.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Sarah E. Mostafa on 17-May-16.
 */
public class ItemAmount {
    private int id;
    private int itemId;
    private int amountId;
    private int totalQuantity;
    private int mealId;

    public ItemAmount() {
    }

    public ItemAmount(int itemId, int amountId, int totalQuantity, int mealId) {
        this.itemId = itemId;
        this.amountId = amountId;
        this.totalQuantity = totalQuantity;
        this.mealId = mealId;
    }

    public ItemAmount(int id, int itemId, int amountId, int totalQuantity, int mealId) {
        this.id = id;
        this.itemId = itemId;
        this.amountId = amountId;
        this.totalQuantity = totalQuantity;
        this.mealId = mealId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getAmountId() {
        return amountId;
    }

    public void setAmountId(int amountId) {
        this.amountId = amountId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public static ItemAmount fromJson(String json) {
        return new Gson().fromJson(json, ItemAmount.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ArrayList<ItemAmount> listFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<ItemAmount>>(){}.getType());
    }

    public static String listToJson(ArrayList<ItemAmount> itemAmounts) {
        Gson gson = new Gson();
        return gson.toJson(itemAmounts, new TypeToken<ArrayList<ItemAmount>>(){}.getType());
    }
}
