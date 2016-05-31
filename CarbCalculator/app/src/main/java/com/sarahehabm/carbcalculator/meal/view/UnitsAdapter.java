package com.sarahehabm.carbcalculator.meal.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.common.model.Amount;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 31-May-16.
 */

public class UnitsAdapter extends ArrayAdapter<Amount> {
    private ArrayList<Amount> amounts;

    public UnitsAdapter(Context context, ArrayList<Amount> amounts) {
        super(context, android.R.layout.simple_list_item_1, amounts);
        this.amounts = amounts;
        if(amounts == null)
            this.amounts = new ArrayList<>();
    }

    @Override
    public Amount getItem(int position) {
        if(amounts == null)
            return null;

        return amounts.get(position);
    }

    @Override
    public int getCount() {
        if(amounts == null)
            return 0;

        return amounts.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView label = (TextView) row.findViewById(android.R.id.text1);
        label.setText(amounts.get(position).getUnit());

        return row;
    }

}
