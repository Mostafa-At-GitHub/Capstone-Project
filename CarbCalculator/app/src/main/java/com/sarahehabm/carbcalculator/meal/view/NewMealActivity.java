package com.sarahehabm.carbcalculator.meal.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.CarbCounterContract.ItemEntry;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.meal.business.MealBusiness;

public class NewMealActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
//    private static final String TAG = NewMealActivity.class.getSimpleName();
    private static final int LOADER_ID = 10;

//    private TextInputLayout textInputLayout;
    private AutoCompleteTextView editText;
    private RecyclerView recyclerView;

    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();
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
                return MealBusiness.filter(NewMealActivity.this, constraint);
            }
        });

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String str = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_NAME));

                Toast.makeText(NewMealActivity.this, "You selected: " + str + "\nAt position: "
                        + position, Toast.LENGTH_SHORT).show();

                //TODO clear the field
                editText.setText("");
                //TODO update data set that holds the selected items
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_items);
//        LoaderManager
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
