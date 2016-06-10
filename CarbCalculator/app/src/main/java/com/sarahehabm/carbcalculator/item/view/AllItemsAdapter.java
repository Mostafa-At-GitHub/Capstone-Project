package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 Created by Sarah E. Mostafa on 30-May-16.
 */

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.ViewHolder> {
    private Cursor cursor;
    private HashMap<Integer, Item> selectedItems;
//    private boolean favoriteOnly;
    private OnItemLongClickListener onItemLongClickListener;

    public AllItemsAdapter(Cursor cursor, /*boolean favoriteOnly,*/
                           OnItemLongClickListener onItemLongClickListener) {
        this.cursor = cursor;
        this.selectedItems = new HashMap<>();
//        this.favoriteOnly = favoriteOnly;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_items_item, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        int id = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ID));
        holder.itemView.setTag(id);
        String itemName = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));
        holder.textView_name.setText(itemName);
        holder.checkBox.setTag(position);
        holder.itemView.setTag(R.id.tag_id, id);
        holder.itemView.setTag(R.id.tag_position, position);
    }

    @Override
    public int getItemCount() {
        if(cursor == null)
            return 0;

        return cursor.getCount();
    }

    public HashMap<Integer, Item> getSelectedItems() {
        return selectedItems;
    }

    public ArrayList<Item> getSelectedItemsArray() {
        if(selectedItems == null)
            return null;

        return new ArrayList<Item>(selectedItems.values());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {
        TextView textView_name;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_name = (TextView) itemView.findViewById(R.id.textView_item_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            Toast.makeText(buttonView.getContext(),
                    "Item at position " + position + " is checked: " + isChecked,
                    Toast.LENGTH_SHORT).show();

            int itemId = (int) itemView.getTag();
            Item item = CarbCounterInterface.getItem(itemView.getContext(), itemId);
            if(isChecked) {
                if(!selectedItems.containsKey(itemId))
                    selectedItems.put(itemId, item);
            } else {
                selectedItems.remove(itemId);
            }

            Log.e("SELECTED ITEMS", String.valueOf(selectedItems.size()));
        }

        @Override
        public boolean onLongClick(View v) {
            if(onItemLongClickListener != null) {
                int position = (int) v.getTag(R.id.tag_position),
                        id = (int) v.getTag(R.id.tag_id);
                onItemLongClickListener.onLongClick(position, id);
                return true;
            }
            return false;
        }
    }
}
