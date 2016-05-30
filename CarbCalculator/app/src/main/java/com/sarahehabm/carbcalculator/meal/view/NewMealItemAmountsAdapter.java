package com.sarahehabm.carbcalculator.meal.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Item;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 31-May-16.
 */

public class NewMealItemAmountsAdapter extends RecyclerView.Adapter<NewMealItemAmountsAdapter.ViewHolder>{
    private ArrayList<Item> items;

    public NewMealItemAmountsAdapter(ArrayList<Item> items) {
        this.items = items;
        if(this.items == null)
            this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_new_meal_item_amount, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        String itemName = items.get(position).getName();
        int itemId = items.get(position).getId();
//        ArrayList<Amount> itemAmounts =
//                CarbCounterInterface.getAmountsByItemId(context, itemId);
        String[] itemAmounts = CarbCounterInterface.getAmountsByItemId_TEMP(context, itemId);

        holder.textView_name.setText(itemName);
        holder.spinner_amounts.setTag(position);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, itemAmounts);
        holder.spinner_amounts.setAdapter(spinnerAdapter);
    }

    @Override
    public int getItemCount() {
        if(items == null)
            return 0;

        return items.size();
    }

    @Override
    public long getItemId(int position) {
        if (items == null)
            return -1;

        return items.get(position).getId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        TextView textView_name;
        Spinner spinner_amounts;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_item_name);
            spinner_amounts = (Spinner) itemView.findViewById(R.id.spinner_amount);
            spinner_amounts.setOnItemSelectedListener(this);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int parentPosition = (int) parent.getTag();
            Toast.makeText(view.getContext(), "Parent position: " + parentPosition
                    + ", selected item: " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
