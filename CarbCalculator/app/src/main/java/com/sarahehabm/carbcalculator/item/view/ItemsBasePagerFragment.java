package com.sarahehabm.carbcalculator.item.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.R;

/**
 Created by Sarah E. Mostafa on 31-May-16.
 */

public abstract class ItemsBasePagerFragment extends Fragment {
    private RecyclerView recyclerViewItems;
    private TextView textViewEmpty;

    private AllItemsAdapter itemsAdapter;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_all_items, container, false);

        recyclerViewItems = (RecyclerView)rootView.findViewById(R.id.recyclerView_items);
        textViewEmpty = (TextView) rootView.findViewById(R.id.textView_empty);

        cursor = getCursor();

        itemsAdapter = new AllItemsAdapter(cursor, false);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewItems.setAdapter(itemsAdapter);

        if(itemsAdapter.getItemCount() == 0) {
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerViewItems.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            recyclerViewItems.setVisibility(View.VISIBLE);
        }

        return rootView;
    }


    public abstract Cursor getCursor();

}
