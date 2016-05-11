package com.sarahehabm.carbcalculator.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.main.business.MainBusiness;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SteppersView mealsView;

    private MainBusiness mainBusinessLayer;
    private ArrayList<SteppersItem> mealsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();
        initializeMealsView();
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initializeMealsView() {
        SteppersView steppersView = (SteppersView) findViewById(R.id.steppersView);
        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setFragmentManager(getSupportFragmentManager());
        ArrayList<String> meals = MainBusiness.getMeals();
        mealsList = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            SteppersItem mealItem = new SteppersItem();
            mealItem.setLabel(meals.get(i));
            mealItem.setSubLabel(meals.get(i) + "'s description");
            mealsList.add(mealItem);
        }

        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(mealsList);
        steppersView.build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                //TODO start profile activity
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                //TODO start settings activity
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
