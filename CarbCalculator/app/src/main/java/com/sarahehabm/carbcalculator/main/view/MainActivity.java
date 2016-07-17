package com.sarahehabm.carbcalculator.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sarahehabm.carbcalculator.CarbCalculatorApplication;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.meal.view.NewMeal1Activity;
import com.sarahehabm.carbcalculator.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((CarbCalculatorApplication)getApplication()).startTracking();

        initializeToolbar();
        initializeFAB();
    }

    private void initializeFAB() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_create_meal);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMeal1Activity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            case R.id.action_profile: {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }

            /*//TODO case added for testing database and content provider
            case R.id.action_test_activity: {
                Intent intent = new Intent(this, TestActivity.class);
                startActivity(intent);
            }
            return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
