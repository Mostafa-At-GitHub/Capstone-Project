package com.sarahehabm.carbcalculator.meal.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.Constants;
import com.sarahehabm.carbcalculator.Item;
import com.sarahehabm.carbcalculator.R;

public class NewMeal2Activity extends AppCompatActivity {

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
        if (intent != null && intent.hasExtra(Constants.KEY_ITEMS)) {
            s = intent.getStringExtra(Constants.KEY_ITEMS);
//            ((TextView) findViewById(R.id.textView2)).setText("ITEMS RECIEVED:\n " + s);
        }


        NewMealItemsAdapter newMealItemsAdapter = new NewMealItemsAdapter(Item.listFromJson(s));
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
