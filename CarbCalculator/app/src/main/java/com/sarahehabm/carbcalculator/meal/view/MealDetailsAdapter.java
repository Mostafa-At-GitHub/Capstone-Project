package com.sarahehabm.carbcalculator.meal.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Amount;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.common.model.ItemAmount;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 02-Jun-16.
 */

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder> {

    private ArrayList<ItemAmount> itemAmounts;

    public MealDetailsAdapter(ArrayList<ItemAmount> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_meal_details, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.textViewName.setText("Item" + position);
//        holder.textViewData.setText("" + position);
        Context context = holder.itemView.getContext();

        ItemAmount itemAmount = itemAmounts.get(position);
        Item item = CarbCounterInterface.getItem(context, itemAmount.getItemId());
        Amount amount = CarbCounterInterface.getAmount(context, itemAmount.getAmountId());

        holder.textViewName.setText(item.getName());
        holder.textViewData.setText(itemAmount.getTotalWeight() +
                " " + amount.getUnit() + ": " +
                itemAmount.getTotalQuantity() + " carb grams");
    }

    @Override
    public int getItemCount() {
        if(itemAmounts == null)
            return 0;
        return itemAmounts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName, textViewData;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textView_name);
            textViewData = (TextView) itemView.findViewById(R.id.textView_data);
        }
    }
}
