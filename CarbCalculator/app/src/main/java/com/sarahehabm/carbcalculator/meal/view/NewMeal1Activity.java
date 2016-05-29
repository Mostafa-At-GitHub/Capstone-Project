package com.sarahehabm.carbcalculator.meal.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.common.database.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.meal.business.MealBusiness;

public class NewMeal1Activity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    //    private static final String TAG = NewMeal1Activity.class.getSimpleName();
    private static final int LOADER_ID = 10;

    //    private TextInputLayout textInputLayout;
    private AutoCompleteTextView editText;
    private RecyclerView recyclerView;

    private SimpleCursorAdapter cursorAdapter;
    private NewMealItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_meal_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_next:
                Toast.makeText(this, "NEXT clicked", Toast.LENGTH_SHORT).show();
                //TODO should call the next activity
                Intent intent = new Intent(this, NewMeal2Activity.class);
                intent.putExtra(Constants.KEY_ITEMS, Item.listToJson(itemsAdapter.getItems()));
                startActivity(intent);
//                return true;
                break;

//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
//        textInputLayout = (TextInputLayout) findViewById(textInputLayout);
        editText = (AutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);

        cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_expandable_list_item_1,
                null,
                new String[]{ItemEntry.COLUMN_NAME},
                new int[]{android.R.id.text1},
                0
        );
        editText.setThreshold(1);
        editText.setAdapter(cursorAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        cursorAdapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cursor) {
                return cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));
            }
        });

        cursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return MealBusiness.filter(NewMeal1Activity.this, constraint, itemsAdapter.getItems());
            }
        });

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String itemName = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));
                int itemId = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ID));
                int itemIsFavorite = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_FAVORITE));

                Toast.makeText(NewMeal1Activity.this, "You selected: " + itemName + "\nAt position: "
                        + position, Toast.LENGTH_SHORT).show();

                //TODO clear the field
                editText.setText("");
                editText.clearFocus();
                /*View v = NewMeal1Activity.this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(!imm.isActive())
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }*/
                //TODO update data set that holds the selected items
//                itemsAdapter.swapCursor(cursor);
                itemsAdapter.addItem(new Item(itemId, itemName, (itemIsFavorite == 1)));
                Log.v("ET OnItemClick", "Cursor count = " + cursor.getCount());
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsAdapter = new NewMealItemsAdapter(null);
        recyclerView.setAdapter(itemsAdapter);
    }

    public void onViewAllItemsClick(View view) {
        Toast.makeText(this, "onPlusClick", Toast.LENGTH_SHORT).show();
        //TODO start all items activity
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return MealBusiness.getCursorLoader(this);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
