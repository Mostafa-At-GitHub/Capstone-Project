package com.sarahehabm.carbcalculator.meal.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.Utility;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.common.model.ItemAmount;
import com.sarahehabm.carbcalculator.common.model.Meal;

import java.util.ArrayList;

public class MealDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textView_carb_total, textView_date;

    private MealDetailsAdapter mealDetailsAdapter;
    private long mealId;
    private Meal meal;
    private ArrayList<Item> items;
    private ArrayList<ItemAmount> itemAmounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(Constants.KEY_MEAL_ID)) {
            mealId = (long) intent.getExtras().get(Constants.KEY_MEAL_ID);
        }

        initializeData();

        String title = meal.getName() == null? "" : meal.getName();
        setTitle(title);

        textView_date  = (TextView) findViewById(R.id.textView_date);
        textView_carb_total = (TextView) findViewById(R.id.textView_total);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealDetailsAdapter = new MealDetailsAdapter(itemAmounts);
        recyclerView.setAdapter(mealDetailsAdapter);
        textView_date.setText(Utility.formatDate(meal.getTimestamp()));
        textView_carb_total.setText(String.valueOf(meal.getTotalCarbs()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void initializeData() {
        meal = CarbCounterInterface.getMeal(this, (int) mealId);
        itemAmounts = CarbCounterInterface.getItemAmountsNyMealId(this, (int) mealId);
    }
}
