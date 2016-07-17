package com.sarahehabm.carbcalculator.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.sarahehabm.carbcalculator.R;
import com.sarahehabm.carbcalculator.common.Preferences;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewName, textViewAge;
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
        long birthday = preferences.getLong(Preferences.KEY_BIRTHDAY);

                textViewName = (TextView) findViewById(R.id.textView_user_name);
        textViewAge = (TextView) findViewById(R.id.textView_user_age);
        imageView = (CircularImageView) findViewById(R.id.imageView_user_picture);

        textViewName.setText(name);
        Picasso.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);

        long now = System.currentTimeMillis();
        if(birthday == -1) {
            textViewAge.setVisibility(View.INVISIBLE);
        } else {
            Calendar calendarNow = Calendar.getInstance(),
                    calendarBirthday = Calendar.getInstance();
            calendarBirthday.setTimeInMillis(birthday);

            int age = calendarNow.get(Calendar.YEAR) - calendarBirthday.get(Calendar.YEAR);
            textViewAge.setVisibility(View.VISIBLE);
            textViewAge.setText(getString(R.string.age, age));
        }
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
