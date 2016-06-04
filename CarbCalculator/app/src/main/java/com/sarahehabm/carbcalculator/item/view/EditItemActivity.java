package com.sarahehabm.carbcalculator.item.view;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Constants;
import com.sarahehabm.carbcalculator.common.database.CarbCounterInterface;
import com.sarahehabm.carbcalculator.common.model.Amount;
import com.sarahehabm.carbcalculator.common.model.Item;

import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {
    private static final String TAG = EditItemActivity.class.getSimpleName();
    private EditText editText_name;
    private RecyclerView recyclerView_amounts;

    private AddNewItemAmountsAdapter adapter;
    private int itemId;
    private String itemName;
    private Item item;
    private ArrayList<Amount> newAmounts;
    private boolean validItemName, validQuantities, nameChanged, amountAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(Constants.KEY_ITEM_ID)) {
            int itemId = intent.getIntExtra(Constants.KEY_ITEM_ID, -1);
            item = CarbCounterInterface.getItem(this, itemId);
        }

        validItemName = true;
        validQuantities = false;
        nameChanged = false;
        amountAdded = false;

        editText_name = (EditText) findViewById(R.id.editText_name);
        if(item!=null)
            editText_name.setText(item.getName());
        editText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                itemName = editText_name.getText().toString().trim();
                nameChanged = true;
                if (itemName != null && !itemName.isEmpty())
                    validItemName = true;
                else
                    validItemName = false;

                supportInvalidateOptionsMenu();
                invalidateOptionsMenu();
            }
        });
        recyclerView_amounts = (RecyclerView) findViewById(R.id.recyclerView_amounts);
        recyclerView_amounts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddNewItemAmountsAdapter();
        if(item!=null) {
            ArrayList<Amount> amounts = CarbCounterInterface.getAmountsByItemId(this,
                    item.getId());
            adapter = new AddNewItemAmountsAdapter(amounts);
        }

        validQuantities = adapter.isValidAmounts();
        supportInvalidateOptionsMenu();
        invalidateOptionsMenu();

        recyclerView_amounts.setAdapter(adapter);
    }

    public void onAddNewAmountClick(View view) {
        amountAdded = true;
        if (adapter == null)
            adapter = new AddNewItemAmountsAdapter();

        adapter.addItem();
//        Amount amount = adapter.getAmountAt(adapter.getItemCount() -1);

        validQuantities = adapter.isValidAmounts();
        supportInvalidateOptionsMenu();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (nameChanged && validItemName /*&& validQuantities*/) {
            menu.getItem(0).setEnabled(true);
        } else {
            menu.getItem(0).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_save_edit:
//                boolean result = save();
//                Log.e(TAG, "Save success: " + result);
                //TODO Should monitor changes & save now
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean save() {
        String itemName = editText_name.getText().toString();
        if(itemName == null || itemName.trim().isEmpty()) {
            Toast.makeText(this, "A name must be provided", Toast.LENGTH_SHORT).show();
            return false;
        }

        Uri itemUri = CarbCounterInterface.insertItem(this, new Item(itemName, false));
        int itemId = (int) ContentUris.parseId(itemUri);

        ArrayList<Amount> amounts = adapter.getAmounts(itemId);
        if(amounts==null || amounts.isEmpty()) {
            Toast.makeText(this, "At least one amount must be provided", Toast.LENGTH_SHORT).show();
            return false;
        }
        int insertCount = CarbCounterInterface.insertAmounts(this, amounts);

        if(insertCount == amounts.size()) {
            Toast.makeText(this, insertCount + " amounts inserted successfully for item " + itemId,
                    Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } else
            Toast.makeText(this, "Failed to insert item and amounts. Please try again later",
                    Toast.LENGTH_SHORT).show();

        return false;
    }

}
