package com.sarahehabm.carbcalculator.item.view;

/**
 Created by Sarah E. Mostafa on 16-Jun-16.
 */

public interface OnItemPropertyChangeListener {
    void onAmountChanged(String amount);
    void onUnitChanged(String unit);
    void onCarbsChanged(String carbs);
}
