package com.sarahehabm.carbcalculator.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.TestActivity;
import com.sarahehabm.carbcalculator.main.business.MainBusiness;
import com.sarahehabm.carbcalculator.meal.view.NewMealActivity;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    private MainBusiness mainBusinessLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();
        initializeFAB();
    }

    private void initializeFAB() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_create_meal);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //TODO start createMealActivity
//                Toast.makeText(MainActivity.this, "Should start create meal activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, NewMealActivity.class);
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
            case R.id.action_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                //TODO start profile activity
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                //TODO start settings activity
                return true;

            //TODO case added for testing database and content provider
            case R.id.action_test_activity:
                Intent intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
