package com.sarahehabm.carbcalculator.meal.view;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.common.model.ItemAmount;
import com.sarahehabm.carbcalculator.common.model.Meal;

import java.util.ArrayList;

public class NewMeal2Activity extends AppCompatActivity {
    NewMealItemAmountsAdapter newMealItemsAdapter;
    private String mealName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String s = null;
        if (intent != null) {
            if(intent.hasExtra(Constants.KEY_ITEMS)) {
                s = intent.getStringExtra(Constants.KEY_ITEMS);
//            ((TextView) findViewById(R.id.textView2)).setText("ITEMS RECIEVED:\n " + s);
            }

            if(intent.hasExtra(Constants.KEY_MEAL_NAME))
                mealName = intent.getStringExtra(Constants.KEY_MEAL_NAME);
        }

        newMealItemsAdapter = new NewMealItemAmountsAdapter(Item.listFromJson(s));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newMealItemsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_meal_2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_done:
                Toast.makeText(this, "DONE clicked", Toast.LENGTH_SHORT).show();
                //TODO should create the meal and insert in the DB
                // TODO then finish and go to meal details?
                Uri uri = CarbCounterInterface.
                        insertMeal(this, new Meal(mealName, 0, System.currentTimeMillis()));
                long mealId  = ContentUris.parseId(uri);
                ArrayList<ItemAmount> itemAmounts = newMealItemsAdapter.computeMealData((int) mealId);
                Log.e("NewMeal", String.valueOf(itemAmounts.size()));
                Log.e("NewMeal", itemAmounts.toString());

                int insertCount = CarbCounterInterface.insertItemAmounts(this, itemAmounts);
                if(insertCount == itemAmounts.size()) {
                    Toast.makeText(this, insertCount + " itemAmounts inserted", Toast.LENGTH_SHORT).show();
                    int totalMealCarbs = calculateMealCarbs(itemAmounts);
                    int mealUpdated = CarbCounterInterface.updateMealCarbs(this, (int) mealId, totalMealCarbs);
                    if(mealUpdated > 0) {
                        Toast.makeText(this, "Meal carbs updated successfully to " + totalMealCarbs, Toast.LENGTH_SHORT).show();
//                        finish();
                        Intent intent = new Intent(this, MealDetailsActivity.class);
                        intent.putExtra(Constants.KEY_MEAL_ID, mealId);
                        startActivityForResult(intent, Constants.REQUEST_CODE_MEAL_DETAILS);
                    } else
                        Toast.makeText(this, "Failed to update meal carbs", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to insert meal (itemAmounts)", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int calculateMealCarbs(ArrayList<ItemAmount> itemAmounts) {
        if(itemAmounts == null || itemAmounts.isEmpty())
            return 0;

        int carbs = 0;
        for (int i = 0; i < itemAmounts.size(); i++) {
            carbs += itemAmounts.get(i).getTotalQuantity();
        }
        return carbs;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_MEAL_DETAILS:
                switch (resultCode) {
                    case RESULT_OK:
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
