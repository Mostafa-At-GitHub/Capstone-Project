package com.sarahehabm.carbcalculator.item.view;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.model.Amount;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 01-Jun-16.
 */

public class AddNewItemAmountsAdapter extends RecyclerView.Adapter<AddNewItemAmountsAdapter.ViewHolder> {
    private final String TAG = AddNewItemAmountsAdapter.class.getSimpleName();

    private ArrayList<Amount> amounts;
    private OnItemPropertyChangeListener propertyChangeListener;

    /*public AddNewItemAmountsAdapter() {
        amounts = new ArrayList<>();
        amounts.add(new Amount());
    }*/

    public AddNewItemAmountsAdapter(OnItemPropertyChangeListener propertyChangeListener) {
        amounts = new ArrayList<>();
        amounts.add(new Amount());
        this.propertyChangeListener = propertyChangeListener;
    }

    public AddNewItemAmountsAdapter(ArrayList<Amount> amounts,
                                    OnItemPropertyChangeListener propertyChangeListener) {
        this.amounts = amounts;
        this.propertyChangeListener = propertyChangeListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_new_item_amount, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.editTextAmount.setTag(position);
        holder.editTextUnit.setTag(position);
        holder.editTextCarbs.setTag(position);

        Amount amount = amounts.get(position);
        holder.editTextAmount.setText(String.valueOf(amount.getQuantity()));
        holder.editTextUnit.setText(amount.getUnit());
        holder.editTextCarbs.setText(String.valueOf(amount.getCarbGrams()));
    }

    @Override
    public int getItemCount() {
        if(amounts == null)
            return 0;
        return amounts.size();
    }

    public void addItem() {
        if(amounts == null)
            amounts = new ArrayList<>();

        amounts.add(new Amount());
        notifyDataSetChanged();
    }

    public Amount getAmountAt(int position) {
        if (amounts == null)
            return null;

        if (amounts.size()<position)
            return null;

        return amounts.get(position);
    }

    public ArrayList<Amount> getAmounts(int itemId) {
        for (int i = 0; i < amounts.size(); i++) {
            amounts.get(i).setItemId(itemId);
        }
        return amounts;
    }

    public boolean isValidAmounts() {
        boolean isValid = false;
        if (amounts==null || amounts.isEmpty())
            return false;

        for (int i = 0; i < amounts.size(); i++) {
            Amount amount = amounts.get(i);
            if(amount.getQuantity() >0
                    && amount.getUnit()!=null && !amount.getUnit().isEmpty()
                    && amount.getCarbGrams()>=0)
                isValid = true;
            else {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText editTextAmount, editTextUnit, editTextCarbs;

        public ViewHolder(View itemView) {
            super(itemView);

            editTextAmount = (EditText) itemView.findViewById(R.id.editText_amount);
            editTextUnit = (EditText) itemView.findViewById(R.id.editText_unit);
            editTextCarbs = (EditText) itemView.findViewById(R.id.editText_carbs);

            editTextAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = (int) editTextAmount.getTag();
                    String str = s.toString();
                    int quantity = (str==null || str.trim().isEmpty())? 0 : Integer.parseInt(str);
                    amounts.get(position).setQuantity(quantity);

                    if(!(str == null || str.isEmpty()))
                        propertyChangeListener.onAmountChanged(str);
                    else
                        propertyChangeListener.onAmountChanged("");
                }
            });

            editTextUnit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = (int) editTextUnit.getTag();
                    String unit = s.toString();
                    amounts.get(position).setUnit(unit);

                    if(!(unit == null || unit.isEmpty()))
                        propertyChangeListener.onUnitChanged(unit);
                    else
                        propertyChangeListener.onUnitChanged("");
                }
            });

            editTextCarbs.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = (int) editTextCarbs.getTag();
                    String str = s.toString();
                    int carbs = (str==null || str.trim().isEmpty())? -1 : Integer.parseInt(str);
                    amounts.get(position).setCarbGrams(carbs);

                    if(!(str == null || str.isEmpty()))
                        propertyChangeListener.onCarbsChanged(str);
                    else
                        propertyChangeListener.onCarbsChanged("");
                }
            });
        }
    }
}
