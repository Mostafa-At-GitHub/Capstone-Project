package com.sarahehabm.carbcalculator.meal.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.model.Item;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 28-May-16.
 */

public class NewMealItemsAdapter extends RecyclerView.Adapter<NewMealItemsAdapter.ViewHolder>  {
    private ArrayList<Item> items;

    public NewMealItemsAdapter(ArrayList<Item> items) {
        this.items = items;
        if(this.items == null)
            this.items = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_new_meal_item, parent, false);

        return new ViewHolder(rowView, new OnRemoveItemClickListener() {
            @Override
            public void onRemoveClick(int position) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String itemName = items.get(position).getName();
        holder.textView_name.setText(itemName);
        holder.imageButton_remove.setTag(position);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        if (items == null)
            return -1;

        return items.get(position).getId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView_name;
        ImageButton imageButton_remove;

        private OnRemoveItemClickListener onRemoveItemClickListener;

        public ViewHolder(View itemView, OnRemoveItemClickListener onRemoveItemClickListener) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_item_name);
            imageButton_remove = (ImageButton) itemView.findViewById(R.id.imageButton_remove);
            imageButton_remove.setOnClickListener(this);
            this.onRemoveItemClickListener = onRemoveItemClickListener;
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            onRemoveItemClickListener.onRemoveClick(position);
        }
    }

    public interface OnRemoveItemClickListener {
        void onRemoveClick(int position);
    }
}
