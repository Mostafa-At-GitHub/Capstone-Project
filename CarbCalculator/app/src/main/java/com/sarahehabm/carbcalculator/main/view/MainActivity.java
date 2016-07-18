package com.sarahehabm.carbcalculator.main.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sarahehabm.carbcalculator.CarbCalculatorApplication;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.TestAsyncTask;
import com.sarahehabm.carbcalculator.TestAsyncTaskAmounts;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.OnDataRetrieveListener;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Amount;
import com.sarahehabm.carbcalculator.common.model.Item;
import com.sarahehabm.carbcalculator.meal.view.NewMeal1Activity;
import com.sarahehabm.carbcalculator.profile.ProfileActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDataRetrieveListener {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((CarbCalculatorApplication)getApplication()).startTracking();

        initializeViews();
        getData();
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    private void initializeViews() {
        initializeToolbar();
        initializeFAB();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setIndeterminate(true);
    }

    private void getData() {
        ArrayList<Item> items = CarbCounterInterface.getAllItems(this);
        if(items == null || items.isEmpty())
            new TestAsyncTask(this).execute(new Pair<Context, String>(this, ""));

        ArrayList<Amount> amounts = CarbCounterInterface.getAllAmounts(this);
        if(amounts == null || amounts.isEmpty())
            new TestAsyncTaskAmounts(this).execute(new Pair<Context, String>(this, ""));
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

    @Override
    public void onStartCall() {
        progressDialog.show();
    }

    @Override
    public void onFinishCall(String result, int serviceKey) {
        progressDialog.hide();

        switch (serviceKey) {
            case Constants.SERVICE_GET_ITEMS: {
                int insertCount = CarbCounterInterface.insertItems(this, Item.listFromJson(result));
                if(insertCount < 1) {
                    new TestAsyncTask(this).execute(new Pair<Context, String>(this, ""));
                }
                break;
            }

            case Constants.SERVICE_GET_ITEM_AMOUNTS: {
                int insertCount = CarbCounterInterface.insertAmounts(this, Amount.listFromJson(result));
                if(insertCount < 1) {
                    new TestAsyncTaskAmounts(this).execute(new Pair<Context, String>(this, ""));
                }
                break;
            }
        }
    }
}
