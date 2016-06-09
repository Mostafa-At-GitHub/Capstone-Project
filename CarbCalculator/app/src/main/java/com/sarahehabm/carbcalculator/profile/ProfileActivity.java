package com.sarahehabm.carbcalculator.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Preferences;

public class ProfileActivity extends AppCompatActivity {
    private TextView textView;
    private CircularImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        Preferences preferences = new Preferences(this);
        String name = preferences.getString(Preferences.KEY_NAME);
        String url = preferences.getString(Preferences.KEY_IMAGE_URL);

        textView = (TextView) findViewById(R.id.textView_user_name);
        imageView = (CircularImageView) findViewById(R.id.imageView_user_picture);

        textView.setText(name);
        Log.e(ProfileActivity.class.getSimpleName(), "URL= " + url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
