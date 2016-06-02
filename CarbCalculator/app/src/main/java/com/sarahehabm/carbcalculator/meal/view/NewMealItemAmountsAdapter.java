package com.sarahehabm.carbcalculator.meal.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Amount;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.common.model.ItemAmount;

import java.util.ArrayList;
import java.util.HashMap;

/**
 Created by Sarah E. Mostafa on 31-May-16.
 */

public class NewMealItemAmountsAdapter extends RecyclerView.Adapter<NewMealItemAmountsAdapter.ViewHolder>{
    private final String TAG = NewMealItemAmountsAdapter.class.getSimpleName();

    private ArrayList<Item> items;
    private HashMap<Item, Pair<Amount, Integer>> itemAmountsMap;
//    private ArrayList<Amount> itemAmounts;

    public NewMealItemAmountsAdapter(ArrayList<Item> items) {
        this.items = items;
        if(this.items == null)
            this.items = new ArrayList<>();

        itemAmountsMap = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            itemAmountsMap.put(items.get(i), null);
        }
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
        ArrayList<Amount> itemAmounts = CarbCounterInterface.getAmountsByItemId(context, itemId);
//        String[] itemAmountsArr = CarbCounterInterface.getAmountsByItemId_TEMP(context, itemId);

        holder.textView_name.setText(itemName);
        holder.spinner_unit.setTag(position);
        holder.editText_amount.setTag(position);
        UnitsAdapter unitsAdapter = new UnitsAdapter(context, itemAmounts);
        holder.spinner_unit.setAdapter(unitsAdapter);
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

    public ArrayList<ItemAmount> computeMealData(int mealId) {
        ArrayList<ItemAmount> result = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Pair<Amount, Integer> amountQuantityPair =  itemAmountsMap.get(item);
            Amount amount = amountQuantityPair.first;
            int quantity = amountQuantityPair.second;
            int carbGrams = (quantity*amount.getCarbGrams())/amount.getQuantity();
            ItemAmount itemAmount = new ItemAmount(item.getId(), amount.getId(), carbGrams, mealId);
            result.add(itemAmount);
        }

        return result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        TextView textView_name;
        EditText editText_amount;
        Spinner spinner_unit;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_item_name);
            editText_amount = (EditText) itemView.findViewById(R.id.editText_amount);
            spinner_unit = (Spinner) itemView.findViewById(R.id.spinner_unit);
            spinner_unit.setOnItemSelectedListener(this);
            editText_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = (int) editText_amount.getTag();
                    Item item = items.get(position);

                    View parent = (View)editText_amount.getParent();
                    Spinner spinner = (Spinner) parent.findViewById(R.id.spinner_unit);
                    Amount amount = ((UnitsAdapter)spinner.getAdapter()).getItem(spinner.getSelectedItemPosition());

                    String quantityStr = editText_amount.getText().toString().trim().isEmpty()?
                            "0" : editText_amount.getText().toString().trim();
                    int quantity = Integer.parseInt(quantityStr);
                    itemAmountsMap.put(item, new Pair<Amount, Integer>(amount, quantity));
                    Log.e(TAG, "Hashmap size(afterTextChanged)= " + itemAmountsMap.size());
                }
            });
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int parentPosition = (int) parent.getTag();
            Toast.makeText(view.getContext(), "Parent position: " + parentPosition
                    + ", selected item: " + position, Toast.LENGTH_SHORT).show();
//            if(itemAmounts!=null)
//                editText_amount.setText(String.valueOf(itemAmounts.get(position).getQuantity()));

            Item item = items.get(parentPosition);
            Amount amount = ((UnitsAdapter)parent.getAdapter()).getItem(position);

            View parentParent = (View) parent.getParent();
            if(((int)parentParent.findViewById(R.id.editText_amount).getTag()) == parentPosition) {
                if(amount!=null)
                    ((EditText)parentParent.findViewById(R.id.editText_amount))
                            .setText(String.valueOf(amount.getQuantity()));
            }

            int quantity = Integer.parseInt(
                    ((EditText)parentParent.findViewById(R.id.editText_amount)).getText().toString());
            itemAmountsMap.put(item, new Pair<Amount, Integer>(amount, quantity));
            Log.e(TAG, "Hashmap size= " + itemAmountsMap.size());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
