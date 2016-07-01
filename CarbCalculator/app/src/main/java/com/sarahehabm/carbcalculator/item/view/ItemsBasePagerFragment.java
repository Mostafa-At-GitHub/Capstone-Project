package com.sarahehabm.carbcalculator.item.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.ItemsAlertDialog;
import com.sarahehabm.carbcalculator.common.ItemsAlertDialogInterface;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Item;

import java.util.ArrayList;

/**
 Created by Sarah E. Mostafa on 31-May-16.
 */

public abstract class ItemsBasePagerFragment extends Fragment
        implements OnItemLongClickListener, ItemsAlertDialogInterface, LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerViewItems;
    private TextView textViewEmpty;

    private AllItemsAdapter itemsAdapter;
    protected Cursor cursor;
    protected ItemsAlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_all_items, container, false);

        recyclerViewItems = (RecyclerView)rootView.findViewById(R.id.recyclerView_items);
        textViewEmpty = (TextView) rootView.findViewById(R.id.textView_empty);

        initLoader();

        return rootView;
    }

    protected abstract void initLoader();

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursor = data;
        itemsAdapter = new AllItemsAdapter(data, /*false,*/ this);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewItems.setAdapter(itemsAdapter);

        if(itemsAdapter.getItemCount() == 0) {
            textViewEmpty.setVisibility(View.VISIBLE);
            recyclerViewItems.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            recyclerViewItems.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public abstract Cursor getCursor();

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        itemsAdapter = new AllItemsAdapter(cursor, /*false, */this);
        recyclerViewItems.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();
    }

    public boolean isFavorite(int itemAtPosition) {
        if(cursor!= null && cursor.moveToPosition(itemAtPosition)) {
            int favoriteInt = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_FAVORITE));

            return favoriteInt != 0;
        }
        return false;
    }

    @Override
    public abstract void onLongClick(int position, int itemId);

    @Override
    public void onFavoriteItemClick(int itemId) {
        CarbCounterInterface.updateItemFavorite(getContext(), itemId, true);
        cursor = getCursor();
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUnFavoriteItemClick(int itemId) {
        CarbCounterInterface.updateItemFavorite(getContext(), itemId, false);
        cursor = getCursor();
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditItemClick(int itemId) {Intent intent = new Intent(getContext(), EditItemActivity.class);
        intent.putExtra(Constants.KEY_ITEM_ID, itemId);
        startActivity(intent);
    }

    public ArrayList<Item> getSelectedItems() {
        if(itemsAdapter!=null)
            return itemsAdapter.getSelectedItemsArray();

        return null;
    }
}
