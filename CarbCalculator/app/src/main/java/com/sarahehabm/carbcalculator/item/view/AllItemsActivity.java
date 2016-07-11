package com.sarahehabm.carbcalculator.item.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.model.Item;

import java.util.ArrayList;

public class AllItemsActivity extends AppCompatActivity {
    private final String TAG = AllItemsActivity.class.getSimpleName();

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.action_done:
//                Toast.makeText(this, "Should finish this activity and pass the selected items",
//                        Toast.LENGTH_SHORT).show();
                //TODO finish this activity and pass the selected items
                String selectedItems = getSelectedItems();
                Intent data = new Intent();
                data.putExtra(Constants.KEY_ITEMS, selectedItems);
                setResult(RESULT_OK, data);
                finish();
                break;

            case R.id.action_new_item:
//                Toast.makeText(this, "Add new item", Toast.LENGTH_SHORT).show();
                //TODO start new item activity
                Intent intent = new Intent(this, AddNewItemActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getSelectedItems() {
        AllItemsActivityFragment activityFragment = ((AllItemsActivityFragment)fragment);
        ArrayList<Item> selectedItemsAll = ((AllItemsPagerFragment)activityFragment.pagerAdapter.getItem(0)).getSelectedItems();
        ArrayList<Item> selectedItemsFav = ((FavoritesItemsPagerFragment)activityFragment.pagerAdapter.getItem(1)).getSelectedItems();

//        Log.e(TAG, "selectedItemsAll= " + selectedItemsAll.size());
//        Log.e(TAG, "selectedItemsFav= " + selectedItemsFav.size());


        String selectedItemsString;
        if (selectedItemsAll != null && selectedItemsFav != null) {
            selectedItemsAll.addAll(selectedItemsFav);
            selectedItemsString = Item.listToJson(selectedItemsAll);
        } else if (selectedItemsAll != null)
            selectedItemsString = Item.listToJson(selectedItemsAll);
        else if (selectedItemsFav != null)
            selectedItemsString = Item.listToJson(selectedItemsFav);
        else
            selectedItemsString = null;

        return selectedItemsString;
    }
}
