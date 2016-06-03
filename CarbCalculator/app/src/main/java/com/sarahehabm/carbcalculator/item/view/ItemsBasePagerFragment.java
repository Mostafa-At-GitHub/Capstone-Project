package com.sarahehabm.carbcalculator.item.view;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;

/**
 Created by Sarah E. Mostafa on 31-May-16.
 */

public abstract class ItemsBasePagerFragment extends Fragment implements OnItemLongClickListener {
    private RecyclerView recyclerViewItems;
    private TextView textViewEmpty;

    private AllItemsAdapter itemsAdapter;
    private Cursor cursor;

    public static final int DIALOG_ALL = 1;
    public static final int DIALOG_FAVORITE = 2;
    public static final int OPTION_FAVORITE = 0;
    public static final int OPTION_UNFAVORITE = 0;
    public static final int OPTION_EDIT = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_all_items, container, false);

        recyclerViewItems = (RecyclerView)rootView.findViewById(R.id.recyclerView_items);
        textViewEmpty = (TextView) rootView.findViewById(R.id.textView_empty);

        cursor = getCursor();

        itemsAdapter = new AllItemsAdapter(cursor, false, this);
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

    public boolean isFavorite(int itemAtPosition) {
        if(cursor!= null && cursor.moveToPosition(itemAtPosition)) {
            int favoriteInt = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_FAVORITE));

            return favoriteInt != 0;
        }
        return false;
    }

    @Override
    public void onLongClick(int position, int itemId) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                .setItems(new String[]{"Add/Remove favorite", "Edit Item"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getContext(), "Clicked on " + which, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        builder.show();
    }

    protected void showDialog(int which, final int itemId) {
        String[] options = new String[0];
        DialogInterface.OnClickListener onClickListener = null;
        switch (which) {
            case DIALOG_ALL:
                options = getContext().getResources().getStringArray(R.array.dialog_options_all);
                onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case OPTION_FAVORITE:
                                Toast.makeText(getContext(), "Should add to favorites",
                                        Toast.LENGTH_SHORT).show();
                                updateItemFavorite(itemId, true);
                                break;

                            case OPTION_EDIT:
                                editItem();
                                break;
                        }
                    }
                };
                break;

            case DIALOG_FAVORITE:
                options = getContext().getResources().getStringArray(R.array.dialog_options_favorites);
                onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case OPTION_UNFAVORITE:
                                Toast.makeText(getContext(), "Should remove from favorites",
                                        Toast.LENGTH_SHORT).show();
                                updateItemFavorite(itemId, false);
                                break;

                            case OPTION_EDIT:
                                editItem();
                                break;
                        }
                    }
                };
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setItems(options, onClickListener);
        builder.show();
    }

    public void editItem() {
        Toast.makeText(getContext(), "Should edit item", Toast.LENGTH_SHORT).show();
    }

    public void updateItemFavorite(int itemId, boolean isFavorite) {
        CarbCounterInterface.updateItemFavorite(getContext(), itemId, isFavorite);
    }
}
