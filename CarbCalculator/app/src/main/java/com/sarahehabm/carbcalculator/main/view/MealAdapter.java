package com.sarahehabm.carbcalculator.main.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.CursorRecyclerViewAdapter;
import com.sarahehabm.carbcalculator.common.Utility;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.MealEntry;

/**
 Created by Sarah E. Mostafa on 12-Jun-16.
 */

public class MealAdapter extends CursorRecyclerViewAdapter<MealAdapter.ViewHolder> {
    private Context context;

    public MealAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        String timestamp = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TIMESTAMP));
        String mealName = cursor.getString(cursor.getColumnIndex(MealEntry.COLUMN_TITLE));
        int carbs = cursor.getInt(cursor.getColumnIndex(MealEntry.COLUMN_TOTAL_CARBS));

        viewHolder.textViewTime.setText(Utility.formatTime(Long.valueOf(timestamp)));
        viewHolder.textViewName.setText(mealName);
        viewHolder.textViewCarbs.setText(context.getString(R.string.carb_grams, carbs));
    }

    @Override
    public int getItemCount() {
        if (getCursor() == null)
            return 0;

        return getCursor().getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewTime, textViewCarbs;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textView_meal_title);
            textViewTime = (TextView) itemView.findViewById(R.id.textView_meal_time);
            textViewCarbs = (TextView) itemView.findViewById(R.id.textView_meal_carbs);
        }
    }
}
