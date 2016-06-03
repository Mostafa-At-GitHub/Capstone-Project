package com.sarahehabm.carbcalculator.meal.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.R;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 28-May-16.
 */

public class NewMealItemsAdapter extends RecyclerView.Adapter<NewMealItemsAdapter.ViewHolder>  {
//    private Context context;
//    private Cursor cursor;
    private ArrayList<Item> items;

//    public NewMealItemsAdapter(/*Context context,*/ Cursor cursor) {
    public NewMealItemsAdapter(ArrayList<Item> items) {
//        this.context = context;
//        this.cursor = cursor;
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
//        if(cursor!=null && !cursor.isClosed() && cursor.moveToPosition(position)) {
//            String itemName = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));
        String itemName = items.get(position).getName();
            holder.textView_name.setText(itemName);
            holder.imageButton_remove.setTag(position);
//        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        notifyDataSetChanged();
    }

    /*public boolean removeItem(Item item) {
        notifyDataSetChanged();
        return items.remove(item);
    }*/

    /*public Item removeItem(int position) {
        notifyDataSetChanged();
        return items.remove(position);
    }*/

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

    /*
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == cursor) {
            return null;
        }
        final Cursor oldCursor = cursor;
//        if (oldCursor != null && mDataSetObserver != null) {
//            oldCursor.unregisterDataSetObserver(mDataSetObserver);
//        }
        cursor = newCursor;
        if (cursor != null) {
//            if (mDataSetObserver != null) {
//                cursor.registerDataSetObserver(mDataSetObserver);
//            }
//            mRowIdColumn = newCursor.getColumnIndexOrThrow(ItemEntry.COLUMN_ID);
//            mDataValid = true;
            notifyDataSetChanged();
        } else {
//            mRowIdColumn = -1;
//            mDataValid = false;
            notifyDataSetChanged();
            //There is no notifyDataSetInvalidated() method in RecyclerView.Adapter
        }
        return oldCursor;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }*/


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
            Toast.makeText(v.getContext(), "Remove clicked on item at position " + position,
                    Toast.LENGTH_SHORT).show();
            //TODO create and call callback
            onRemoveItemClickListener.onRemoveClick(position);
        }
    }

    public interface OnRemoveItemClickListener {
        void onRemoveClick(int position);
    }
}
